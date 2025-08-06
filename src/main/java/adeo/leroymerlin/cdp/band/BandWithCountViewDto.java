package adeo.leroymerlin.cdp.band;

import adeo.leroymerlin.cdp.member.MemberDto;

import java.util.Set;

public record BandWithCountViewDto(String name, Set<MemberDto> members) {
    public BandWithCountViewDto(String name, Set<MemberDto> members) {
        this.name = String.format("%s [%d]", name, members.size());
        this.members = members;
    }
}
