package vn.zerocoder.Mart.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.ShippingAddressRequest;
import vn.zerocoder.Mart.model.Cart;
import vn.zerocoder.Mart.service.CartDetailService;
import vn.zerocoder.Mart.service.OrderService;
import vn.zerocoder.Mart.service.ShippingAddressService;
import vn.zerocoder.Mart.service.ShippingMethodService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shipping")
public class ShippingController {

    private final CartDetailService cartDetailService;
    private final ShippingAddressService shippingAddressService;
    private final AuthUtils authUtils;
    private final ShippingMethodService shippingMethodService;
    private final OrderService orderService;

    @GetMapping
    public String index(Model theModel) {
        Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();
        if(cart.getCartDetails().isEmpty()) {
            return "redirect:/cart?shippingCartEmpty=true";
        }
        theModel.addAttribute("addressRequest", new ShippingAddressRequest());
        theModel.addAttribute("cartDetail", cart.getCartDetails());
        theModel.addAttribute("shippingMethods", shippingMethodService.findAll());
        return "user/shipping/list";
    }


    @PostMapping("/address/add")
    public String addAddress(@Valid @ModelAttribute("addressRequest") ShippingAddressRequest addressRequest,
                             BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {

            Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();
            theModel.addAttribute("cartDetail", cart.getCartDetails());
            // Thông báo lỗi để hiện thị
            theModel.addAttribute("saveShippingAddressFailed", true);

            return "user/shipping/list";
        }
        // Lưu địa chỉ
        shippingAddressService.save(addressRequest);
        return "redirect:/shipping?saveShippingAddressSuccess=true";
    }

    @GetMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        shippingAddressService.delete(id);
        return "redirect:/shipping?deleteShippingAddressSuccess=true";
    }


    @PostMapping("/order")
    public String order(@RequestParam("shippingMethodId") Long shippingMethodId,
                        @RequestParam(value = "addressId", required = false) Long addressId
    ) {

        // Kiểm tra xem giỏ hàng có rỗng không
        Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();
        // Nếu rỗng thì chuyển hướng về trang giỏ hàng
        if(cart.getCartDetails().isEmpty()) {
            return "redirect:/cart?shippingCartEmpty=true";
        }
        // Kiểm tra xem địa chỉ và phương thức vận chuyển có null không
        if(addressId == null || shippingMethodId == null) {
            return "redirect:/shipping?adship=true";
        }

        // Lưu đơn hàng
        Long id = orderService.save(shippingMethodId, addressId);

        // Nếu lưu thất bại thì chuyển hướng về trang giỏ hàng
        if(id == -1) {
            return "redirect:/cart?orderFailed=true"; // Giỏ hàng có sản phẩm không đủ số lượng trong kho
        }

        return "redirect:/order/" + id + "?orderSuccess=true";
    }
}
