package vn.zerocoder.Mart.controller.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.utils.AuthUtils;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final AuthUtils authUtils;
    @ModelAttribute("userAdvice")
    public CustomUserDetail userAdvice() {
        return authUtils.loadUserByUsername();
    }

}