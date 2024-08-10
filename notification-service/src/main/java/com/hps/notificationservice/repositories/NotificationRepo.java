package com.hps.notificationservice.repositories;


import com.hps.notificationservice.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
}