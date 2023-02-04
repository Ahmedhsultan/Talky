package gov.iti.jets.dto;

import gov.iti.jets.network.IClient;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ConnectionDto implements Serializable {
    private UserDto userDto;
    private IClient iClient;
}
