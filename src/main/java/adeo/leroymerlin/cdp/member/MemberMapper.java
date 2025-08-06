package adeo.leroymerlin.cdp.member;

public interface MemberMapper {
    MemberDto toDto(Member member);
    Member toEntity(MemberDto memberDto);
}
