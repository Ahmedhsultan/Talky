package gov.iti.jets.server.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Chat implements BaseEntity{
    private long id;
    private String name;
    private String imgPath;
    private Date created_on;
    private Date modified_on;
}