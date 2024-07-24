package vn.zerocoder.Mart.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.enums.OrderStatus;
import vn.zerocoder.Mart.model.*;
import vn.zerocoder.Mart.repository.*;
import vn.zerocoder.Mart.service.OrderService;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final AuthUtils authUtils;
    private final ShippingAddressRepository addressRepository;
    private final ShippingMethodRepository methodRepository;
    private final OrderRepository orderRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductDetailRepository productDetailRepository;

    @Override
    @Transactional
    public Long save(Long shippingMethodId, Long addressId) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        ShippingMethod method = methodRepository.findById(shippingMethodId).orElseThrow();
        ShippingAddress address = addressRepository.findById(addressId).orElseThrow();
        Cart cart = user.getCart();

        Long total = checkAndTotalQuantity(cart);
        if(total == -1L) {
            return -1L; // Trả về -1 nếu số lượng sản phẩm không đủ
        }
        updateQuantity(cart);

        // Tạo đơn hàng
        Order order = Order.builder()
                .user(user)
                .shippingMethod(method)
                .shippingAddress(address)
                .status(OrderStatus.PENDING)
                .total(total + method.getPrice())
                .build();

        // Tạo chi tiết đơn hàng và xóa chi tiết giỏ hàng
        List<OrderDetail> orderDetails = createOrderDetailAndDelteCartDetail(cart, order);

        return orderRepository.save(order).getId(); // Lưu đơn hàng và trả về ID
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    private Long checkAndTotalQuantity(Cart cart) {
        long total = 0L;
        for(CartDetail cartDetail : cart.getCartDetails()) {
            ProductDetail productDetail = cartDetail.getProductDetail();
            if(productDetail.getQty() < cartDetail.getQuantity()) {
                return -1L;
            }
            total += productDetail.getPrice() * cartDetail.getQuantity();
        }
        return total;
    }

    private void updateQuantity(Cart cart) {
        // Kiểm tra và cập nhật số lượng sản phẩm trước khi xóa chi tiết giỏ hàng
        for(CartDetail cartDetail : cart.getCartDetails()) {
            ProductDetail productDetail = cartDetail.getProductDetail();
            productDetail.setQty(productDetail.getQty() - cartDetail.getQuantity());
            productDetailRepository.save(productDetail);
        }
    }

    private List<OrderDetail> createOrderDetailAndDelteCartDetail(Cart cart, Order order) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(CartDetail cartDetail: cart.getCartDetails()) {
            cartDetailRepository.delete(cartDetail); // Xóa chi tiết giỏ hàng
            // Tạo chi tiết đơn hàng
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productDetail(cartDetail.getProductDetail())
                    .quantity(cartDetail.getQuantity())
                    .price(cartDetail.getProductDetail().getPrice())
                    .build();
            orderDetails.add(orderDetail);
        }

        cart.getCartDetails().clear(); // Xóa sạch giỏ hàng
        cartRepository.save(cart); // Lưu lại thông tin giỏ hàng

        order.setOrderDetails(orderDetails); // Gán chi tiết đơn hàng vào đơn hàng
        return orderDetails;
    }
}
