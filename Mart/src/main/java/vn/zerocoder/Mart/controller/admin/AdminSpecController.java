package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.SpecRequest;
import vn.zerocoder.Mart.dto.response.SpecResponse;
import vn.zerocoder.Mart.service.SpecService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/specs")
public class AdminSpecController {

    private final SpecService specService;

    @GetMapping
    public String listSpec(Model theModel) {
        theModel.addAttribute("specs", specService.findAll());
        return "admin/spec/list";
    }

    @GetMapping("/add")
    public String addSpec(Model theModel) {
        theModel.addAttribute("spec", new SpecRequest());
        return "admin/spec/add";
    }
    @PostMapping("/add")
    public String addSpec(@Valid @ModelAttribute("spec") SpecRequest spec, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/spec/add";
        }
        specService.save(spec);
        return "redirect:/admin/specs";
    }
    @GetMapping("/edit/{id}")
    public String editSpec(@PathVariable Long id, Model theModel) {
        SpecResponse response = specService.findById(id);
        SpecRequest spec = SpecRequest.builder()
                .id(response.getId())
                .name(response.getName())
                .description(response.getDescription())
                .build();
        theModel.addAttribute("spec", spec);
        return "admin/spec/edit";
    }
    @PostMapping("/edit")
    public String editSpec(@Valid @ModelAttribute("spec") SpecRequest spec, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/spec/edit";
        }
        specService.update(spec);
        return "redirect:/admin/specs";
    }
    @GetMapping("/delete/{id}")
    public String deleteSpec(@PathVariable Long id) {
        specService.delete(id);
        return "redirect:/admin/specs";
    }
}
