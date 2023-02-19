package gov.iti.jets.server.mapper;


import gov.iti.jets.common.dto.NotificationDto;
import gov.iti.jets.server.entity.Notification;

import java.util.ArrayList;
import java.util.Collection;

public class NotificationMapper implements BaseMapper<Notification, NotificationDto>{
    @Override
    public NotificationDto toDTO(Notification notification) {
        return null;
    }

    @Override
    public Notification toEntity(NotificationDto notificationDto) {
        return null;
    }

    @Override
    public ArrayList<NotificationDto> toDTOs(Collection<Notification> e) {
        return null;
    }

    @Override
    public ArrayList<Notification> toEntities(Collection<NotificationDto> notificationDtos) {
        return null;
    }
}
