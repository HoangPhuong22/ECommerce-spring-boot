package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.zerocoder.Mart.dto.request.ReviewRequest;
import vn.zerocoder.Mart.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/review/save")
    public String save(@ModelAttribute("review") ReviewRequest reviewRequest) {
        reviewService.save(reviewRequest);
        return "redirect:/product/" + reviewRequest.getProduct_id();
    }
}
