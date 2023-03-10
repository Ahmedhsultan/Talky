package gov.iti.jets.common.dto;


import gov.iti.jets.common.dto.Interfaces.IUser;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto extends IUser implements BaseDto, Serializable {

    private String id;
    private String name;
    private String email;
    private String imgPath;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private String isOnlineStatus;
    private boolean botMode;
    private String bio;
    private byte[] image;


}
