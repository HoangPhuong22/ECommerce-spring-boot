package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.model.SubscribedUser;
import vn.zerocoder.Mart.repository.SubscribedUserRepository;
import vn.zerocoder.Mart.service.SubscribedUserService;

@Service
@RequiredArgsConstructor
public class SubscribedUserServiceImpl implements SubscribedUserService {

    private final SubscribedUserRepository subscribedUserRepository;

    @Override
    public Long save(String email) {
        if(subscribedUserRepository.existsByEmail(email)) {
            return -1L;
        }
        SubscribedUser subscribedUser = SubscribedUser.builder()
                .email(email)
                .build();
        return subscribedUserRepository.save(subscribedUser).getId();
    }
}
