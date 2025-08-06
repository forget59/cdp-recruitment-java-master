package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.Band;
import adeo.leroymerlin.cdp.band.BandDto;
import adeo.leroymerlin.cdp.band.BandMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventMapperImpl implements EventMapper {

    private final BandMapper bandMapper;

    EventMapperImpl(BandMapper bandMapper) {
        this.bandMapper = bandMapper;
    }

    @Override
    public EventDto toDto(Event event) {
        if (event == null) return null;
        Set<BandDto> bands;
        if (event.getBands() != null) {
            bands = event.getBands().stream()
                    .map(bandMapper::toDto)
                    .collect(Collectors.toSet());
        } else {
            bands = Set.of();
        }
        return new EventDto(
                event.getTitle(),
                event.getImgUrl(),
                bands,
                event.getNbStars(),
                event.getComment()
        );
    }

    @Override
    public Event toEntity(EventDto eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.title());
        event.setImgUrl(eventDto.imgUrl());
        event.setNbStars(eventDto.nbStars());
        event.setComment(eventDto.comment());
        if (eventDto.bands() != null) {
            Set<Band> bands = eventDto.bands().stream()
                    .map(bandMapper::toEntity)
                    .collect(Collectors.toSet());
            event.setBands(bands);
        } else {
            event.setBands(Set.of());
        }
        return event;
    }
}
