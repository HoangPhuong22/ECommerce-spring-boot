package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.CartDetailRequest;
import vn.zerocoder.Mart.model.Cart;
import vn.zerocoder.Mart.model.CartDetail;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.repository.CartDetailRepository;
import vn.zerocoder.Mart.repository.CartRepository;
import vn.zerocoder.Mart.repository.ProductDetailRepository;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.service.CartDetailService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {

    private final CartDetailRepository cartDetailRepository;
    private final AuthUtils authUtils;
    private final CartRepository cartRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public Long save(Long quantity, Long productDetailId, Boolean isAdd) {

        // Lấy thông tin giỏ hàng của người dùng
        Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();

        // Lấy thông tin chi tiết sản phẩm
        ProductDetail productDetail = productDetailRepository.findById(productDetailId).orElseThrow();

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartDetail cartDetail = cartDetailRepository.findByProductDetailId(productDetailId);

        // Nếu chưa có thì tạo mới
        if(cartDetail == null) {
            cartDetail = CartDetail.builder()
                    .cart(cart)
                    .productDetail(productDetail)
                    .quantity(quantity)
                    .build();
        }  // Nếu có rồi thì cập nhật số lượng
        else {
            if(isAdd) cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            else cartDetail.setQuantity(cartDetail.getQuantity() - quantity);
            if(cartDetail.getQuantity() <= 0) {
                cartDetailRepository.delete(cartDetail);
                return 0L;
            }
        }
        if(cartDetail.getQuantity() > productDetail.getQty()) {
            return -1L;
        }
        return cartDetailRepository.save(cartDetail).getId();
    }

    @Override
    public Long delete(Long id) {
        cartDetailRepository.deleteById(id);
        return id;
    }
}
