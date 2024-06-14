package vn.zerocoder.Mart.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @GetMapping
    public String listUser() {
        return "admin/user/list";
    }
}
