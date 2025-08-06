package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.BandWithCountViewDto;

import java.util.Set;

public record EventWithCountViewDto(String title, String imgUrl, Set<BandWithCountViewDto> bands, Integer nbStars, String comment) {

    public EventWithCountViewDto(String title, String imgUrl, Set<BandWithCountViewDto> bands, Integer nbStars, String comment) {
        this.title = String.format("%s [%d]", title, bands.size()) ;
        this.imgUrl = imgUrl;
        this.bands = bands;
        this.nbStars = nbStars;
        this.comment = comment;
    }
}
