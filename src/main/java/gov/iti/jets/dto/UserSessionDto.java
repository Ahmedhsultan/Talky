package gov.iti.jets.dto;

import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;

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
    private ArrayList<InvitationDto> invitationListDto;
}
