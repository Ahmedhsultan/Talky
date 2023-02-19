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
public class NotificationDto implements BaseDto, Serializable {
    private String type;
    private long chatId;
    private byte[] bytes;
    private String name;
    private String msg;
    private String onlineStatus;
}
