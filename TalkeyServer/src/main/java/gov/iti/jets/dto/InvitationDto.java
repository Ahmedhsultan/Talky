package gov.iti.jets.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InvitationDto implements  BaseDto, Serializable {

        private long id;
        private String senderId;
        private String receiverId;
        private String  createdOn;
        private String status;
        private boolean isSeen;
    }
