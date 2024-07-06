package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.ProfileRequest;

public interface ProfileService {
    Long save(ProfileRequest profileRequest);
    Long update(ProfileRequest profileRequest);
}
