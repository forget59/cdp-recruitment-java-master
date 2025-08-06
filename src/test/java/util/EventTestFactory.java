package util;

import adeo.leroymerlin.cdp.event.Event;

public class EventTestFactory {
    private static int id;

    public static Event createEventWithName(String name) {
        Event event = new Event();
        event.setTitle(name);
        event.setId((long) id++);
        return event;
    }
}
