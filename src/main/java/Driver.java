import lld.enums.EventStatus;
import lld.models.Event;
import lld.models.Interval;
import lld.models.User;
import lld.services.EventManager;
import lld.services.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        System.out.println("Welcome to meeting scheduler");


        User user1 = User.builder()
                .id(1)
                .name("akash")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("anand")
                .build();
        User user3 = User.builder()
                .id(3)
                .name("biswas")
                .build();
        User user4 = User.builder()
                .id(4)
                .name("rashika")
                .build();

        List<User> participants1 = new ArrayList<>(Arrays.asList(user2, user3));
        List<User> participants2 = new ArrayList<>(Arrays.asList(user2, user3, user4));

        EventManager eventManager = new EventManager();
        UserManager userManager = new UserManager(eventManager);

        Interval interval1 = Interval.builder()
                .startTime(System.currentTimeMillis() + 1000*60*120)    // start time after 2 hours
                .endTime(System.currentTimeMillis() + 1000*60*180)      // end time after 3 hours
                .build();

        Interval interval2 = Interval.builder()
                .startTime(System.currentTimeMillis() + 1000*60*20*60)    // start time after 3 hours
                .endTime(System.currentTimeMillis() + 1000*60*24*60)      // end time after 4 hours
                .build();

        Interval interval3 = Interval.builder()
                .startTime(System.currentTimeMillis() + 1000*60*10*60)    // start time after 4 hours
                .endTime(System.currentTimeMillis() + 1000*60*((12*60)+ 30))    // end time after 5 hours
                .build();

        userManager.addUser(user1);
        userManager.addUser(user2);
        userManager.addUser(user3);
        userManager.addUser(user4);

        Event event1= eventManager.createEvent("event1", "slack", participants1, user1, interval1);
        Event event2= eventManager.createEvent("event2", "slack", participants2, user1, interval2);
        Event event3= eventManager.createEvent("event3", "slack", new ArrayList<>(), user4, interval3);

        Interval oneDayInterval = Interval.builder()
                .startTime(System.currentTimeMillis())
                .endTime(System.currentTimeMillis() + 1000*60*24*60)
                .build();

        userManager.printUserCalender(user1.getId(), oneDayInterval);
        userManager.printUserCalender(user2.getId(), oneDayInterval);
        userManager.printUserCalender(user3.getId(), oneDayInterval);
        userManager.printUserCalender(user4.getId(), oneDayInterval);

        userManager.acceptEvent(2, 1);
        userManager.acceptEvent(3, 1);

//        userManager.printBookedSlots(2);
//        userManager.rejectEvent(2, 1);
//        userManager.printBookedSlots(2);

        userManager.printEventWithStatus(1, oneDayInterval, EventStatus.ACCEPTED);
        userManager.printEventWithStatus(2, oneDayInterval, EventStatus.ACCEPTED);
        userManager.printEventWithStatus(3, oneDayInterval, EventStatus.ACCEPTED);
        userManager.printEventWithStatus(4, oneDayInterval, EventStatus.ACCEPTED);

        userManager.rejectEvent(user2.getId(), 1);

        userManager.printEventWithStatus(1, oneDayInterval, EventStatus.REJECTED);

        userManager.printEventWithStatus(2, oneDayInterval, EventStatus.REJECTED);
        userManager.printEventWithStatus(2, oneDayInterval, EventStatus.ACCEPTED);

        userManager.printEventWithStatus(3, oneDayInterval, EventStatus.REJECTED);
        userManager.printEventWithStatus(4, oneDayInterval, EventStatus.REJECTED);

        userManager.printAllFreeSlots(participants1, oneDayInterval);
        userManager.printAllFreeSlots(participants2, oneDayInterval);
    }
}
