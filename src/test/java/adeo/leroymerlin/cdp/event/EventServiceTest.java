package adeo.leroymerlin.cdp.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.EventTestFactory;

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
        Long eventId = (Long) 1L;

        doNothing().when(eventRepository).deleteById(eventId);

        eventService.delete(eventId);

        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void update_ShouldUpdateEvent() {
        Long id = (Long) 1L;
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

    }
}