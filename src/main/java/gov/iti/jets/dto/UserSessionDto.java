package gov.iti.jets.dto;

import lombok.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserSessionDto implements Serializable {
    private UserDto user;
    private List<ChatDto> chatListDto;
    private List<ContactDto> contactListDto;
}
