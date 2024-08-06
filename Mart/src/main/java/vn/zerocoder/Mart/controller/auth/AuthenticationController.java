package vn.zerocoder.Mart.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.UserRequest;
import vn.zerocoder.Mart.model.PasswordResetToken;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.service.EmailService;
import vn.zerocoder.Mart.service.PasswordResetTokenService;
import vn.zerocoder.Mart.service.UserService;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthUtils authUtils;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "auth/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRequest());
        return "auth/register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRequest userRequest,
                           BindingResult bindingResult,
                           @RequestParam("confirmPassword") String confirmPassword
    ) {
        boolean checkPassword = userRequest.getPassword().equals(confirmPassword);
        if (bindingResult.hasErrors() || !checkPassword) {
            if(!checkPassword) {
                bindingResult.rejectValue("password", "error.user", "Mật khẩu không khớp");
            }
            return "auth/register";
        }
        Long id = userService.save(userRequest);
        if(id == -1) {
            bindingResult.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
            return "auth/register";
        }
        if(id == -2) {
            bindingResult.rejectValue("email", "error.user", "Email đã tồn tại");
            return "auth/register";
        }
        String text = "Xin chào " + userRequest.getFirstName() + ",\n\n"
                + "Tài khoản " + userRequest.getUsername() + " của bạn đã được tạo thành công.\n"
                + "Bạn có thể đăng nhập vào hệ thống bằng tài khoản này để sử dụng dịch vụ của chúng tôi.\n\n"
                + "Trân trọng,\n"
                + "Zero Coder";
        emailService.sendSimpleMessage(userRequest.getEmail(), "Đăng ký tài khoản thành công", text);
        return "redirect:/login?register=true";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }


    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);
        if(user == null)
        {
            model.addAttribute("error", "Email không tồn tại");
            return "auth/forgot-password";
        }
        passwordResetTokenService.deleteByUser(user);
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.save(token, user);
        String text = "Xin chào " + user.getProfile().getFirstName() + ",\n\n"
                + "Tài khoản " + user.getUsername() + " của bạn đã được yêu cầu đặt lại mật khẩu.\n"
                + "Nhấn vào đường dẫn sau để đặt lại mật khẩu:\n"
                + "http://localhost:8080/change-password?id=" + user.getId() + "&token=" + token + "\n\n"
                + "Đường dẫn này sẽ hết hạn sau 1 phút, bạn vui lòng đặt lại mật khẩu trong thời gian này.\n"
                + "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n\n"
                + "Trân trọng,\n"
                + "Zero Coder";
        emailService.sendSimpleMessage(email, "Yêu cầu đặt lại mật khẩu", text);
        model.addAttribute("success", "Đường dẫn đặt lại mật khẩu đã được gửi đến email của bạn");
        return "auth/forgot-password";
    }


    @GetMapping("/change-password")
    public String resetPassword(@RequestParam Long id, @RequestParam String token, Model model) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);
        if(passwordResetToken == null || !AuthUtils.validatePasswordToken(passwordResetToken.getExpiryDate())) {
            model.addAttribute("expired", "Đường dẫn đã hết hạn");
            return "auth/forgot-password";
        }
        model.addAttribute("id", id);
        model.addAttribute("token", token);
        return "auth/change-password";
    }


    @PostMapping("/change-password")
    public String resetPassword(@RequestParam Long id, @RequestParam String token,
                                @RequestParam String password,
                                @RequestParam String confirmPassword, Model model
    ) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);
        if(passwordResetToken == null || !AuthUtils.validatePasswordToken(passwordResetToken.getExpiryDate())) {
            model.addAttribute("expired", "Đường dẫn đã hết hạn");
            return "auth/forgot-password";
        }
        if(!password.equals(confirmPassword) || password.length() < 6) {
            model.addAttribute("error_mk", "Mật khẩu không khớp hoặc không đủ 6 ký tự");
            return "auth/change-password";
        }
        userService.updatePassword(password, confirmPassword, id);
        passwordResetTokenService.deleteByToken(token);
        String text = "Xin chào " + passwordResetToken.getUser().getProfile().getFirstName() + ",\n\n"
                + "Mật khẩu của tài khoản " + passwordResetToken.getUser().getUsername() + " đã được thay đổi.\n"
                + "Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ với chúng tôi ngay lập tức.\n\n"
                + "Trân trọng,\n"
                + "Zero Coder";
        emailService.sendSimpleMessage(passwordResetToken.getUser().getEmail(), "Thay đổi mật khẩu", text);
        return "redirect:/login?resetPass=true";
    }
}
