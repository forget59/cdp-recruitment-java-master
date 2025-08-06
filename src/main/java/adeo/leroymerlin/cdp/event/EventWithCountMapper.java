package adeo.leroymerlin.cdp.event;

import org.springframework.stereotype.Component;

@Component
public interface EventWithCountMapper {
    EventWithCountViewDto toEventWithCountDto(Event event);
}
