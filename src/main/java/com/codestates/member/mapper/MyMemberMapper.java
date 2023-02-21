package com.codestates.member.mapper;

import com.codestates.member.Member;
import com.codestates.member.MemberDTO;

public class MyMemberMapper implements MemberMapper {
    @Override
    public Member memberPostDTOToMember(MemberDTO.Post memberPostDTO) {
        Member member = new Member();
        member.setPhone(memberPostDTO.getPhone());
        member.setName(memberPostDTO.getName());
        member.setEmail(memberPostDTO.getEmail());
        return member;
    }

    @Override
    public Member memberPatchDTOToMember(MemberDTO.Patch memberPatchDTO) {
        Member member = new Member();
        member.setMemberId(memberPatchDTO.getMemberId());
        member.setName(memberPatchDTO.getName());
        member.setPhone(memberPatchDTO.getPhone());
        return member;
    }

    @Override
    public MemberDTO.Response memberToMemberResponseDTO(Member member) {
        return new MemberDTO.Response(member.getMemberId(), member.getEmail(), member.getName(), member.getPhone());
    }
}
