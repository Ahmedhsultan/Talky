package gov.iti.jets.common.dto;

import gov.iti.jets.common.dto.Interfaces.IUser;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCardDto extends IUser  implements  BaseDto {
    private String id;
    private String name;
    private String imgPath;
    private byte[] image;
}
