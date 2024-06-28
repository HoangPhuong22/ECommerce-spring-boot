package vn.zerocoder.Mart.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.VariationOptionRequest;
import vn.zerocoder.Mart.dto.response.VariationOptionResponse;
import vn.zerocoder.Mart.service.VariationOptionService;
import vn.zerocoder.Mart.service.VariationService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/options")
public class AdminVariationOptionController {
    private final VariationOptionService optionService;
    private final VariationService variationService;
    @GetMapping("/add")
    public String addOption(@RequestParam("variation_id") Long id, Model theModel) {
        VariationOptionRequest optionRequest = new VariationOptionRequest();
        optionRequest.setVariation_id(id);
        theModel.addAttribute("optionRequest", optionRequest);
        theModel.addAttribute("variation", variationService.findById(id).getName());
        return "admin/variation_option/add";
    }
    @PostMapping("/add")
    public String addOption(@RequestParam("variation_id") Long id, @ModelAttribute("optionRequest") VariationOptionRequest optionRequest,
                            BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            return "admin/variation_option/add";
        }
        Long result = optionService.save(optionRequest);
        if(result == -1) {
            theModel.addAttribute("variation", variationService.findById(id).getName());
            bindingResult.rejectValue("value", "error.optionRequest", "Giá trị đã tồn tại");
            return "admin/variation_option/add";
        }
        return "redirect:/admin/variations";
    }
    @GetMapping("/edit/{id}")
    public String editOption(@PathVariable Long id, Model theModel) {
        VariationOptionResponse option = optionService.findById(id);
        theModel.addAttribute("optionRequest", option);
        theModel.addAttribute("variation", variationService.findById(option.getVariation_id()).getName());
        return "admin/variation_option/edit";
    }
    @PostMapping("/edit/{id}")
    public String editOption(@PathVariable Long id, @ModelAttribute("optionRequest") VariationOptionRequest optionRequest,
                             BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            return "admin/variation_option/edit";
        }
        Long result = optionService.update(id, optionRequest);
        if(result == -1) {
            theModel.addAttribute("variation", variationService.findById(optionRequest.getVariation_id()).getName());
            bindingResult.rejectValue("value", "error.optionRequest", "Giá trị đã tồn tại");
            return "admin/variation_option/edit";
        }
        return "redirect:/admin/variations";
    }
    @GetMapping("/delete/{id}")
    public String deleteOption(@PathVariable Long id) {
        optionService.delete(id);
        return "redirect:/admin/variations";
    }
}
