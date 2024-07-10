package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.zerocoder.Mart.service.CartDetailService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartDetailService cartDetailService;
    private AuthUtils authUtils;

    @PostMapping("/add")
    @ResponseBody
    public Long addToCart(@RequestParam("productDetailId") Long productDetailId,
                             @RequestParam("quantity") Long quantity,
                             @RequestParam("isAdd") Boolean isAdd
    ) {
        return cartDetailService.save(quantity, productDetailId, isAdd);
    }

}
