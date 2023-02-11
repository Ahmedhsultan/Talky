package gov.iti.jets.common.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatDto implements  BaseDto{
    private long id;
    private String name;
    private String imgPath;
    private Date modified_on;
    private byte[] image;
}