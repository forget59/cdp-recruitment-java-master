package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.BandWithCountMapper;
import adeo.leroymerlin.cdp.band.BandWithCountMapperImpl;
import adeo.leroymerlin.cdp.member.MemberMapper;
import adeo.leroymerlin.cdp.member.MemberMapperImpl;
import org.junit.jupiter.api.Test;
import util.EventTestFactory;

import static org.junit.jupiter.api.Assertions.*;

class EventWithCountMapperImplTest {

    private final MemberMapper memberMapper = new MemberMapperImpl();
    private final BandWithCountMapper bandWithCountMapper = new BandWithCountMapperImpl(memberMapper);
    private final EventWithCountMapper eventWithCountMapper = new EventWithCountMapperImpl(bandWithCountMapper);

    @Test
    void toEventWithCountDto() {
        Event event = EventTestFactory.createEventWithName("Test Event");
        EventWithCountViewDto eventWithCountDto = eventWithCountMapper.toEventWithCountDto(event);

        assertNotNull(eventWithCountDto);
        assertEquals("Test Event [0]", eventWithCountDto.title());
    }
}