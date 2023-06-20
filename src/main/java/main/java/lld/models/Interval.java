package main.java.lld.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Interval {
    private long startTime;
    private long endTime;
}
