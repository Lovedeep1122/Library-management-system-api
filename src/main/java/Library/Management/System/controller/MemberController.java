package Library.Management.System.controller;

import Library.Management.System.entity.Member;
import Library.Management.System.repository.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // CREATE a new member
    @PostMapping
    // @Valid tells Spring Boot: "Check the annotations in the Member class before running this code!"
    public ResponseEntity<?> createMember(@Valid @RequestBody Member member) {

        if (memberRepository.existsByEmail(member.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: A member with this email already exists.");
        }

        Member savedMember = memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    // READ all members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
