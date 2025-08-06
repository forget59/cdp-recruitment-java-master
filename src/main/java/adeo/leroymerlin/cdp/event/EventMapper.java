package adeo.leroymerlin.cdp.event;

public interface EventMapper {
    EventDto toDto(Event event);
    Event toEntity(EventDto eventDto);
}
