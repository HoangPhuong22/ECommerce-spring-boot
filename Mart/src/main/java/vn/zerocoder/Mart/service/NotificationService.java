package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.model.Notification;

import java.util.List;

public interface NotificationService {
    Long save(String title, String content);
    void delete(Long id);
    List<Notification> findAll();
}
