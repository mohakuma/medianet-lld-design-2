package lld.utils;

import lld.models.Event;

import java.util.Comparator;

public class CustomComparatorEvent implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        // if start time of both events are same, sort by end time
        if(e1.getTimeSlot().getStartTime().equals(e2.getTimeSlot().getStartTime())) {
            return e1.getTimeSlot().getEndTime().compareTo(e2.getTimeSlot().getEndTime());
        }
        // sort by ascending order of start time
        return e1.getTimeSlot().getStartTime().compareTo(e2.getTimeSlot().getStartTime());
    }
}
