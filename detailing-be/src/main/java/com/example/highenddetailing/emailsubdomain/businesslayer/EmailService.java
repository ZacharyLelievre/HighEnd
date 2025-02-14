package com.example.highenddetailing.emailsubdomain.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentConfirmation(String recipientEmail, String serviceName, String date, String time) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com"); // Change to your email
        message.setTo(recipientEmail);
        message.setSubject("Appointment Confirmation - High-End Detailing");
        message.setText(
                "Hello,\n\n" +
                        "Your appointment for " + serviceName + " has been confirmed.\n" +
                        "📅 Date: " + date + "\n" +
                        "⏰ Time: " + time + "\n\n" +
                        "Thank you for choosing High-End Detailing!\n" +
                        "Best regards,\nHigh-End Detailing Team"
        );

        mailSender.send(message);
        System.out.println("Confirmation email sent to " + recipientEmail);
    }
}
