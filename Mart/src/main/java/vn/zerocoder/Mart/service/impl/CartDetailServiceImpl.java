package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.model.Cart;
import vn.zerocoder.Mart.model.CartDetail;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.repository.CartDetailRepository;
import vn.zerocoder.Mart.repository.CartRepository;
import vn.zerocoder.Mart.repository.ProductDetailRepository;
import vn.zerocoder.Mart.service.CartDetailService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {

    private final CartDetailRepository cartDetailRepository;
    private final AuthUtils authUtils;
    private final CartRepository cartRepository;
    private final ProductDetailRepository productDetailRepository;

    @Override
    public Long save(Long quantity, Long productDetailId, Integer isAdd) {

        // Lấy thông tin giỏ hàng của người dùng
        Cart cart = authUtils.loadUserByUsername().getUserConfig().getCart();

        // Lấy thông tin chi tiết sản phẩm
        ProductDetail productDetail = productDetailRepository.findById(productDetailId).orElseThrow();

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartDetail cartDetail = cartDetailRepository.findByProductDetailIdAndCartId(productDetailId, cart.getId());

        // Nếu chưa có thì tạo mới
        if(cartDetail == null) {
            cartDetail = CartDetail.builder()
                    .cart(cart)
                    .productDetail(productDetail)
                    .quantity(quantity)
                    .build();
        }  // Nếu có rồi thì cập nhật số lượng
        else {
            // Nếu isAdd = 1 thì tăng số lượng sản phẩm
            if(isAdd == 1) {
                cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            }
            // Nếu isAdd = -1 thì xoá sản phẩm khỏi giỏ hàng
            else if(isAdd == -1) {
                delete(cart, cartDetail);
                return 0L;
            }
            else {
                cartDetail.setQuantity(cartDetail.getQuantity() - quantity);
            }

            // Nếu số lượng sản phẩm bằng 0 thì xóa sản phẩm khỏi giỏ hàng
            if(cartDetail.getQuantity() <= 0) {
                delete(cart, cartDetail);
                return 0L; // Trả về 0 thông bá xóa sản phẩm khỏi giỏ hàng
            }
        }
        if(cartDetail.getQuantity() > productDetail.getQty()) {
            return -1L; // Trả về -1 nếu số lượng sản phẩm vượt quá số lượng tồn kho
        }
        return cartDetailRepository.save(cartDetail).getId();
    }

    public void delete(Cart cart, CartDetail cartDetail) {
        cart.getCartDetails().remove(cartDetail);
        cartRepository.save(cart);
        cartDetailRepository.delete(cartDetail);
    }
}
