package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.SpecRequest;
import vn.zerocoder.Mart.dto.response.SpecResponse;
import vn.zerocoder.Mart.model.Spec;
import vn.zerocoder.Mart.repository.SpecRepository;
import vn.zerocoder.Mart.service.SpecService;
import vn.zerocoder.Mart.utils.Normalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecServiceImpl implements SpecService {

    private final SpecRepository specRepository;

    @Override
    public Long save(SpecRequest specRequest) {
        Spec spec = Spec.builder()
                .name(Normalizer.titleNormalize(specRequest.getName()))
                .description(specRequest.getDescription())
                .build();
        if(specRepository.existsByName(spec.getName())) {
            return -1L;
        }
        return specRepository.save(spec).getId();
    }

    @Override
    public Long update(SpecRequest specRequest) {
        Long spec_id = specRequest.getId();
        Spec spec = specRepository.findById(spec_id).orElseThrow();
        spec.setName(Normalizer.titleNormalize(specRequest.getName()));
        spec.setDescription(specRequest.getDescription());
        if(specRepository.existsByName(spec.getName())) {
            return -1L;
        }
        return specRepository.save(spec).getId();
    }

    @Override
    public Long delete(Long id) {
        Spec spec = specRepository.findById(id).orElseThrow();
        specRepository.delete(spec);
        return id;
    }

    @Override
    public SpecResponse findById(Long id) {
        Spec spec = specRepository.findById(id).orElseThrow();
        return SpecResponse.builder()
                .id(spec.getId())
                .name(spec.getName())
                .description(spec.getDescription())
                .build();
    }

    @Override
    public List<SpecResponse> findAll() {
        return specRepository.findAll().stream()
                .map(spec -> SpecResponse.builder()
                        .id(spec.getId())
                        .name(spec.getName())
                        .description(spec.getDescription())
                        .build())
                .toList();
    }
}
