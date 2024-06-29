package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.AdvertiseRequest;
import vn.zerocoder.Mart.dto.response.AdvertiseResponse;
import vn.zerocoder.Mart.service.AdvertiseService;
import vn.zerocoder.Mart.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/advertise")
public class AdminAvertiseController {

    private final AdvertiseService advertiseService;
    private final ProductService productService;

    @GetMapping("/add")
    public String addAdvertise(@RequestParam("product_id") Long product_id, Model theModel) {
        AdvertiseRequest advertiseRequest = new AdvertiseRequest();
        advertiseRequest.setProduct_id(product_id);
        theModel.addAttribute("advertise", advertiseRequest);
        return "admin/advertise/add";
    }
    @PostMapping("/add")
    public String addAdvertise(@Valid @ModelAttribute("advertise") AdvertiseRequest advertiseRequest,
                                BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "admin/advertise/add";
        }
        advertiseService.save(advertiseRequest);
        return "redirect:/admin/products/view/" + advertiseRequest.getProduct_id();
    }
    @GetMapping("/edit/{id}")
    public String editAdvertise(@PathVariable("id") Long id, Model theModel) {
        AdvertiseResponse advertiseResponse = advertiseService.findById(id);
        AdvertiseRequest advertiseRequest = AdvertiseRequest.builder()
                .id(advertiseResponse.getId())
                .bannerImage(advertiseResponse.getBannerImage())
                .product_id(advertiseResponse.getProduct_id())
                .build();
        theModel.addAttribute("advertise", advertiseRequest);
        return "admin/advertise/edit";
    }
    @PostMapping("/edit")
    public String editAdvertise(@Valid @ModelAttribute("advertise") AdvertiseRequest advertiseRequest,
                                BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "admin/advertise/edit";
        }
        advertiseService.update(advertiseRequest);
        return "redirect:/admin/products/view/" + advertiseRequest.getProduct_id();
    }
    @GetMapping("/delete/{id}")
    public String deleteAdvertise(@PathVariable("id") Long id){
        advertiseService.delete(id);
        return "redirect:/admin/products";
    }
}
