package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.dto.request.PromotionRequest;
import vn.zerocoder.Mart.service.PromotionService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/promotion")
public class AdminPromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public String listPromotion(Model model) {
        model.addAttribute("promotions", promotionService.findAll());
        return "admin/promotion/list";
    }
    @GetMapping("/add")
    public String addPromotion(Model model) {
        model.addAttribute("promotion", new PromotionRequest());
        return "admin/promotion/add";
    }
    @PostMapping("/add")
    public String addPromotion(@Valid @ModelAttribute("promotion") PromotionRequest promotionRequest,
                               BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/promotion/add";
        }
        promotionService.save(promotionRequest);
        return "redirect:/admin/promotion";
    }
}
