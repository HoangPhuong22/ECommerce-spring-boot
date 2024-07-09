package vn.zerocoder.Mart.controller.user;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.service.BrandService;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.ProductService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final AuthUtils authUtils;
    @GetMapping("/")
    public String home(Model theModel) {
        CustomUserDetail customUserDetail = authUtils.loadUserByUsername();
        if(customUserDetail != null) {
            theModel.addAttribute("favouriteList", customUserDetail.getProductIdsFavourite());
        }
        theModel.addAttribute("products", productService.findAll());
        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);
        return "user/home";
    }
}