package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.BandDto;

import java.util.Set;

public record EventDto (String title, String imgUrl, Set<BandDto> bands, Integer nbStars, String comment){
}
