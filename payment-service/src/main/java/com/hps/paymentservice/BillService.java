package com.hps.paymentservice;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BillService {

    public static Bill appBill;
    private final BillRepo billRepo;

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Value("${password.choices.5}")
    private String numeric;


    public Bill createBill(BillRequest billRequest){
        appBill = billRepo.save(
                Bill.builder()
                        .clientId(billRequest.getClientId())
                        .orderId(billRequest.getOrderId())
                        .paid(Boolean.FALSE)
                        .totalAmount(BigDecimal.ZERO)
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

        String verificationCode = generateVerificationCode(appBill, billRequest.getPhone());
        System.out.println(verificationCode);
        appBill.setVerificationCode(verificationCode);
        appBill.setVerificationCodeSentAt(LocalDateTime.now());

        return "Check sms to verify payment";
    }


    public String confirmBillPayment(String verificationCode){
//        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getName();
//        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();

        int minutes = (int) ChronoUnit.MINUTES.between(appBill.getVerificationCodeSentAt(), LocalDateTime.now());
        System.out.println(appBill.getVerificationCode());
        System.out.println(verificationCode);
        System.out.println(minutes);

        if ( appBill.getVerificationCode().equals(verificationCode)) {
            if (minutes <= 5) {
                //todo : set the bill as paid and update products accordingly
                //complete the payment process

                return "Balance updated and bill paid";
            } else return "Verification code expired, try again !";
        }
        return "Wrong code";
    }



    public String passwordForSms(){
        String password = "";
        for (int i = 0; i < 5; i++){
            password += numeric.charAt(new Random().nextInt(numeric.length()));
        }
        return password;
    }

    String generateVerificationCode(Bill bill, String phone){
        String verificationCode = passwordForSms();
        String verificationMssg = generateSms(verificationCode, bill);
//        smsService.sendSMS(phone, verificationMssg);
        return verificationCode;
    }

    private String generateSms(String verificationCode, Bill bill){
        return verificationCode+ " est le code de confirmation pour votre payement pour " +
                "un montant de "+ bill.getTotalAmount()+
                " MAD.\n" +
                "Ce code expire dans 5 minutes.";
    }
}
