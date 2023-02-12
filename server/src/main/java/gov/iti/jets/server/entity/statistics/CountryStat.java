package gov.iti.jets.server.entity.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@ToString
public class CountryStat {

    private Map<String, Long> countryMap = new TreeMap<>();
}
