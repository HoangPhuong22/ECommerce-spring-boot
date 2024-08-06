package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.model.Notification;
import vn.zerocoder.Mart.model.SubscribedUser;
import vn.zerocoder.Mart.repository.NotificationRepository;
import vn.zerocoder.Mart.repository.SubscribedUserRepository;
import vn.zerocoder.Mart.service.EmailService;
import vn.zerocoder.Mart.service.NotificationService;
import vn.zerocoder.Mart.service.SubscribedUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final SubscribedUserRepository subscribedUserRepository;

    @Override
    public Long save(String title, String content) {
        List<SubscribedUser> subscribedUsers = subscribedUserRepository.findAll();
        Notification notification = Notification.builder()
                .title(title)
                .message(content)
                .subscribedUsers(subscribedUsers)
                .build();
        for(SubscribedUser subscribedUser : subscribedUsers) {
            emailService.sendSimpleMessage(subscribedUser.getEmail(), title, content);
        }
        return notificationRepository.save(notification).getId();
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }
}
