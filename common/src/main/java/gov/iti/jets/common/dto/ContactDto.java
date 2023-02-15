package gov.iti.jets.common.dto;

import gov.iti.jets.common.dto.Interfaces.IUser;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactDto extends IUser implements Serializable {
    private String id;
    private String name;
    private String imgPath;
    private String isOnlineStatus;
    private String bio;
    private byte[] image;
    private boolean botMode;
}
