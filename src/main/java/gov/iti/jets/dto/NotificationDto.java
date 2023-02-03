package gov.iti.jets.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationDto implements BaseDto{
    private int id;
    private int sender_id;
    private int receiver_id;
    private String type;
    private Date created_on;
    private String status;
    private boolean is_seen;
}
