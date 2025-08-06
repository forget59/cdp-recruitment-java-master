package adeo.leroymerlin.cdp.band;

import adeo.leroymerlin.cdp.member.MemberDto;
import adeo.leroymerlin.cdp.member.MemberMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BandWithCountMapperImpl implements BandWithCountMapper {

    private final MemberMapper memberMapper;

    public BandWithCountMapperImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public BandWithCountViewDto toDto(Band band) {
        if (band.getMembers().isEmpty()) {
            return new BandWithCountViewDto(band.getName(), Set.of());
        } else {
            Set<MemberDto> members = band.getMembers().stream()
                    .map(memberMapper::toDto)
                    .collect(Collectors.toSet());
            return new BandWithCountViewDto(band.getName(), members);
        }
    }
}
