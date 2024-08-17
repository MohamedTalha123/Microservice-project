package com.hps.paymentservice.service;


import com.hps.paymentservice.dto.NotificationRequest;
import com.hps.paymentservice.repository.BillRepo;
import com.hps.paymentservice.dto.BillRequest;
import com.hps.paymentservice.dto.PaymentInfo;
import com.hps.paymentservice.entity.PaymentMethod;
import com.hps.paymentservice.entity.Bill;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BillService {

    private final RabbitTemplate rabbitTemplate;


    @Value("${notification.msgRoutingKey}")
    private String msgRoutingKey;

    @Value("${notification.otpRoutingKey}")
    private String otpRoutingKey;

    @Value("${notification.exchange}")
    private String exchangeName;

    public static Bill appBill;
    private final BillRepo billRepo;

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Value("${password.choices.5}")
    private String numeric;

    public Bill createBill(BillRequest billRequest){
        String ref = UUID.randomUUID().toString().substring(0, 5);;

        appBill = billRepo.save(
                Bill.builder()
                        .clientId(billRequest.getClientId())
                        .orderId(billRequest.getOrderId())
                        .paid(Boolean.FALSE)
                        .billReference("BILL-".concat(ref) )
                        .totalAmount(billRequest.getAmount())
                        .paymentMethod(PaymentMethod.STRIPE)
                        .build()
        );
        return appBill;
    }

    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        Stripe.apiKey = secretKey;

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Product purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    public String payBill(BillRequest billRequest){
        String verificationCode = passwordForSms();
        System.out.println(verificationCode);

        appBill.setVerificationCode(verificationCode);
        appBill.setVerificationCodeSentAt(LocalDateTime.now());
        billRepo.save(appBill);
        sendNotification(billRequest.getPhone(), verificationCode ,"OTP" );

        return "Check sms to verify payment";
    }

    public String confirmBillPayment(String verificationCode){
        int minutes = (int) ChronoUnit.MINUTES.between(appBill.getVerificationCodeSentAt(), LocalDateTime.now());
        if ( appBill.getVerificationCode().equals(verificationCode)) {
            if (minutes <= 5) {
                try {
                    PaymentInfo paymentInfo = new PaymentInfo();
                    paymentInfo.setAmount(appBill.getTotalAmount().multiply(BigDecimal.valueOf(10)).intValue());
                    paymentInfo.setCurrency("USD");
                    paymentInfo.setReceiptEmail("mouad10cherrat@gmail.com");

                    PaymentIntent paymentIntent = createPaymentIntent(paymentInfo);
                    System.out.println("payemtnn intent ++++++++++++ " + paymentIntent);
                    //not sure
//                    if ("succeeded".equals(paymentIntent.getStatus())) {
                        appBill.setPaid(Boolean.TRUE);
                        billRepo.save(appBill);
                        appBill = null;
                        return "Bill paid successfully";
//                    } else {
//                        return "Payment failed, status: " + paymentIntent.getStatus();
//                    }

                } catch (StripeException e) {
                    return "Payment failed: " + e.getMessage();
                }
            } else throw new RuntimeException("Verification code expired, try again !");
        }
        throw new RuntimeException("Wrong code, try again !") ;
    }

    public String passwordForSms(){
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 5; i++){
            password.append(numeric.charAt(new Random().nextInt(numeric.length())));
        }
        return password.toString();
    }
    public void sendNotification(String phone, String code, String msgType) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .phone(phone)
                .factureReference(appBill.getBillReference())
                .totalAmount(appBill.getTotalAmount())
                .build();
        if (code != null) {
            notificationRequest.setCode(code);
        }
        if (msgType.equals("OTP")) {
            rabbitTemplate.convertAndSend(exchangeName, otpRoutingKey, notificationRequest);
            return;
        }
        rabbitTemplate.convertAndSend(exchangeName, msgRoutingKey, notificationRequest);
    }
}
