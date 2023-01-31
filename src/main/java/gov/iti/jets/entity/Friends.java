package gov.iti.jets.entity;

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
