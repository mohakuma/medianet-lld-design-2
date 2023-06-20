package main.java.lld.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Getter
@Setter
//@Builder
public class Calender {
    private int id;

    private Map<Long, Set<Integer>> startTimeToEventIdSetMap = new TreeMap<>();
    private List<Interval> bookedslots = new ArrayList<>(); // user accepts
}
