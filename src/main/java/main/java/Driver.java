package main.java;

import main.java.lld.models.Event;
import main.java.lld.models.Interval;
import main.java.lld.models.User;
import main.java.lld.services.EventManager;
import main.java.lld.services.UserManager;

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
                .startTime(System.currentTimeMillis() + 1000*60*180)    // start time after 3 hours
                .endTime(System.currentTimeMillis() + 1000*60*240)      // end time after 4 hours
                .build();

        Interval interval3 = Interval.builder()
                .startTime(System.currentTimeMillis() + 1000*60*240)    // start time after 4 hours
                .endTime(System.currentTimeMillis() + 1000*60*300)      // end time after 5 hours
                .build();

        userManager.addUser(user1);
        userManager.addUser(user2);
        userManager.addUser(user3);
        userManager.addUser(user4);

        Event event1= eventManager.createEvent("event1", "slack", participants1, user1, interval1);
        Event event2= eventManager.createEvent("event2", "slack", participants2, user1, interval2);

        userManager.printUserCalender(user1.getId());
        userManager.printUserCalender(user2.getId());
        userManager.printUserCalender(user3.getId());
        userManager.printUserCalender(user4.getId());

        userManager.acceptEvent(2, 1);
        userManager.acceptEvent(3, 1);

//        userManager.printBookedSlots(2);
//        userManager.rejectEvent(2, 1);
//        userManager.printBookedSlots(2);

        userManager.printBookedSlots(1);
        userManager.printBookedSlots(2);
        userManager.printBookedSlots(3);
        userManager.printBookedSlots(4);
    }
}
