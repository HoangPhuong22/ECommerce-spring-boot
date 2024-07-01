package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.SpecRequest;
import vn.zerocoder.Mart.dto.response.SpecResponse;
import vn.zerocoder.Mart.mapper.SpecMapper;
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
    private final SpecMapper specMapper;
    @Override
    public Long save(SpecRequest specRequest) {
        // Tạo spec mới
        Spec spec = specMapper.toEntity(specRequest);

        // Kiểm tra xem spec có tồn tại không
        if(specRepository.existsByName(spec.getName())) {
            return -1L;
        }
        return specRepository.save(spec).getId();
    }

    @Override
    public Long update(SpecRequest specRequest) {
        // Lấy spec cần update
        Long spec_id = specRequest.getId();
        Spec spec = specRepository.findById(spec_id).orElseThrow();

        // Cập nhật thông tin spec
        spec.setName(Normalizer.titleNormalize(specRequest.getName()));
        spec.setDescription(specRequest.getDescription());

        // Kiểm tra xem spec có tồn tại không
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
        return specMapper.toResponse(spec);
    }

    @Override
    public List<SpecResponse> findAll() {
        return specRepository.findAll().stream().map(specMapper::toResponse).toList();
    }
}
