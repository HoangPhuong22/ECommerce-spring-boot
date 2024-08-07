package vn.zerocoder.Mart.controller.user;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.dto.search.ProductSearchDTO;
import vn.zerocoder.Mart.service.BrandService;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.ProductService;
import vn.zerocoder.Mart.service.SubscribedUserService;
import vn.zerocoder.Mart.utils.AuthUtils;
import vn.zerocoder.Mart.utils.SearchUtils;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final AuthUtils authUtils;
    private final SubscribedUserService subscribedUserService;


    @GetMapping("/")
    public String home(Model theModel) {
        CustomUserDetail customUserDetail = authUtils.loadUserByUsername();
        if(customUserDetail != null)
            theModel.addAttribute("favouriteList", customUserDetail.getProductIdsFavourite());
        theModel.addAttribute("products", productService.findAll());
        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);
        return "user/home";
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam("email") String email) {
        subscribedUserService.save(email);
        return "redirect:/?subscribed=true";
    }

    @GetMapping("/product")
    public String product(@RequestParam MultiValueMap<String, String> params, Model theModel) {
        ProductSearchDTO searchDTO = SearchUtils.buildSearchDTO(params);
        theModel.addAttribute("products", productService.findAllAndSearch(searchDTO));

        CustomUserDetail customUserDetail = authUtils.loadUserByUsername();
        if(customUserDetail != null) {
            theModel.addAttribute("favouriteList", customUserDetail.getProductIdsFavourite());
        }
        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);

        return "user/product/list";
    }
}