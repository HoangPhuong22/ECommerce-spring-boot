package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.UserRequest;
import vn.zerocoder.Mart.dto.response.UserResponse;
import vn.zerocoder.Mart.service.RoleService;
import vn.zerocoder.Mart.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String listUser(Model theModel) {
        theModel.addAttribute("users", userService.findAll());
        return "admin/user/list";
    }

    @GetMapping("/add")
    public String addUser(Model theModel) {
        theModel.addAttribute("user", new UserRequest());
        return "admin/user/add";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") UserRequest userRequest,
                          BindingResult bindingResult,
                          @RequestParam("confirmPassword") String confirmPassword
    ) {
        boolean checkPassword = userRequest.getPassword().equals(confirmPassword);
        if (bindingResult.hasErrors() || !checkPassword) {
            if(!checkPassword) {
                bindingResult.rejectValue("password", "error.user", "Mật khẩu không khớp");
            }
            return "admin/user/add";
        }
        Long id = userService.save(userRequest);
        if(id == -1) {
            bindingResult.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
            return "admin/user/add";
        }
        if(id == -2) {
            bindingResult.rejectValue("email", "error.user", "Email đã tồn tại");
            return "admin/user/add";
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model theModel) {
        theModel.addAttribute("user", userService.findById(id));
        theModel.addAttribute("roles", roleService.findAll());
        return "admin/user/edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") UserRequest userRequest) {
        userService.update(userRequest);
        return "redirect:/admin/user";
    }
}
