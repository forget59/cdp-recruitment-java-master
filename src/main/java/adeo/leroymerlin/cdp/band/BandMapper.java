package adeo.leroymerlin.cdp.band;

public interface BandMapper {
    BandDto toDto(Band band);
    Band toEntity(BandDto bandDto);
}
