package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.SpecValueRequest;
import vn.zerocoder.Mart.dto.response.SpecResponse;
import vn.zerocoder.Mart.dto.response.SpecValueResponse;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.service.*;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/spec-value")
public class AdminSpecValueController {

    private final SpecService specService;
    private final SpecValueService valueService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/add")
    public String addSpecValue(@RequestParam("product_id") Long product_id, Model theModel) {

        SpecValueRequest spec = new SpecValueRequest();
        spec.setProduct_id(product_id);
        theModel.addAttribute("specValue", spec);

        addAttributesToModel(product_id, theModel);

        return "admin/spec_value/add";
    }
    @PostMapping("/add")
    public String addSpecValue(@Valid @ModelAttribute("specValue") SpecValueRequest spec,
                               BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            addAttributesToModel(spec.getProduct_id(), theModel);
            return "admin/spec_value/add";
        }
        Long id = valueService.save(spec);
        if(id == -1) {
            addAttributesToModel(spec.getProduct_id(), theModel);
            bindingResult.rejectValue("value", "error.spec", "Đã thêm thông số kĩ thuật này cho sản phẩm rồi, vui lòng không thêm trùng");
            return "admin/spec_value/add";
        }
        return "redirect:/admin/products/view/" + spec.getProduct_id();
    }

    @GetMapping("/edit/{id}")
    public String editSpecValue(@PathVariable Long id, Model theModel) {
        SpecValueResponse response = valueService.findById(id);
        SpecValueRequest spec = SpecValueRequest.builder()
                .id(response.getId())
                .product_id(response.getProduct_id())
                .spec_id(response.getSpec_id())
                .value(response.getValue())
                .build();
        theModel.addAttribute("specValue", spec);
        addAttributesToModel(spec.getProduct_id(), theModel);
        return "admin/spec_value/edit";
    }
    @PostMapping("/edit")
    public String editSpecValue(@Valid @ModelAttribute("specValue") SpecValueRequest spec,
                                BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            addAttributesToModel(spec.getProduct_id(), theModel);
            return "admin/spec_value/edit";
        }
        Long id = valueService.update(spec);
        if(id == -1) {
            addAttributesToModel(spec.getProduct_id(), theModel);
            bindingResult.rejectValue("value", "error.spec", "Đã thêm thông số kĩ thuật này cho sản phẩm rồi, vui lòng không thêm trùng");
            return "admin/spec_value/edit";
        }
        return "redirect:/admin/products/view/" + spec.getProduct_id();
    }

    @GetMapping("/delete/{id}")
    public String deleteSpecValue(@PathVariable Long id) {
        Long product_id = valueService.findById(id).getProduct_id();
        valueService.delete(id);
        return "redirect:/admin/products/view/" + product_id;
    }

    private void addAttributesToModel(Long product_id, Model theModel) {

        theModel.addAttribute("product", productService.findById(product_id));
        theModel.addAttribute("specs", specService.findAll());

        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);
    }
}
