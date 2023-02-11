package gov.iti.jets.common.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCardDto  implements  BaseDto{
    private String id;
    private String name;
    private String imgPath;
    private byte[] image;
}
