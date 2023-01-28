package gov.iti.jets.entity;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User implements BaseEntity{
    private String phoneNumber;
    private String name;
    private String email;
    private String picture;
    private String password;
    private String sultPassword;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private Date createdOn;
    private String isOnlineStatus;
    private String botMode;
}
