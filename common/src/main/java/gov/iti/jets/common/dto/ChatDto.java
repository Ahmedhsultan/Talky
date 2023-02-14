package gov.iti.jets.common.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatDto implements  BaseDto, Serializable{
    private long id;
    private String name;
    private String imgPath;
    private Date modified_on;
    private byte[] image;
    private String type;
    private ArrayList<String> membersIds;
}
