package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.Band;
import adeo.leroymerlin.cdp.member.Member;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EventPredicatesTest {

    @Test
    void eventBandsContainMemberWith_shouldReturnTrue_WhenMemberContainSearch() {
        Event event = new Event();
        Band band = new Band();
        Member member = new Member();

        member.setName("Alice");
        band.setMembers(Set.of(member));
        event.setBands(Set.of(band));

        assertTrue(EventPredicates.eventBandsContainMemberWith("Al").test(event));
    }

    @Test
    void eventBandsContainMemberWith_shouldReturnFalse_WhenNoMemberContainSearch () {
        Event event = new Event();
        Band band = new Band();
        Member member = new Member();

        member.setName("Alice");
        band.setMembers(Set.of(member));
        event.setBands(Set.of(band));

        assertFalse(EventPredicates.eventBandsContainMemberWith("b").test(event));
    }
}