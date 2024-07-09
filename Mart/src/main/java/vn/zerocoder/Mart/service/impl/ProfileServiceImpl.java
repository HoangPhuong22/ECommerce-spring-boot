package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProfileRequest;
import vn.zerocoder.Mart.model.Profile;
import vn.zerocoder.Mart.repository.ProfileRepository;
import vn.zerocoder.Mart.service.ProfileService;
import vn.zerocoder.Mart.utils.FileUtils;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Value("${file.upload-dir}")
    private String Path;

    @Override
    public Long save(ProfileRequest profileRequest) {
        return 0L;
    }

    @Override
    public Long update(ProfileRequest profileRequest) {
        Long profile_id = profileRequest.getId();
        Profile profile = profileRepository.findById(profile_id).orElseThrow();

        // Xóa ảnh cũ, thêm ảnh mới
        if(profileRequest.getProfile_image() != null && !profileRequest.getProfile_image().isEmpty()) {
            String old_avatar = profile.getAvatar();
            String new_avatar = FileUtils.save(Path, profileRequest.getProfile_image());
            FileUtils.delete(Path, old_avatar);
            profile.setAvatar(new_avatar);
        }
        profile.setFirstName(profileRequest.getFirstName());
        profile.setLastName(profileRequest.getLastName());
        profile.setPhoneNumber(profileRequest.getPhoneNumber());
        profile.setDateOfBirth(profileRequest.getDateOfBirth());
        profile.setGender(profileRequest.getGender());

        return profileRepository.save(profile).getId();
    }
}
