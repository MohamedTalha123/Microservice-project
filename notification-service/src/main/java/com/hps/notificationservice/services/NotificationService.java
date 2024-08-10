package com.hps.notificationservice.services;

import com.hps.notificationservice.dto.NotificationRequest;
import com.hps.notificationservice.entities.Notification;
import com.hps.notificationservice.repositories.NotificationRepo;
import com.vonage.client.sms.SmsSubmissionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {

    private final SmsService smsService;
    private final NotificationRepo notificationRepo;

    @RabbitListener(queues = "${notification.queues.msgQueue}")
    public void msgReceiveMessage(NotificationRequest request) {
        String verificationMsg = generateConfirmationNotification(request);
         generateNotification(request, verificationMsg);
    }

    @RabbitListener(queues = "${notification.queues.otpQueue}")
    public void otpReceiveMessage(NotificationRequest request) {
        String verificationMsg = generateSmsForOtp(request);
        generateNotification(request, verificationMsg);
    }

    private void generateNotification(NotificationRequest request, String verificationMsg) {
        SmsSubmissionResponse response = smsService.sendSMS(request.getPhone(), verificationMsg);
        Notification notification = Notification.builder()
                .recipientPhone(request.getPhone())
                .textBody(verificationMsg)
                .messageId(response.getMessages().get(0).getId())
                .responseStatus(response.getMessages().get(0).getStatus().toString())
                .messagePrice(response.getMessages().get(0).getMessagePrice())
//                .messagePrice(BigDecimal.valueOf(0.15))
                .errorMessage(response.getMessages().get(0).getErrorText())
                .build();
        notificationRepo.save(notification);
    }

    private String generateSmsForOtp(NotificationRequest request) {
        return "Votre code de confirmation pour le payment  de la facture " +
                request.getFactureReference() +
                " d'un montant de " +
                request.getTotalAmount() +
                " MAD est : " +
                request.getCode() +
                ". Veuillez saisir ce code pour compléter la transaction." +
                "Ce code expire dans 5 minutes.";
    }

    private String generateConfirmationNotification(NotificationRequest request) {
        return "Le payment d'un montant de " +
                request.getTotalAmount() +
                " MAD pour la facture avec la référence " +
                request.getFactureReference() +
                " a été effectué avec succès. \n" + "Merci de votre confiance.";
    }
}