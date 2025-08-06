package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.BandWithCountMapper;
import adeo.leroymerlin.cdp.band.BandWithCountViewDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventWithCountMapperImpl implements EventWithCountMapper {

    private final BandWithCountMapper bandWithCountMapper;

    EventWithCountMapperImpl(BandWithCountMapper bandWithCountMapper) {
        this.bandWithCountMapper = bandWithCountMapper;
    }

    @Override
    public EventWithCountViewDto toEventWithCountDto(Event event) {
        if (event == null) return null;
        Set<BandWithCountViewDto> bands;
        if (event.getBands() == null || event.getBands().isEmpty()) {
            bands = Set.of();
        } else {
            bands = event.getBands().stream()
                    .map(bandWithCountMapper::toDto)
                    .collect(Collectors.toSet());
        }
        return new EventWithCountViewDto(
                event.getTitle(),
                event.getImgUrl(),
                bands,
                event.getNbStars(),
                event.getComment()
        );
    }
}
