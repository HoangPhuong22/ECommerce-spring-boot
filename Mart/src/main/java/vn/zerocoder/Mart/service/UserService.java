package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.UserRequest;
import vn.zerocoder.Mart.dto.response.UserResponse;
import vn.zerocoder.Mart.model.User;

import java.util.List;

public interface UserService {
    Long save(UserRequest userRequest);
    Long changePassword(String oldPassword, String newPassword, String confirmPassword);
    List<UserResponse> findAll();
}
