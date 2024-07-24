package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.model.Order;
import vn.zerocoder.Mart.service.OrderService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final AuthUtils authUtils;
    private final OrderService orderService;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model theModel) {
        Order order = orderService.findById(id);
        if(!order.getUser().getId().equals(authUtils.loadUserByUsername().getUserConfig().getId())) {
            throw new AccessDeniedException("Không có quyền vào đơn hàng này");
        }
        theModel.addAttribute("order", order);
        return "/user/order/view";
    }
}
