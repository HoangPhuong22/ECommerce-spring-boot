package vn.zerocoder.Mart.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.service.OrderService;
import vn.zerocoder.Mart.service.UserService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashBoardController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("users", userService.findTop6ByOrderByCreatedAtDesc());
        model.addAttribute("orders", orderService.findTop6ByOrderByCreatedAtDesc());
        return "admin/dashboard";
    }
}
