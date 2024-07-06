package vn.zerocoder.Mart.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Kiểm tra loại ngoại lệ và xác định thông báo lỗi
        String errorMessage = switch (exception) {
            case BadCredentialsException badCredentialsException -> "Tên đăng nhập hoặc mật khẩu không đúng!";
            case LockedException lockedException -> "Tài khoản của bạn đã bị khóa!";
            case DisabledException disabledException -> "Tài khoản của bạn đã bị vô hiệu hóa!";
            case null, default -> "Đăng nhập thất bại!";
        };

        // Mã hóa thông điệp lỗi để đảm bảo không chứa ký tự không hợp lệ trong URL
        String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

        // Chuyển hướng lại trang đăng nhập với thông báo lỗi
        response.sendRedirect("/login?error=true&message=" + encodedErrorMessage);
    }
}
