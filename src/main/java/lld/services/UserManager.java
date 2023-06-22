package lld.services;

import lld.enums.EventStatus;
import lld.models.Calender;
import lld.models.Event;
import lld.models.Interval;
import lld.models.User;
import lld.utils.CustomComparatorInterval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class UserManager {
    private List<User> users;
    private final EventManager eventManager;

    public UserManager(EventManager eventManager) {
        this.users = new ArrayList<>();
        this.eventManager = eventManager;
    }

    public void printUserCalender(int userId, Interval givenSlot) {
        User user = getUserById(userId);
        Calender userCalender = user.getCalender();
        Set<Event> eventsScheduledInInterval = userCalender.getEventsInInterval(givenSlot);


        System.out.println();
        System.out.println("printing calender for user: " + user.getName());

//        Map<Long, Set<Integer>> allEventsMap = userCalender.getStartTimeToEventIdSetMap();

        if(eventsScheduledInInterval.isEmpty()) {
            System.out.println("no events is scheduled for the user currently");
        }


        for(Event event: eventsScheduledInInterval) {
            System.out.println("Event titled: " + event.getTitle() +
                    ": scheduled from " +
                    new Date(event.getTimeSlot().getStartTime()) + "-" +
                    new Date(event.getTimeSlot().getEndTime()));
        }

//        int count = 1;
//        for(Map.Entry<Long,Set<Integer>> entry: allEventsMap.entrySet()) {
//            Long startTime = entry.getKey();
//            Set<Integer> eventIds = entry.getValue();
//
//            List<Event> eventList = eventManager.getEventsByIds(eventIds);
//            for(Event event: eventList) {
//                System.out.println("Event " + count +
//                        ": sceuduled from " + new Date(startTime) + "-" + new Date(event.getTimeSlot().getEndTime()));
//                count++;
//            }
//        }
    }

    public void printEventWithStatus(
            Integer userId, Interval givenSlot, EventStatus eventStatus) {
        User user = getUserById(userId);
        Calender userCalender = user.getCalender();
        Set<Event> allEventsInInterval = userCalender.getEventsInIntervalWithStatus(
                givenSlot, eventStatus, userId);

        System.out.println();
        System.out.println("For user: " + user.getName() + " printing events with status: "
                + eventStatus.toString());
        for(Event event: allEventsInInterval) {
            System.out.println("Event titled: " + event.getTitle() +
                    ": scheduled from " + new Date(event.getTimeSlot().getStartTime())
                    + " to " + new Date(event.getTimeSlot().getEndTime()));
        }

//        int count = 1;
//        List<Interval> bookedSlots = userCalender.getBookedslots();
//
//        if(bookedSlots.isEmpty()) {
//            System.out.println("No accepted events for the user");
//        }
//
//        for(Interval i: bookedSlots) {
//            System.out.println("Slot " + count +
//                    ": booked from " + new Date(i.getStartTime()) + " to " + new Date(i.getEndTime()));
//            count++;
//        }
    }

    public void acceptEvent(Integer userId, Integer eventId) {
        Event event = eventManager.getEvent(eventId);
        event.getUserIdToEventStatus().put(userId, EventStatus.ACCEPTED);

//        User user = getUserById(userId);
//        user.getCalender().getBookedslots().add(event.getTimeSlot());
    }

    public void rejectEvent(Integer userId, Integer eventId) {
        Event event = eventManager.getEvent(eventId);
        event.getUserIdToEventStatus().put(userId, EventStatus.REJECTED);

//        User user = getUserById(userId);
//        user.getCalender().getBookedslots().add(event.getTimeSlot());
    }

    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst().get();
    }

    public User addUser(User user) {
        users.add(user);

        System.out.println();
        System.out.println("user with name: " + user.getName() + " added successfully");
        return user;
    }

    private Set<Interval> getFreeSlots(List<User> users, Interval givenSlot) {
        Set<Interval> allIntervals = new TreeSet<>(new CustomComparatorInterval());
        Set<Interval> result = new TreeSet<>(new CustomComparatorInterval());

        for(User user: users) {
            Set<Interval> userInterval =  user.getCalender().getEventTimeInterval(givenSlot);
            allIntervals.addAll(userInterval);
        }

//        Long start = givenSlot.getStartTime();
//        Long end = givenSlot.getEndTime();
//
//        Interval first = allIntervals.stream().findFirst().orElse(null);

        if(allIntervals.isEmpty()) {
            return result;
        }

        Interval prev = null;
        for(Interval i: allIntervals) {
            if(prev == null) {
                result.add(Interval.builder()
                                .startTime(givenSlot.getStartTime())
                                .endTime(i.getStartTime())
                        .build());
                prev = i;
//                continue;
            }
//            else if(i.getStartTime() > prev.getStartTime() && i.getEndTime() <= prev.getEndTime()) {
//                continue;
//            }
            else if(i.getStartTime() <= prev.getEndTime() && i.getEndTime() > prev.getEndTime()) {
                prev = i;
//                continue;
            }
            else if (i.getStartTime() > prev.getEndTime()){
                result.add(Interval.builder()
                                .startTime(prev.getEndTime())
                                .endTime(i.getStartTime())
                        .build());
                prev= i;
            }
        }


        if(prev != null) {
            long diff = givenSlot.getEndTime() - prev.getEndTime();
            if (prev.getEndTime() < givenSlot.getEndTime() && diff > 1000) {
                result.add(Interval.builder()
                        .startTime(prev.getEndTime())
                        .endTime(givenSlot.getEndTime())
                        .build());
            }
        }

//        List<Interval> freeSlots = new ArrayList<>();

        // iterate over all the user
        // get their calender-> booked slots
        // List<Interval> freeslots;
        // minus slots for user2
        // minus slots for user3

        return result;
    }

    public void printAllFreeSlots(List<User> users, Interval givenSlot) {
        Set<Interval> freeSlots = getFreeSlots(users, givenSlot);

        System.out.println();
        System.out.println("Print free slots for users: " +
                users.stream().map(User::getName).collect(Collectors.joining(", ")));


        for(Interval freeslot: freeSlots) {
            System.out.println("Free slot from: " + new Date(freeslot.getStartTime())
                    + " to: " + new Date(freeslot.getEndTime()));
        }
    }
}
