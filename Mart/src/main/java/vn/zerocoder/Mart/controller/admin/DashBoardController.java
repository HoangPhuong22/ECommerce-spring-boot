package vn.zerocoder.Mart.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashBoardController {
    @GetMapping
    public String home() {
        return "admin/dashboard";
    }
}
