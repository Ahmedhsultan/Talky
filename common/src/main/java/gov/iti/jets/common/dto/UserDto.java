package gov.iti.jets.common.dto;


import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString
public class UserDto implements BaseDto, Serializable {

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

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id +
                '}';
    }
}
