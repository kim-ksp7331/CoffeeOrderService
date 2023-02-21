package com.codestates.member;

import com.codestates.member.mapper.MemberMapper;
import com.codestates.validator.PatchValidation;
import com.codestates.validator.PostValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v10/members")
@Validated
public class MemberController {
    private static final String MEMBER_DEFAULT_URL = "/v10/members";
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Validated(PostValidation.class) @RequestBody MemberDTO.Post memberPostDTO) {
        Member member = memberService.createMember(mapper.memberPostDTOToMember(memberPostDTO));
        URI location = UriComponentsBuilder.newInstance()
                .path(MEMBER_DEFAULT_URL + "/{member-id}")
                .buildAndExpand(member.getMemberId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Validated(PatchValidation.class) @RequestBody MemberDTO.Patch memberPatchDTO) {
        memberPatchDTO.setMemberId(memberId);
        Member response = memberService.updateMember(mapper.memberPatchDTOToMember(memberPatchDTO));
        return new ResponseEntity<>(mapper.memberToMemberResponseDTO(response), HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member response = memberService.findMember(memberId);
        return new ResponseEntity<>(mapper.memberToMemberResponseDTO(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers() {
        List<Member> members = memberService.findMembers();
        List<MemberDTO.Response> response = members.stream()
                .map(mapper::memberToMemberResponseDTO).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @ExceptionHandler
//    public ResponseEntity<List<ErrorResponse.FieldError>> handlerException(MethodArgumentNotValidException e) {
//        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        List<ErrorResponse.FieldError> errors = errorMapper.fieldErrorListToErrorResponseFieldErrorList(fieldErrors);
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
}
