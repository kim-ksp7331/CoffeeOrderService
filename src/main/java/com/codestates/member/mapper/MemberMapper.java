package com.codestates.member.mapper;

import com.codestates.member.Member;
import com.codestates.member.MemberDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDTOToMember(MemberDTO.Post memberPostDTO);
    Member memberPatchDTOToMember(MemberDTO.Patch memberPatchDTO);
    MemberDTO.Response memberToMemberResponseDTO(Member member);
}
