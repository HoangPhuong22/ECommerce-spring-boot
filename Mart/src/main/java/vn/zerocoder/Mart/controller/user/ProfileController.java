package vn.zerocoder.Mart.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.dto.request.AddressRequest;
import vn.zerocoder.Mart.dto.request.ProfileRequest;
import vn.zerocoder.Mart.service.AddressService;
import vn.zerocoder.Mart.service.ProfileService;
import vn.zerocoder.Mart.service.UserService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final AuthUtils authUtils;
    private final ProfileService profileService;
    private final AddressService addressService;
    private final UserService userService;

    @GetMapping
    public String profile(Model theModel) {
        CustomUserDetail user = authUtils.loadUserByUsername();
        theModel.addAttribute("user", user);
        return "user/profile/profile";
    }
    @GetMapping("/edit")
    public String editProfile(Model theModel) {
        ProfileRequest profile = authUtils.loadProfile();
        profile.setProfile_image(null);
        theModel.addAttribute("profileRequest", profile);
        return "user/profile/edit-profile";
    }
    @PostMapping("/edit")
    public String editProfile(@Valid @ModelAttribute("profileRequest") ProfileRequest profileRequest,
                              BindingResult bindingResult, Model theModel
    ) {
        if (bindingResult.hasErrors()) {
            return "user/profile/edit-profile";
        }
        profileService.update(profileRequest);
        return "redirect:/profile?profileSave=true";
    }
    @GetMapping("/address")
    public String address(Model theModel) {
        theModel.addAttribute("addressRequest", authUtils.loadAddress() == null ? new AddressRequest() : authUtils.loadAddress());
        return "user/profile/address";
    }
    @PostMapping("/address")
    public String address(@Valid @ModelAttribute("addressRequest") AddressRequest addressRequest,
                          BindingResult bindingResult, Model theModel
    ) {
        if (bindingResult.hasErrors()) {
            return "user/profile/address";
        }
        if(addressRequest.getId() == null)
            addressService.save(addressRequest);
        else
            addressService.update(addressRequest);
        return "redirect:/profile?addressSave=true";
    }
    @GetMapping("/change-password")
    public String changePassword() {
        return "user/profile/change-password";
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
            return "user/profile/change-password";
        }
        return "redirect:/profile?passwordChange=true";
    }
}
