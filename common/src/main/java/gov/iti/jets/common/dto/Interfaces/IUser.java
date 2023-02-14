package gov.iti.jets.common.dto.Interfaces;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IUser implements Serializable {
    private String id;
    private String name;
    private String email;
    private String imgPath;
    private String password;
    private String sultPassword;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private Date createdOn;
    private String isOnlineStatus;
    private boolean botMode;
    private String bio;
}
