package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.Band;
import adeo.leroymerlin.cdp.band.BandWithCountViewDto;
import adeo.leroymerlin.cdp.member.Member;
import adeo.leroymerlin.cdp.member.MemberDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.EventTestFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventWithCountMapper eventWithCountMapper;

    @InjectMocks
    private EventService eventService;

    @Test
    void getEvents_ShouldReturnAllEvents() {
        Event event1 = EventTestFactory.createEventWithName("Hellfest");
        Event event2 = EventTestFactory.createEventWithName("Sylak Open Air");
        Event event3 = EventTestFactory.createEventWithName("Raismes Fest");

        when(eventRepository.findAll()).thenReturn(List.of(event1, event2, event3));

        List<Event> res = eventService.getEvents();

        assertEquals(3, res.size());

        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void delete_ShouldDeleteEventById() {
        Long eventId = 1L;

        doNothing().when(eventRepository).deleteById(eventId);

        eventService.delete(eventId);

        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void update_ShouldUpdateEvent() {
        Long id = 1L;
        EventDto dto = new EventDto(
                "Updated Event",
                "http://example.com/updated.jpg",
                Set.of(),
                5,
                "Updated comment"
        );
        Event mappedEntity = new Event();
        mappedEntity.setComment("Updated comment");
        mappedEntity.setNbStars(5);

        Event existingEvent = new Event();
        existingEvent.setComment("Old comment");
        existingEvent.setNbStars(1);

        when(eventMapper.toEntity(dto)).thenReturn(mappedEntity);
        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));

        eventService.update(id, dto);

        verify(eventMapper).toEntity(dto);
        verify(eventRepository).findById(id);
        verify(eventRepository).save(existingEvent);

        assert existingEvent.getComment().equals("Updated comment");
        assert existingEvent.getNbStars() == 5;
    }

    @Test
    void getFilteredEvents_ShouldReturnFilteredEvents() {
        Member james = new Member();
        james.setName("James Hetfield");

        Member lars = new Member();
        lars.setName("Lars Ulrich");

        Band metallica = new Band();
        metallica.setMembers(Set.of(james, lars));

        Event hellfest = EventTestFactory.createEventWithName("Hellfest");
        hellfest.setBands(Set.of(metallica));

        Event sylak = EventTestFactory.createEventWithName("Sylak Open Air");
        Band slayer = new Band();
        slayer.setMembers(Set.of());
        sylak.setBands(Set.of(slayer));

        when(eventRepository.findAll()).thenReturn(List.of(hellfest, sylak));

        BandWithCountViewDto metallicaDto = new BandWithCountViewDto("Metallica", Set.of(new MemberDto("James Hetfield"), new MemberDto("Lars Ulrich")));
        BandWithCountViewDto slayerDto = new BandWithCountViewDto("Slayer", Set.of());
        EventWithCountViewDto hellfestDto = new EventWithCountViewDto("Hellfest", "https://example.com/hellfest.jpg", Set.of(metallicaDto, slayerDto), 5, "Best festival ever!");
        when(eventWithCountMapper.toEventWithCountDto(hellfest)).thenReturn(hellfestDto);

        List<EventWithCountViewDto> result = eventService.getFilteredEvents("James");

        assertEquals(1, result.size());
        assertEquals("Hellfest [2]", result.getFirst().title());

        verify(eventRepository, times(1)).findAll();
        verify(eventWithCountMapper, times(1)).toEventWithCountDto(hellfest);
        verify(eventWithCountMapper, never()).toEventWithCountDto(sylak);
    }

    @Test
    void getFilteredEvents_ShouldReturnEmptyListWhenNoMatch() {
        Member james = new Member();
        james.setName("James Hetfield");

        Member lars = new Member();
        lars.setName("Lars Ulrich");

        Band metallica = new Band();
        metallica.setMembers(Set.of(james, lars));

        Event hellfest = EventTestFactory.createEventWithName("Hellfest");
        hellfest.setBands(Set.of(metallica));

        Event sylak = EventTestFactory.createEventWithName("Sylak Open Air");
        Band slayer = new Band();
        slayer.setMembers(Set.of());
        sylak.setBands(Set.of(slayer));

        when(eventRepository.findAll()).thenReturn(List.of(hellfest, sylak));

        BandWithCountViewDto metallicaDto = new BandWithCountViewDto("Metallica", Set.of(new MemberDto("James Hetfield"), new MemberDto("Lars Ulrich")));
        BandWithCountViewDto slayerDto = new BandWithCountViewDto("Slayer", Set.of());

        List<EventWithCountViewDto> result = eventService.getFilteredEvents("Nonexistent Member");

        assertEquals(Collections.emptyList(), result);
        verify(eventRepository, times(1)).findAll();
        verify(eventWithCountMapper, never()).toEventWithCountDto(hellfest);
        verify(eventWithCountMapper, never()).toEventWithCountDto(sylak);
    }
}