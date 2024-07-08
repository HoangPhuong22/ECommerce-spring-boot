package vn.zerocoder.Mart.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.model.Favourite;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.FavouriteRepository;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.repository.UserRepository;
import vn.zerocoder.Mart.service.FavouriteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final ProductRepository productRepository;
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String toggleFavourite(Long productId) {
        // Lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByUsername(userName).orElseThrow();
        // Lấy thông tin sản phẩm
        Product product = productRepository.findById(productId).orElseThrow();
        // Kiểm tra xem sản phẩm đã được yêu thích chưa
        Favourite favourite_check = favouriteRepository.findByProductIdAndUserId(productId, user.getId());

        if (favourite_check != null) {
            favouriteRepository.delete(favourite_check);
            user.getFavourites().remove(favourite_check);
            userRepository.save(user);
            return "success";
        }
        // Tạo mới một bản ghi yêu thích
        Favourite favourite = Favourite.builder()
                .user(user)
                .product(product)
                .build();
        favouriteRepository.save(favourite);
        return "success";
    }

    @Override
    public List<Favourite> findAllFavouriteByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return favouriteRepository.findAllFavouriteByUserDetail(userName);
    }
}
