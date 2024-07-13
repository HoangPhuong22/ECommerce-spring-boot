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
    @PostMapping("/order")
    public String order(@RequestParam("shippingMethodId") Long shippingMethodId,
                        @RequestParam("addressId") Long addressId
    ) {
        Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();
        if(cart.getCartDetails().isEmpty()) {
            return "redirect:/cart?shippingCartEmpty=true";
        }
        Long id = orderService.save(shippingMethodId, addressId);
        if(id == -1) {
            return "redirect:/cart?orderFailed=true";
        }
        return "redirect:/cart?orderSuccess=true";
    }
    @PostMapping("/address/add")
    public String addAddress(@Valid @ModelAttribute("addressRequest") ShippingAddressRequest addressRequest,
                             BindingResult bindingResult, Model theModel
    ) {
        Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();
        if(cart.getCartDetails().isEmpty()) {
            return "redirect:/cart?shippingCartEmpty=true";
        }
        if(bindingResult.hasErrors()) {
            theModel.addAttribute("cartDetail", cart.getCartDetails());
            theModel.addAttribute("saveShippingAddressFailed", true);
            return "user/shipping/list";
        }
        shippingAddressService.save(addressRequest);
        return "redirect:/shipping?saveShippingAddressSuccess=true";
    }
    @GetMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        shippingAddressService.delete(id);
        return "redirect:/shipping?deleteShippingAddressSuccess=true";
    }
}
