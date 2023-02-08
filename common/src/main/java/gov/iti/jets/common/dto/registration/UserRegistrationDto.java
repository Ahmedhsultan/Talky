package gov.iti.jets.common.dto.registration;


import gov.iti.jets.common.dto.BaseDto;
import gov.iti.jets.common.dto.UserDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRegistrationDto implements BaseDto, Serializable {
    private UserDto userDto;
    private String password;
}
