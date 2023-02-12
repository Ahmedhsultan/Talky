package gov.iti.jets.server.entity;

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
public class User extends IUser implements BaseEntity, Serializable {
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
