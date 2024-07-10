package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.dto.request.PaymentMethodRequest;
import vn.zerocoder.Mart.dto.response.PaymentMethodResponse;
import vn.zerocoder.Mart.model.PaymentMethod;
import vn.zerocoder.Mart.model.PaymentType;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.PaymentMethodRepository;
import vn.zerocoder.Mart.repository.PaymentTypeRepository;
import vn.zerocoder.Mart.repository.UserRepository;
import vn.zerocoder.Mart.service.PaymentMethodService;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final AuthUtils authUtils;
    private final UserRepository userRepository;


    @Override
    public Long save(PaymentMethodRequest paymentMethodRequest) {
        // Lấy thông tin loại thanh toán
        Long paymentType_id = paymentMethodRequest.getPayment_type_id();
        PaymentType paymentType = paymentTypeRepository.findById(paymentType_id).orElseThrow();

        User user = authUtils.loadUserByUsername().getUserConfig();

        // Tạo mới phương thức thanh toán
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .accountName(paymentMethodRequest.getAccount_name())
                .accountNumber(paymentMethodRequest.getAccount_number())
                .provider(paymentMethodRequest.getProvider())
                .paymentType(paymentType)
                .expired(paymentMethodRequest.getExpired())
                .user(user)
                .build();
        if(paymentMethodRepository.existsByAccountNumber(paymentMethodRequest.getAccount_number())){
            return -1L;
        }
        return paymentMethodRepository.save(paymentMethod).getId();
    }

    @Override
    public Long update(PaymentMethodRequest paymentMethodRequest) {
        Long id = paymentMethodRequest.getId();
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow();

        User user = authUtils.loadUserByUsername().getUserConfig();
        if(!paymentMethod.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Không thể xóa phương thức thanh toán của người khác");
        }

        paymentMethod.setAccountName(paymentMethodRequest.getAccount_name());
        paymentMethod.setAccountNumber(paymentMethodRequest.getAccount_number());
        paymentMethod.setProvider(paymentMethodRequest.getProvider());
        paymentMethod.setExpired(paymentMethodRequest.getExpired());

        if(paymentMethodRepository.existsByAccountNumberAndIdNot(paymentMethodRequest.getAccount_number(), id)){
            return -1L;
        }
        return paymentMethodRepository.save(paymentMethod).getId();
    }

    @Override
    public void delete(Long id) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow();
        if(!paymentMethod.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Không thể xóa phương thức thanh toán của người khác");
        }
        user.getPaymentMethods().remove(paymentMethod);
        userRepository.save(user);
        paymentMethodRepository.deleteById(id);
    }

    @Override
    public List<PaymentMethodResponse> findAll() {
        return paymentMethodRepository.findAll().stream()
                .map(
                        paymentMethod -> PaymentMethodResponse.builder()
                                .id(paymentMethod.getId())
                                .provider(paymentMethod.getProvider())
                                .account_number(paymentMethod.getAccountNumber())
                                .account_name(paymentMethod.getAccountName())
                                .payment_type_id(paymentMethod.getPaymentType().getId())
                                .expired(paymentMethod.getExpired())
                                .build()
                )
                .toList();
    }

    @Override
    public PaymentMethodResponse findById(Long id) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow();

        if(!paymentMethod.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Không thể xem phương thức thanh toán của người khác");
        }

        return PaymentMethodResponse.builder()
                .id(paymentMethod.getId())
                .provider(paymentMethod.getProvider())
                .account_number(paymentMethod.getAccountNumber())
                .account_name(paymentMethod.getAccountName())
                .payment_type_id(paymentMethod.getPaymentType().getId())
                .expired(paymentMethod.getExpired())
                .build();
    }
}
