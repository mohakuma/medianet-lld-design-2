package lld.models;

import lombok.Getter;
import lombok.Setter;
import lld.enums.EventStatus;
import lld.utils.CustomComparatorEvent;
import lld.utils.CustomComparatorInterval;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
@Setter
//@Builder
public class Calender {
    private int id;

//    private Map<Long, Set<Integer>> startTimeToEventIdSetMap = new TreeMap<>();
//    private List<Interval> bookedslots = new ArrayList<>(); // user accepts

    private Set<Event> allEvents = new TreeSet<>(new CustomComparatorEvent());

    public Set<Interval> getEventTimeInterval(Interval givenSlot) {
        return allEvents.stream()
                .filter(event -> (
                        (event.getTimeSlot().getStartTime() > givenSlot.getStartTime() &&
                                event.getTimeSlot().getEndTime() < givenSlot.getEndTime())))
                .map(Event::getTimeSlot)
                .collect(
                        Collectors.toCollection(() ->
                                new TreeSet<>(new CustomComparatorInterval())));
    }

    public Set<Event> getEventsInInterval(Interval givenSlot) {
        return allEvents.stream()
                .filter(event -> (
                        (event.getTimeSlot().getStartTime() >= givenSlot.getStartTime() &&
                                event.getTimeSlot().getEndTime() <= givenSlot.getEndTime())))
                .collect(
                        Collectors.toCollection(() ->
                                new TreeSet<>(new CustomComparatorEvent())));
    }

    public Set<Event> getEventsInIntervalWithStatus(
            Interval givenSlot, EventStatus status, int userId) {
        return allEvents.stream()
                .filter(event -> (
                        (event.getTimeSlot().getStartTime() >= givenSlot.getStartTime() &&
                                event.getTimeSlot().getEndTime() <= givenSlot.getEndTime())))
                .filter(event -> event.getUserIdToEventStatus().containsKey(userId))
                .filter(event -> event.getUserIdToEventStatus().get(userId).equals(status))
                .collect(
                        Collectors.toCollection(() ->
                                new TreeSet<>(new CustomComparatorEvent())));
    }
}
