package vn.zerocoder.Mart.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.model.PasswordResetToken;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.PasswordResetTokenRepository;
import vn.zerocoder.Mart.service.PasswordResetTokenService;
import vn.zerocoder.Mart.utils.TimeUtils;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private static final int EXPIRATION = 60; // 60 seconds
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public Long save(String token, User user) {
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(TimeUtils.calculateExpiryDate(EXPIRATION))
                .build();
        return passwordResetTokenRepository.save(passwordResetToken).getId();
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        passwordResetTokenRepository.deleteByToken(token);
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        passwordResetTokenRepository.deleteByUser(user);
    }
}
