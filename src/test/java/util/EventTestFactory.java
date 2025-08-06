package util;

import adeo.leroymerlin.cdp.band.Band;
import adeo.leroymerlin.cdp.band.BandWithCountViewDto;
import adeo.leroymerlin.cdp.event.Event;
import adeo.leroymerlin.cdp.event.EventWithCountViewDto;
import adeo.leroymerlin.cdp.member.Member;
import adeo.leroymerlin.cdp.member.MemberDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventTestFactory {
    private static int id;

    public static Event createEventWithName(String name) {
        Event event = new Event();
        event.setTitle(name);
        event.setId(Long.valueOf((long) id++));
        return event;
    }

    public static Band createBandWithMembers(String s, List<String> names) {
        Band band = new Band();
        band.setName(s);
        Set<Member> members = names.stream()
                .map(name -> {
                    var member = new adeo.leroymerlin.cdp.member.Member();
                    member.setName(name);
                    return member;
                })
                .collect(Collectors.toSet());
        return band;
    }

    public static Event createFullEvent() {
        Event event = createEventWithName("Hellfest");
        event.setImgUrl("https://example.com/image.jpg");
        event.setNbStars(Integer.valueOf(5));
        event.setComment("Best festival ever!");
        event.setBands(Set.of(
                createBandWithMembers("Band A", List.of("Member 1", "Member 2")),
                createBandWithMembers("Band B", List.of("Member 3", "Member 4"))
        ));
        return event;
    }

    public static EventWithCountViewDto createFullEventWithCount() {
        BandWithCountViewDto bandA = new BandWithCountViewDto("Band A", Set.of(new MemberDto("Member 1"), new MemberDto("Member 2")));
        BandWithCountViewDto bandB = new BandWithCountViewDto("Band B", Set.of(new MemberDto("Member 3"), new MemberDto("Member 4")));
        Set<BandWithCountViewDto> bands = Set.of(bandA, bandB);
        EventWithCountViewDto eventWithCount = new EventWithCountViewDto("Hellfest","https://example.com/image.jpg", bands, 5, "Best festival ever!");
        return eventWithCount;
    }
}
