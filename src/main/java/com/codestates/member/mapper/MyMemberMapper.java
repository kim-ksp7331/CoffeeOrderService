package com.codestates.member.mapper;

import com.codestates.member.Member;
import com.codestates.member.MemberDTO;

public class MyMemberMapper implements MemberMapper {
    @Override
    public Member memberPostDTOToMember(MemberDTO.Post memberPostDTO) {
        return new Member(0L, memberPostDTO.getEmail(), memberPostDTO.getName(), memberPostDTO.getPhone());
    }

    @Override
    public Member memberPatchDTOToMember(MemberDTO.Patch memberPatchDTO) {
        return new Member(memberPatchDTO.getMemberId(), null, memberPatchDTO.getName(), memberPatchDTO.getPhone());
    }

    @Override
    public MemberDTO.Response memberToMemberResponseDTO(Member member) {
        return new MemberDTO.Response(member.getMemberId(), member.getEmail(), member.getName(), member.getPhone());
    }
}
