package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.BandMapperImpl;
import adeo.leroymerlin.cdp.member.MemberMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import util.EventTestFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {EventMapperImpl.class, BandMapperImpl.class, MemberMapperImpl.class})
class EventMapperImplTest {

    @Autowired
    private EventMapper eventMapper;

    @Test
    void toDto() {
        Event event = EventTestFactory.createEventWithName("Test Event");

        EventDto eventDto = eventMapper.toDto(event);

        assertNotNull(eventDto);
        assertEquals("Test Event", eventDto.title());
    }

    @Test
    void toEntity() {
        EventDto eventDto = new EventDto("Test Event", "http://example.com/image.jpg", null, 5, "Great event!");

        Event event = eventMapper.toEntity(eventDto);

        assertNotNull(event);
        assertEquals("Test Event", event.getTitle());
        assertEquals("http://example.com/image.jpg", event.getImgUrl());
        assertEquals(5, event.getNbStars());
        assertEquals("Great event!", event.getComment());
        assertTrue(event.getBands().isEmpty());
    }
}