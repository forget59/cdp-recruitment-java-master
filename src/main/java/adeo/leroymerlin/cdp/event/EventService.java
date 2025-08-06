package adeo.leroymerlin.cdp.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAll();
        // Filter the events list in pure JAVA here

        return events;
    }

    public void update(Long id, Event event) {
        eventRepository.findById(id)
                .ifPresent(existingEvent -> {
                    existingEvent.setComment(event.getComment());
                    existingEvent.setNbStars(event.getNbStars());
                    eventRepository.save(existingEvent);
                });
    }
}
