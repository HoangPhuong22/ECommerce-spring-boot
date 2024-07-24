package vn.zerocoder.Mart.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public String listUser(Model theModel) {
        theModel.addAttribute("users", userService.findAll());
        return "admin/user/list";
    }
}
