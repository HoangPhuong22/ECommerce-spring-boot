package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.VariationRequest;
import vn.zerocoder.Mart.dto.response.VariationResponse;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.model.VariationOption;
import vn.zerocoder.Mart.utils.Normalizer;

@Component
public class VariationMapper {
    public Variation toEntity(VariationRequest variationRequest) {
        return Variation.builder()
                .name(Normalizer.nameNormalize(variationRequest.getName()))
                .build();
    }

    public VariationResponse toResponse(Variation variation) {
        return VariationResponse.builder()
                .id(variation.getId())
                .name(variation.getName())
                .options_id(variation.getOptions().stream().map(VariationOption::getId).toList())
                .build();
    }
}
