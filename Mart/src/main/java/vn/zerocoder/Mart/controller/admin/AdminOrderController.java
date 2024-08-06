package vn.zerocoder.Mart.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.service.OrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/order/list";
    }

    @GetMapping("/{id}/edit")
    public String editOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "admin/order/edit";
    }

    @PostMapping("/edit")
    public String saveOrder(@RequestParam Long id, @RequestParam String status) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/order";
    }
}
