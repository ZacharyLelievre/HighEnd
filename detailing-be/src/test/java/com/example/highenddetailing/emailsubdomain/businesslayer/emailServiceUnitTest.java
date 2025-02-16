package com.example.highenddetailing.emailsubdomain.businesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class emailServiceUnitTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendAppointmentConfirmation() {
        // Given
        String recipientEmail = "test@example.com";
        String serviceName = "Car Wash";
        String date = "2025-07-01";
        String time = "10:00 AM";

        // When
        emailService.sendAppointmentConfirmation(recipientEmail, serviceName, date, time);

        // Then: capture the sent message
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        // Assert that the message was built as expected
        assertEquals("jessebou7008@gmail.com", sentMessage.getFrom());
        assertArrayEquals(new String[]{recipientEmail}, sentMessage.getTo());
        assertEquals("Appointment Confirmation - High-End Detailing", sentMessage.getSubject());

        String expectedText = "Hello,\n\n" +
                "Your appointment for " + serviceName + " has been confirmed.\n" +
                "📅 Date: " + date + "\n" +
                "⏰ Time: " + time + "\n\n" +
                "Thank you for choosing High-End Detailing!\n" +
                "Best regards,\nHigh-End Detailing Team";
        assertEquals(expectedText, sentMessage.getText());
    }
}
