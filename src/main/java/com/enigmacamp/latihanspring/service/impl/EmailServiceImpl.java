package com.enigmacamp.latihanspring.service.impl;

import com.enigmacamp.latihanspring.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationCodeEmail(String toEmail, String verificationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Verifikasi Akun Pawtner");

            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px;'>" +
                    "<img src='https://res.cloudinary.com/dh8gmlzth/image/upload/v1750928559/Pawtner_aeaa0x.png' alt='Pawtner Logo' style='height: 100px;'><br><br>" +
                    "<h2>Halo, Pawtner!</h2>" +
                    "<p>Terima kasih telah mendaftar. Berikut adalah kode verifikasi akun Anda:</p>" +
                    "<div style='font-size: 24px; font-weight: bold; color: #4CAF50;'>" + verificationCode + "</div>" +
                    "<p>Kode ini akan kedaluwarsa dalam <strong>3 menit</strong>.</p>" +
                    "<hr style='margin-top:30px;'>" +
                    "<small style='color: #888;'>Email ini dikirim otomatis oleh sistem Pawtner.</small>" +
                    "</div>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException | RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal mengirim email verifikasi");
        }
    }
}