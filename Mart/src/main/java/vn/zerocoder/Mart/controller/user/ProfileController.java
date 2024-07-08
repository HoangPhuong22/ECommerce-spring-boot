package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.utils.UserDetailUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserDetailUtils UserDetailUtils;
    @GetMapping
    public String profile(Model theModel) {
        CustomUserDetail user = UserDetailUtils.loadUserByUsername();
        theModel.addAttribute("user", user);
        return "user/profile";
    }
}
