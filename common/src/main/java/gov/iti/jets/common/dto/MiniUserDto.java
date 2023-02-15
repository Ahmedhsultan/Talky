package gov.iti.jets.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class MiniUserDto implements   BaseDto{

    private String id;
    private String name;
    private String email;
    private String gender;
    private String country;
    private String isOnlineStatus;

}
