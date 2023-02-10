package gov.iti.jets.common.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatUserDto implements BaseDto , Serializable {

    private long id;
    private String userId;


}
