package lld.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private int id;
    private String name;
    private String email;

    @Builder.Default
    private Calender calender = new Calender();
}
