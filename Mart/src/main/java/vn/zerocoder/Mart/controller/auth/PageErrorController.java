package vn.zerocoder.Mart.controller.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "layout/error/404";
    }
    @GetMapping("/403")
    public String accessDenied() {
        throw new AccessDeniedException("Không có quyền truy cập");
    }
}
