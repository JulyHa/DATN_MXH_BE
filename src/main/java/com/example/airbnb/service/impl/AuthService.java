package com.example.airbnb.service.impl;

import com.example.airbnb.model.Role;
import com.example.airbnb.model.Users;
import com.example.airbnb.dto.RegistrationRequest;
import com.example.airbnb.repository.IRoleRepository;
import com.example.airbnb.repository.IUserRepository;
import com.example.airbnb.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService() {
    }

    @Override
    public boolean registerUser(RegistrationRequest registrationRequest) throws MessagingException {
        Iterable<Users> users = userRepository.findAll();
        for (Users currentUser : users) {
            if (currentUser.getEmail().equals(registrationRequest.getEmail())) {
                return false;
            }
        }
        Users user = new Users();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEnabled(false);
        user.setVerificationCode(generateOTP());
        userRepository.save(user);
        sendVerificationEmail(user);
        return true;
    }

    private void sendVerificationEmail(Users user) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Please verify your email");

        String content = "Dear " + user.getFirstName()+" "+user.getLastName() + ",<br>"
                + "Please click the below link to verify your email:<br>"
                + "<a href='http://localhost:8080/verify-otp?otp=" + user.getVerificationCode() + "'> Verify </a><br>"
                + "Thank you!";

        helper.setText(content, true);
        javaMailSender.send(message);
    }

    @Override
    public boolean verifyOTP(String otp) {
        Optional<Users> userOptional = userRepository.findByVerificationCode(otp);
        if (userOptional.isPresent()) {
            Role role = roleRepository.findByName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            Users user = userOptional.get();
            user.setRoles(roles);
            user.setEnabled(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(999999));
    }
}
