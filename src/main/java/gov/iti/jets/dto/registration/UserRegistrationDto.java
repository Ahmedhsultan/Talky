package gov.iti.jets.dto.registration;

import gov.iti.jets.dto.BaseDto;
import gov.iti.jets.dto.UserDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRegistrationDto  {
    private UserDto userDto;
    private String password;
}
