package gov.iti.jets.server.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatUser implements BaseEntity{
    private long id;
    private String user_id;
}
