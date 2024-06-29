package vn.zerocoder.Mart.controller.user;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.zerocoder.Mart.service.BrandService;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.ProductService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/")
    public String home(Model theModel) {
        theModel.addAttribute("products", productService.findAll());
        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);
        return "user/home";
    }
}