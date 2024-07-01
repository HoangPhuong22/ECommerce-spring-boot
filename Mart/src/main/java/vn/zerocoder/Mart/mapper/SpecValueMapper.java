package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.SpecValueRequest;
import vn.zerocoder.Mart.dto.response.SpecValueResponse;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.Spec;
import vn.zerocoder.Mart.model.SpecValue;

@Component
public class SpecValueMapper {

    public SpecValue toEntity(SpecValueRequest request, Product product, Spec spec) {
        return SpecValue.builder()
                .value(request.getValue())
                .product(product)
                .spec(spec)
                .build();
    }
    public SpecValueResponse toResponse(SpecValue value) {
        return SpecValueResponse.builder()
                .id(value.getId())
                .value(value.getValue())
                .product_id(value.getProduct().getId())
                .spec_id(value.getSpec().getId())
                .build();
    }
}
