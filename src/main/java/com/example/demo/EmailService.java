package com.example.demo;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // OTP Generate Karne Ka Method
    public String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    // Email Pe OTP Bhejne Ka Method
    public void sendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP for verification is: " + otp);
        mailSender.send(message);
    }
}