package gov.iti.jets.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatDto implements Serializable {
    private int id;
    private String name;
    private String picture_icon;
}
