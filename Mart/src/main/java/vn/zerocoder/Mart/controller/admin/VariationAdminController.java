package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.VariationRequest;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.dto.response.VariationResponse;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.VariationOptionService;
import vn.zerocoder.Mart.service.VariationService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/variations")
public class VariationAdminController {
    private final CategoryService categoryService;
    private final VariationService variationService;
    private final VariationOptionService optionService;
    @GetMapping
    public String listVariation(Model theModel) {

        List<CategoryResponse> categories = categoryService.findAll();
        List<VariationResponse> variations = variationService.findAll();

        theModel.addAttribute("categories", categories);
        theModel.addAttribute("variations", variations);
        theModel.addAttribute("variationService", variationService);
        theModel.addAttribute("optionService", optionService);

        return "admin/variation/list";
    }


    @GetMapping("/add")
    public String addVariation(Model theModel) {
        theModel.addAttribute("variationRequest", new VariationRequest());
        return "admin/variation/add";
    }




    @PostMapping("/add")
    public String addVariation(@Valid @ModelAttribute("variationRequest") VariationRequest variationRequest,
                           BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            return "admin/variation/add";
        }
        Long id = variationService.save(variationRequest);
        if(id == -1) {
            bindingResult.rejectValue("name", "error.variationRequest", "Tên biến thể đã tồn tại");
            return "admin/variation/add";
        }
        return "redirect:/admin/variations";
    }



    @GetMapping("/edit/{id}")
    public String editVariation(@PathVariable Long id, Model theModel) {
        VariationResponse variation = variationService.findById(id);
        VariationRequest variationRequest = VariationRequest.builder()
                .id(variation.getId())
                .name(variation.getName())
                .options_id(variation.getOptions_id())
                .build();
        theModel.addAttribute("variationRequest", variationRequest);
        theModel.addAttribute("optionService", optionService);
        return "admin/variation/edit";
    }



    @PostMapping("/edit/{id}")
    public String editVariation(@PathVariable Long id, @Valid @ModelAttribute("variationRequest") VariationRequest variationRequest,
                            BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            theModel.addAttribute("optionService", optionService);
            return "admin/variation/edit";
        }
        Long result = variationService.update(id, variationRequest);
        if(result == -1) {
            theModel.addAttribute("optionService", optionService);
            bindingResult.rejectValue("name", "error.variationRequest", "Tên biến thể đã tồn tại");
            return "admin/variation/edit";
        }
        return "redirect:/admin/variations";
    }


    @GetMapping("/delete/{id}")
    public String deleteVariation(@PathVariable Long id) {
        variationService.delete(id);
        return "redirect:/admin/variations";
    }
}
