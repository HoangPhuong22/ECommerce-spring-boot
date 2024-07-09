package vn.zerocoder.Mart.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.dto.request.UserRequest;
import vn.zerocoder.Mart.service.UserService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthUtils authUtils;
    @GetMapping("/login")
    public String login() {
        CustomUserDetail user = authUtils.loadUserByUsername();
        if(user != null) {
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
        return "redirect:/login?register=true";
    }
}
