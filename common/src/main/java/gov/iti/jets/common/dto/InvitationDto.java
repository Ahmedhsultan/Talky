package gov.iti.jets.common.dto;

//import gov.iti.jets.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InvitationDto implements  BaseDto, Serializable {

    private long id;
    private String receiverId;
    private  UserCardDto userCardDto;
    private Date createdOn;
    private String status;
    private boolean isSeen;
}
