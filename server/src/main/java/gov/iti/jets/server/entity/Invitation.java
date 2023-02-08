package gov.iti.jets.server.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Invitation implements BaseEntity{

    private long id;
    private String senderId;
    private String receiverId;
    private Date createdOn;
    private String status;
    private boolean isSeen;
}
