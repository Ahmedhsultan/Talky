package gov.iti.jets.server.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notification implements BaseEntity {
    private int id;
    private int sender_id;
    private int receiver_id;
    private String type;
    private Date created_on;
    private String status;
    private boolean is_seen;
}
