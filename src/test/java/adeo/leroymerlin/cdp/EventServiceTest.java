package adeo.leroymerlin.cdp;

import adeo.leroymerlin.cdp.event.Event;
import adeo.leroymerlin.cdp.event.EventRepository;
import adeo.leroymerlin.cdp.event.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.EventTestFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    public EventServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEvents_ShouldReturnAllEvents() {
        Event event1 = EventTestFactory.createEventWithName("Hellfest");
        Event event2 = EventTestFactory.createEventWithName("Sylak Open Air");
        Event event3 = EventTestFactory.createEventWithName("Raismes Fest");
        when(eventRepository.findAll()).thenReturn(List.of(event1, event2, event3));

        var res = eventService.getEvents();

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

        Long eventId = 1L;
        Event existingEvent = EventTestFactory.createEventWithName("Old Event");
        Event updatedEvent = EventTestFactory.createEventWithName("Updated Event");
        updatedEvent.setComment("Updated Comment");
        updatedEvent.setNbStars(5);

        when(eventRepository.findById(eventId)).thenReturn(java.util.Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        eventService.update(eventId, updatedEvent);

        verify(eventRepository, times(1)).findById(1L);
        assertEquals("Updated Comment", existingEvent.getComment());
        assertEquals(5, existingEvent.getNbStars());
        verify(eventRepository, times(1)).save(existingEvent);
    }
}