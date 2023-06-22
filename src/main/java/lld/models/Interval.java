package lld.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Interval {
    private Long startTime;
    private Long endTime;
}
