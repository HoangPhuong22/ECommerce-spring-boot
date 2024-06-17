package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.BrandRequest;
import vn.zerocoder.Mart.dto.response.BrandResponse;
import vn.zerocoder.Mart.service.BrandService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/brands")
public class BrandAdminController {

    private final BrandService brandService;

    @GetMapping
    public String listBrand(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "admin/brand/list";
    }

    @GetMapping("/add")
    public String addBrand(Model model) {
        model.addAttribute("brand", new BrandRequest());
        return "admin/brand/add";
    }

    @PostMapping("/add")
    public String addBrand(@Valid @ModelAttribute("brand") BrandRequest brandRequest,
                           BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/brand/add";
        }
        Long id = brandService.save(brandRequest);
        if (id == -1) {
            bindingResult.rejectValue("name", "error.brand", "Tên thương hiệu đã tồn tại");
            return "admin/brand/add";
        }
        return "redirect:/admin/brands";
    }

    @GetMapping("/edit/{id}")
    public String editBrand(@PathVariable Long id, Model model) {
        BrandResponse brandResponse = brandService.findById(id);
        model.addAttribute("brand", BrandRequest.builder()
                .id(brandResponse.getId())
                .name(brandResponse.getName())
                .description(brandResponse.getDescription())
                .build());
        return "admin/brand/edit";
    }

    @PostMapping("/edit")
    public String editBrand(@Valid @ModelAttribute("brand") BrandRequest brandRequest,
                            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/brand/edit";
        }
        Long brandId = brandService.update(brandRequest);
        if (brandId == -1) {
            bindingResult.rejectValue("name", "error.brand", "Tên thương hiệu đã tồn tại");
            return "admin/brand/edit";
        }
        return "redirect:/admin/brands";
    }


    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return "redirect:/admin/brands";
    }
}
