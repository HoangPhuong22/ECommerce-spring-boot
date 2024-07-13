package vn.zerocoder.Mart.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.dto.request.AddressRequest;
import vn.zerocoder.Mart.dto.request.PaymentMethodRequest;
import vn.zerocoder.Mart.dto.request.ProfileRequest;
import vn.zerocoder.Mart.service.*;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final AuthUtils authUtils;
    private final ProfileService profileService;
    private final AddressService addressService;
    private final UserService userService;
    private final PaymentTypeService paymentTypeService;
    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public String profile(Model theModel) {
        CustomUserDetail user = authUtils.loadUserByUsername();
        theModel.addAttribute("user", user);
        return "user/account/view";
    }




    @GetMapping("/edit")
    public String editProfile(Model theModel) {
        ProfileRequest profile = authUtils.loadProfile();
        profile.setProfile_image(null);
        theModel.addAttribute("profileRequest", profile);
        return "user/account/edit";
    }



    @PostMapping("/edit")
    public String editProfile(@Valid @ModelAttribute("profileRequest") ProfileRequest profileRequest,
                              BindingResult bindingResult, Model theModel
    ) {
        if (bindingResult.hasErrors()) {
            return "user/account/edit";
        }
        profileService.update(profileRequest);
        return "redirect:/profile?profileSave=true";
    }



    @GetMapping("/address")
    public String address(Model theModel) {
        theModel.addAttribute("addressRequest", authUtils.loadAddress() == null ? new AddressRequest() : authUtils.loadAddress());
        return "user/address/add";
    }
    @PostMapping("/address")
    public String address(@Valid @ModelAttribute("addressRequest") AddressRequest addressRequest,
                          BindingResult bindingResult, Model theModel
    ) {
        if (bindingResult.hasErrors()) {
            return "user/address/add";
        }
        if(addressRequest.getId() == null)
            addressService.save(addressRequest);
        else
            addressService.update(addressRequest);
        return "redirect:/profile?addressSave=true";
    }



    @GetMapping("/change-password")
    public String changePassword() {
        return "user/account/change-password";
    }



    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("passwordNew") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model theModel
    ) {
        Long id = userService.changePassword(oldPassword, newPassword, confirmPassword);
        if(id < 0) {
            if(id == -1) theModel.addAttribute("oldPasswordError", "Mật khẩu cũ không đúng");
            if(id == -2) theModel.addAttribute("confirmPasswordError", "Mật khẩu mới không khớp");
            return "user/account/change-password";
        }
        return "redirect:/profile?passwordChange=true";
    }


    @GetMapping("/payment")
    public String payment(Model theModel) {
        theModel.addAttribute("paymentTypes", paymentTypeService.findAllByRequiresAccount(true));
        theModel.addAttribute("paymentRequest", new PaymentMethodRequest());
        return "user/payment/add";
    }


    @PostMapping("/payment")
    public String payment(@Valid @ModelAttribute("paymentRequest") PaymentMethodRequest request, BindingResult bindingResult, Model theModel) {
        if (bindingResult.hasErrors()) {
            theModel.addAttribute("paymentTypes", paymentTypeService.findAllByRequiresAccount(true));
            return "user/payment/add";
        }
        Long id = paymentMethodService.save(request);
        if(id < 0) {
            bindingResult.rejectValue("account_number", "error.paymentRequest", "Tài khoản đã ủy quyền cho người khác");
            theModel.addAttribute("paymentTypes", paymentTypeService.findAllByRequiresAccount(true));
            return "user/payment/add";
        }
        return "redirect:/profile?paymentSave=true";
    }


    @GetMapping("/payment/{id}")
    public String payment(@PathVariable("id") Long id, Model theModel) {
        theModel.addAttribute("paymentTypes", paymentTypeService.findAllByRequiresAccount(true));
        theModel.addAttribute("paymentRequest", paymentMethodService.findById(id));
        return "user/payment/edit";
    }


    @PostMapping("/payment/{id}")
    public String payment(@PathVariable("id") Long id, @Valid @ModelAttribute("paymentRequest") PaymentMethodRequest request, BindingResult bindingResult, Model theModel) {
        if (bindingResult.hasErrors()) {
            theModel.addAttribute("paymentTypes", paymentTypeService.findAllByRequiresAccount(true));
            return "user/payment/edit";
        }
        Long id1 = paymentMethodService.update(request);
        if(id1 < 0) {
            bindingResult.rejectValue("account_number", "error.paymentRequest", "Tài khoản đã ủy quyền cho người khác");
            theModel.addAttribute("paymentTypes", paymentTypeService.findAllByRequiresAccount(true));
            return "user/payment/edit";
        }
        return "redirect:/profile?paymentSave=true";
    }


    @GetMapping("/payment/delete/{id}")
    public String deletePayment(@PathVariable("id") Long id) {
        paymentMethodService.delete(id);
        return "redirect:/profile?paymentDelete=true";
    }

    @GetMapping("/order")
    public String order(Model theModel) {
        theModel.addAttribute("orders", authUtils.loadUserByUsername().getUserConfig().getOrders());
        return "user/order/list";
    }
}
