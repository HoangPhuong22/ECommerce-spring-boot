package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.service.FavouriteService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("favourites", favouriteService.findAllFavouriteByUserName());
        return "user/favourite/list";
    }

    @PostMapping("/toggle")
    @ResponseBody
    public String toogleFavourite(@RequestParam("productId") Long productId) {
        return favouriteService.toggleFavourite(productId);
    }
}
