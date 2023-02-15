package gov.iti.jets.server.entity.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserStatusStat {
    private  long numOfOnline;
    private  long numOfOffline;
    private  long total;
}
