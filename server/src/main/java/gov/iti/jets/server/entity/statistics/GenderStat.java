package gov.iti.jets.server.entity.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GenderStat {
    private  long numOfMales;
    private  long numOfFemales;
    private  long total;

}
