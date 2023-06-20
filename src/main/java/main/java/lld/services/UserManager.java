package main.java.lld.services;

import main.java.lld.enums.EventStatus;
import main.java.lld.models.Calender;
import main.java.lld.models.Event;
import main.java.lld.models.Interval;
import main.java.lld.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserManager {
    private List<User> users;
    private final EventManager eventManager;

    public UserManager(EventManager eventManager) {
        this.users = new ArrayList<>();
        this.eventManager = eventManager;
    }

    public void printUserCalender(int userId) {
        User user = getUserById(userId);
        Calender userCalender = user.getCalender();

        System.out.println();
        System.out.println("printing calender for user: " + user.getName());

        Map<Long, Set<Integer>> allEventsMap = userCalender.getStartTimeToEventIdSetMap();

        if(allEventsMap.isEmpty()) {
            System.out.println("no events is scheduled for the user currently");
        }

        int count = 1;
        for(Map.Entry<Long,Set<Integer>> entry: allEventsMap.entrySet()) {
            Long startTime = entry.getKey();
            Set<Integer> eventIds = entry.getValue();

            List<Event> eventList = eventManager.getEventsByIds(eventIds);
            for(Event event: eventList) {
                System.out.println("Event " + count +
                        ": sceuduled from " + new Date(startTime) + "-" + new Date(event.getTimeSlot().getEndTime()));
                count++;
            }
        }
    }

    public void printBookedSlots(Integer userId) {
        User user = getUserById(userId);
        Calender userCalender = user.getCalender();

        System.out.println();
        System.out.println("For user: " + user.getName() + " printing booked slots");
        int count = 1;
        List<Interval> bookedSlots = userCalender.getBookedslots();

        if(bookedSlots.isEmpty()) {
            System.out.println("No accepted events for the user");
        }

        for(Interval i: bookedSlots) {
            System.out.println("Slot " + count +
                    ": booked from " + new Date(i.getStartTime()) + " to " + new Date(i.getEndTime()));
            count++;
        }
    }

    public void acceptEvent(Integer userId, Integer eventId) {
        Event event = eventManager.getEvent(eventId);
        event.getUserIdToEventStatus().put(userId, EventStatus.ACCEPTED);

        User user = getUserById(userId);
        user.getCalender().getBookedslots().add(event.getTimeSlot());
    }

    public void rejectEvent(Integer userId, Integer eventId) {
        Event event = eventManager.getEvent(eventId);
        event.getUserIdToEventStatus().put(userId, EventStatus.REJECTED);

        User user = getUserById(userId);
        user.getCalender().getBookedslots().add(event.getTimeSlot());
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

    public List<Interval> getFreeSlots(List<User> users, Interval givenSlot) {
        List<Interval> freeSlots = new ArrayList<>();

        // iterate over all the user
        // get their calender-> booked slots
        // List<Interval> freeslots;
        // minus slots for user2
        // minus slots for user3

        return freeSlots;
    }
}
