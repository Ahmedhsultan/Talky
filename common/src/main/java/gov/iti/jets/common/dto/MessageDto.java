package gov.iti.jets.common.dto;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageDto implements BaseDto , Serializable {
    private String message;
    private String senderId;
}
