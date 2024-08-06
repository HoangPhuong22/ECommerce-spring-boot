package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.model.PasswordResetToken;
import vn.zerocoder.Mart.model.User;

public interface PasswordResetTokenService {
    Long save(String token, User user);
    PasswordResetToken findByToken(String token);
    void deleteByToken(String token);
    void deleteByUser(User user);
}
