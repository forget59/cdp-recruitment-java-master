package adeo.leroymerlin.cdp.member;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberMapperImplTest {

    private final MemberMapper memberMapper = new MemberMapperImpl();

    @Test
    void toDto() {
        Member member = new Member();
        member.setName("John Doe");

        MemberDto memberDto = memberMapper.toDto(member);

        assertNotNull(memberDto);
        assertEquals("John Doe", memberDto.name());
    }

    @Test
    void toEntity() {
        MemberDto memberDto = new MemberDto("Jane Doe");

        Member member = memberMapper.toEntity(memberDto);

        assertNotNull(member);
        assertEquals("Jane Doe", member.getName());
    }
}