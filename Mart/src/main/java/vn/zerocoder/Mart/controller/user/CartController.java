package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.model.Cart;
import vn.zerocoder.Mart.service.CartDetailService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartDetailService cartDetailService;
    private final AuthUtils authUtils;

    @GetMapping
    public String index(Model theModel) {
        Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();
        theModel.addAttribute("cartDetail", cart.getCartDetails());
        return "user/cart/list";
    }

    @PostMapping("/add")
    @ResponseBody
    public Long addToCart(@RequestParam("productDetailId") Long productDetailId,
                             @RequestParam("quantity") Long quantity,
                             @RequestParam("isAdd") Integer isAdd
    ) {
        return cartDetailService.save(quantity, productDetailId, isAdd);
    }

}
