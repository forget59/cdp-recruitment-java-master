package adeo.leroymerlin.cdp.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final EventWithCountMapper eventWithCountMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper, EventWithCountMapper eventWithCountMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.eventWithCountMapper = eventWithCountMapper;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<EventWithCountViewDto> getFilteredEvents(String query) {
        return eventRepository.findAll().stream()
                .filter(EventPredicates.eventBandsContainMemberWith(query))
                .map(eventWithCountMapper::toEventWithCountDto)
                .toList();
    }

    public void update(Long id, EventDto event) {
        Event eventEntity = eventMapper.toEntity(event);
        eventRepository.findById(id)
                .ifPresent(existingEvent -> {
                    existingEvent.setComment(eventEntity.getComment());
                    existingEvent.setNbStars(eventEntity.getNbStars());
                    eventRepository.save(existingEvent);
                });
    }
}
