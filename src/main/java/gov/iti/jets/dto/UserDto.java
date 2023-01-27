package gov.iti.jets.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements BaseDto {

    private String phoneNumber;
    private String name;
    private String email;
    private String image;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private boolean isOnlineStatus;
    private boolean botMode;

}
