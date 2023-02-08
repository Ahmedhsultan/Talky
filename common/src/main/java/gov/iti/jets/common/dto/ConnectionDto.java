package gov.iti.jets.common.dto;


import gov.iti.jets.common.network.IClient;
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
