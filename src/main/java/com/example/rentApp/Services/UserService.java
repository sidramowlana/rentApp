package com.example.rentApp.Services;

import com.example.rentApp.Models.PasswordResetToken;
import com.example.rentApp.Models.User;
import com.example.rentApp.Repositories.PasswordResetTokenRepository;
import com.example.rentApp.Repositories.UserRepository;
import com.example.rentApp.Response.MessageResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JavaMailSender javaMailSender;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Value("${rentApp.app.jwtSecret}")
    private String jwtSecret;

    @Value("${rentApp.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JavaMailSender javaMailSender, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public ResponseEntity<?> getUserByUserId(Integer userId) {
        if (userRepository.existsById(userId)) {
            Optional<User> user = userRepository.findById(userId);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User not found!!!"));
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found with username: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> getUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User email not available!!!"));
    }

    public ResponseEntity<?> getUserByDrivingLicenceId(String drivingLicenceId) {
        if (userRepository.existsByDrivingLicence(drivingLicenceId)) {
            User user = userRepository.findByDrivingLicence(drivingLicenceId);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Driving License No not available!!!"));
    }

    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public ResponseEntity<?> forgotPasswordProcess(String userEmail, HttpServletRequest request) {

        if (userRepository.existsByEmail(userEmail)) {
            User user = userRepository.findByEmail(userEmail);

            //get that user details and store in a variable
            PasswordResetToken passwordResetToken = new PasswordResetToken();

            //generate the token
            String resetToken = generateJwtToken(user.getUsername());
            System.out.println("reset token: " + resetToken);
            passwordResetToken.setToken(resetToken);
//
//            set the token to that particular user
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDate(30);

            // save it in the db
            passwordResetTokenRepository.save(passwordResetToken);

            //send an email with the token to that user
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":4200";
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(passwordResetToken.getUser().getEmail());
            simpleMailMessage.setSubject("Password Reset Request");
            simpleMailMessage.setText("To reset your password, click the link below:\n" + appUrl
                    + "/resetPassword?token=" + passwordResetToken.getToken());
            javaMailSender.send(simpleMailMessage);
            return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error: Email not found!")));
        }
    }

    public ResponseEntity<?> updateNewPassword(String userNameToken, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(userNameToken);
        User user = passwordResetToken.getUser();
        if (userRepository.existsById(user.getUserId())) {
            user.setPassword(newPassword);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse("Password Successfully updated."));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error user not available!"));
        }
    }
}


