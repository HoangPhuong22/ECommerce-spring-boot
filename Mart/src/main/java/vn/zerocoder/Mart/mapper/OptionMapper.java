package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.VariationOptionRequest;
import vn.zerocoder.Mart.dto.response.VariationOptionResponse;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.model.VariationOption;

@Component
public class OptionMapper {

    public VariationOption toEntity(VariationOptionRequest request, Variation variation) {
        return VariationOption.builder()
                .value(request.getValue())
                .variation(variation)
                .build();
    }

    public VariationOptionResponse toResponse(VariationOption option) {
        return VariationOptionResponse.builder()
                .id(option.getId())
                .value(option.getValue())
                .variation_id(option.getVariation().getId())
                .build();
    }
}
