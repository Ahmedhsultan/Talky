package gov.iti.jets.common.dto;

import gov.iti.jets.common.dto.BaseDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationDto implements BaseDto, Serializable {
    private int id;
    private int sender_id;
    private int receiver_id;
    private String type;
    private Date created_on;
    private String status;
    private boolean is_seen;
    private String name;
}
