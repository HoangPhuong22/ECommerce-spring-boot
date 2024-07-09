package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.UserRequest;

public interface UserService {
    Long save(UserRequest userRequest);
    Long changePassword(String oldPassword, String newPassword, String confirmPassword);
}
