package gov.iti.jets.server.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Friends implements BaseEntity{
    private String id1;
    private String id2;
}
