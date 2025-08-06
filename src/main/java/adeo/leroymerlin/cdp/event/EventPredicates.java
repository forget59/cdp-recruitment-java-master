package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.member.Member;

import java.util.Objects;
import java.util.function.Predicate;

public class EventPredicates {
    public static Predicate<Event> eventBandsContainMemberWith(String search) {
        if (search == null || search.isEmpty()) {
            return event -> true;
        }
        String searchLowered = search.toLowerCase();
        return event -> event.getBands().stream()
                .flatMap(band -> band.getMembers().stream())
                .map(Member::getName)
                .filter(Objects::nonNull)
                .anyMatch(name -> name.toLowerCase().contains(searchLowered));
    }
}
