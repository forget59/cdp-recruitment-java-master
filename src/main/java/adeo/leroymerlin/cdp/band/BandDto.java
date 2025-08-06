package adeo.leroymerlin.cdp.band;

import adeo.leroymerlin.cdp.member.MemberDto;

import java.util.Set;

public record BandDto(String name, Set<MemberDto> members) {}