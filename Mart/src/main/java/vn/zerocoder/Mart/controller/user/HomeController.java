package vn.zerocoder.Mart.controller.user;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.configuration.CustomUserDetailService;
import vn.zerocoder.Mart.service.BrandService;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.ProductService;
import vn.zerocoder.Mart.utils.UserDetailUtils;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final UserDetailUtils userDetailUtils;
    @GetMapping("/")
    public String home(Model theModel) {
        CustomUserDetail customUserDetail = userDetailUtils.loadUserByUsername();
        if(customUserDetail != null) {
            theModel.addAttribute("favouriteList", customUserDetail.getProductIdsFavourite());
        }
        theModel.addAttribute("products", productService.findAll());
        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);
        return "user/home";
    }
}