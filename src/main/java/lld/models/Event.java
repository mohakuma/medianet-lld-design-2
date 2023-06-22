package lld.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lld.enums.EventStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class Event {
    private int id;
    private String title;
    private String lcoation;

    private User owner;
    private List<User> participants;
    private Interval timeSlot;

    @Builder.Default
    private Map<Integer, EventStatus> userIdToEventStatus = new HashMap<>();
}
