package adeo.leroymerlin.cdp.band;

import adeo.leroymerlin.cdp.member.Member;
import adeo.leroymerlin.cdp.member.MemberMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BandWithCountMapperImplTest {

    private final BandWithCountMapper bandWithCountMapper = new BandWithCountMapperImpl(
            new MemberMapperImpl()
    );


    @Test
    void toDto_ShouldReturnDtoWithOneMembers() {
        Band band = new Band();
        band.setName("Test Band");
        band.setMembers(Set.of(new Member()));
        BandWithCountViewDto res = bandWithCountMapper.toDto(band);

        assertNotNull(res);
        assertEquals("Test Band [1]", res.name());
    }

    @Test
    void toDto_ShouldReturnDtoWithNoMembers() {
        Band band = new Band();
        band.setName("Test Band");
        band.setMembers(Set.of());
        BandWithCountViewDto res = bandWithCountMapper.toDto(band);

        assertNotNull(res);
        assertEquals("Test Band [0]", res.name());
    }
}