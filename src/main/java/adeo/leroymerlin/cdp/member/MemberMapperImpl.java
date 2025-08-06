package adeo.leroymerlin.cdp.member;

import org.springframework.stereotype.Component;

@Component
public class MemberMapperImpl implements MemberMapper {
    @Override
    public MemberDto toDto(Member member) {
        if (member == null) return null;
        return new MemberDto(member.getName());
    }

    @Override
    public Member toEntity(MemberDto memberDto) {
        if (memberDto == null) return null;
        Member res = new Member();
        res.setName(memberDto.name());
        return res;
    }
}
