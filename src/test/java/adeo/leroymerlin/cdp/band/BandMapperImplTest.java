package adeo.leroymerlin.cdp.band;

import adeo.leroymerlin.cdp.event.EventMapperImpl;
import adeo.leroymerlin.cdp.member.Member;
import adeo.leroymerlin.cdp.member.MemberMapper;
import adeo.leroymerlin.cdp.member.MemberMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {BandMapperImpl.class, MemberMapperImpl.class})
class BandMapperImplTest {

    @Autowired
    private BandMapper bandMapper;

    @Test
    void toDto() {
        Band band = new Band();
        band.setName("The Rockers");
        Member member = new Member();
        member.setName("Alice");
        Member member2 = new Member();
        member2.setName("Bob");
        band.setMembers(Set.of(member, member2));

        BandDto bandDto = bandMapper.toDto(band);

        assertNotNull(bandDto);
        assertEquals("The Rockers", bandDto.name());
        assertEquals(2, bandDto.members().size());
    }

    @Test
    void toEntity() {
    }
}