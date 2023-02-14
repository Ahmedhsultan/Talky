package gov.iti.jets.common.dto;

import javafx.scene.paint.Color;
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
    private int fontSize;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private String font;
    private String  textColor;
    private String  highlightColor;
    private String timestamp;
}
