package lld.utils;

import lld.models.Interval;

import java.util.Comparator;

public class CustomComparatorInterval implements Comparator<Interval> {
    @Override
    public int compare(Interval i1, Interval i2) {
        // if start time of both events are same, sort by end time
        if(i1.getStartTime().equals(i2.getStartTime())) {
            return i1.getEndTime().compareTo(i2.getEndTime());
        }
        // sort by ascending order of start time
        return i1.getStartTime().compareTo(i2.getStartTime());
    }
}
