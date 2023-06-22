package lld.services;

import lld.enums.EventStatus;
import lld.models.Event;
import lld.models.Interval;
import lld.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventManager {
    private List<Event> eventList;
//    private final UserManager userManager;

    public EventManager() {
        this.eventList = new ArrayList<>();
//        this.userManager = userManager;
    }

    public Event createEvent(String title, String location, List<User> participants, User owner, Interval timeSlot) {
        Event crratedEvent = Event.builder()
                .id(eventList.size() + 1)
                .title(title)
                .lcoation(location)
                .participants(participants)
                .owner(owner)
                .timeSlot(timeSlot)
                .build();

        eventList.add(crratedEvent);

        for(User participant: participants) {
            Set<Event> participantsEvents = participant.getCalender().getAllEvents();
            participantsEvents.add(crratedEvent);


//            Map<Long, Set<Integer>> participantMap = participant.getCalender().getStartTimeToEventIdSetMap();
//            if(participantMap.containsKey(timeSlot.getStartTime())) {
//                participantMap.get(timeSlot.getStartTime()).add(crratedEvent.getId());
//            } else {
//                Set<Integer> eventSet = new HashSet<>();
//                eventSet.add(crratedEvent.getId());
//
//                participantMap.put(timeSlot.getStartTime(), eventSet);
//            }
        }

        // add event to onwer calender
        Set<Event> ownerEvents = owner.getCalender().getAllEvents();
        ownerEvents.add(crratedEvent);

        // mark event accepted for the owner
        crratedEvent.getUserIdToEventStatus().put(owner.getId(), EventStatus.ACCEPTED);

//        Map<Long, Set<Integer>> ownerMap = owner.getCalender().getStartTimeToEventIdSetMap();
//        if(ownerMap.containsKey(timeSlot.getStartTime())) {
//            ownerMap.get(timeSlot.getStartTime()).add(crratedEvent.getId());
//        } else {
//            Set<Integer> eventSet = new HashSet<>();
//            eventSet.add(crratedEvent.getId());
//
//            ownerMap.put(timeSlot.getStartTime(), eventSet);
//        }
//
//        // owner always accepts the invite
//        owner.getCalender().getBookedslots().add(timeSlot);
        printEvent(crratedEvent);
        return crratedEvent;
    }

    public void printEvent(Event createdEvent) {
        System.out.println();
        System.out.println("Event with title: " + createdEvent.getTitle()
                + " created from " + new Date(createdEvent.getTimeSlot().getStartTime()) + " to "
                + new Date(createdEvent.getTimeSlot().getEndTime()));
    }

//    public void printEventStatus(Integer eventId) {
//        Event event = getEvent(eventId);
//
//        Map<Integer, EventStatus> map = event.getUserIdToEventStatus();
//        for(Map.Entry<Integer, EventStatus> entry: map.entrySet()) {
//            User user =
//        }
//    }

    public Event getEvent(Integer id) {
        if(id == null) {
            System.out.println("event id is null");
            return null;
        }

        return eventList.stream()
                .filter(event -> event.getId() == id)
                .findFirst().get();
    }

    public List<Event> getEventsByIds(Set<Integer> eventIds) {
        return eventList.stream()
                .filter(event-> eventIds.contains(event.getId()))
                .collect(Collectors.toList());
    }
}
