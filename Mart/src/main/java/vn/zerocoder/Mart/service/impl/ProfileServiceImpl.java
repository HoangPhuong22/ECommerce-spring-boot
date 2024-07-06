package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProfileRequest;
import vn.zerocoder.Mart.service.ProfileService;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    @Override
    public Long save(ProfileRequest profileRequest) {
        return 0L;
    }

    @Override
    public Long update(ProfileRequest profileRequest) {
        return 0L;
    }
}
