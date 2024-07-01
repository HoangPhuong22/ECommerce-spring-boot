package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.SpecRequest;
import vn.zerocoder.Mart.dto.response.SpecResponse;
import vn.zerocoder.Mart.model.Spec;
import vn.zerocoder.Mart.utils.Normalizer;

@Component
public class SpecMapper {
    public SpecResponse toResponse(Spec spec) {
        return SpecResponse.builder()
                .id(spec.getId())
                .name(spec.getName())
                .description(spec.getDescription())
                .build();
    }

    public Spec toEntity(SpecRequest specRequest) {
        return Spec.builder()
                .name(Normalizer.titleNormalize(specRequest.getName()))
                .description(specRequest.getDescription())
                .build();
    }
}
