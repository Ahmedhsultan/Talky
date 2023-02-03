package gov.iti.jets.dto;

import gov.iti.jets.entity.Notification;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserSessionDto implements Serializable {
    private UserDto user;
    private ArrayList<ChatDto> chatListDto;
    private ArrayList<ContactDto> contactListDto;
    private ArrayList<NotificationDto> notificationListDto;
}
