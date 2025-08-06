package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.BandWithCountViewDto;

import java.util.Set;

public record EventWithCountViewDto(String name, String imgUrl, Set<BandWithCountViewDto> bands, Integer nbStars, String comment) {

    public EventWithCountViewDto(String name, String imgUrl, Set<BandWithCountViewDto> bands, Integer nbStars, String comment) {
        this.name = String.format("%s [%d]", name, bands.size()) ;
        this.imgUrl = imgUrl;
        this.bands = bands;
        this.nbStars = nbStars;
        this.comment = comment;
    }
}
