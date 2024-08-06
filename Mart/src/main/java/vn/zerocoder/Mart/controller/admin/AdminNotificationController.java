package vn.zerocoder.Mart.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.service.NotificationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notification")
public class AdminNotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("notifications", notificationService.findAll());
        return "admin/notification/list";
    }
    @GetMapping("/add")
    public String add() {
        return "admin/notification/add";
    }
    @PostMapping("/add")
    public String add(@RequestParam String title, @RequestParam String message, Model model) {
        if(title.isEmpty() || message.isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập đủ thông tin");
            return "admin/notification/add";
        }
        notificationService.save(title, message);
        return "redirect:/admin/notification";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        notificationService.delete(id);
        return "redirect:/admin/notification";
    }
}
