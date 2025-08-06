package adeo.leroymerlin.cdp.band;

import adeo.leroymerlin.cdp.member.Member;
import adeo.leroymerlin.cdp.member.MemberDto;
import adeo.leroymerlin.cdp.member.MemberMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BandMapperImpl implements BandMapper {

    private final MemberMapper memberMapper;

    public BandMapperImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public BandDto toDto(Band band) {
        Set<MemberDto> members = band.getMembers().stream()
                .map(memberMapper::toDto)
                .collect(Collectors.toSet());
        System.out.println("Band members: " + members.size());
        return new BandDto(band.getName(), members);
    }

    @Override
    public Band toEntity(BandDto bandDto) {
        if (bandDto == null) return null;
        Band res = new Band();
        res.setName(bandDto.name());
        if (bandDto.members() != null) {
            Set<Member> members = bandDto.members().stream()
                    .map(memberMapper::toEntity)
                    .collect(Collectors.toSet());
            res.setMembers(members);
        } else {
            res.setMembers(Set.of());
        }
        return res;
    }
}
