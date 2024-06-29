package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.PromotionRequest;
import vn.zerocoder.Mart.service.ProductService;
import vn.zerocoder.Mart.service.PromotionService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/promotion")
public class AdminPromotionController {

    private final PromotionService promotionService;
    private final ProductService productService;

    @GetMapping
    public String listPromotion(Model model) {
        model.addAttribute("promotions", promotionService.findAll());
        return "admin/promotion/list";
    }
    @GetMapping("/add")
    public String addPromotion(Model model) {
        model.addAttribute("promotion", new PromotionRequest());
        model.addAttribute("products", productService.findAll());
        return "admin/promotion/add";
    }
    @PostMapping("/add")
    public String addPromotion(@Valid @ModelAttribute("promotion") PromotionRequest promotionRequest,
                               BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("products", productService.findAll());
            return "admin/promotion/add";
        }
        promotionService.save(promotionRequest);
        return "redirect:/admin/promotion";
    }
    @GetMapping("/edit/{id}")
    public String editPromotion(@PathVariable Long id, Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("promotion", promotionService.findById(id));
        return "admin/promotion/edit";
    }
    @PostMapping("/edit")
    public String editPromotion(@Valid @ModelAttribute("promotion") PromotionRequest promotionRequest,
                                BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("products", productService.findAll());
            return "admin/promotion/edit";
        }
        Long id = promotionService.update(promotionRequest);
        if (id == -1) {
            model.addAttribute("products", productService.findAll());
            bindingResult.rejectValue("startDate", "error.promotion", "Ngày bắt đầu phải trước ngày kết thúc");
            return "admin/promotion/edit";
        }
        return "redirect:/admin/promotion";
    }

    @GetMapping("view/{id}")
    public String viewPromotion(@PathVariable Long id, Model model) {
        model.addAttribute("promotion", promotionService.findById(id));
        model.addAttribute("productService", productService);
        return "admin/promotion/view";
    }

    @GetMapping("/delete/{id}")
    public String deletePromotion(@PathVariable Long id) {
        promotionService.delete(id);
        return "redirect:/admin/promotion";
    }
}
