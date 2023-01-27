package gov.iti.jets.entity;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements BaseEntity{
    private String phoneNumber;
    private String name;
    private String email;
    private String image;
    private String password;
    private String sultPassword;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private Date createdOn;
    private boolean isOnlineStatus;
    private boolean botMode;
}
