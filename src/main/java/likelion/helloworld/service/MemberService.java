package likelion.helloworld.service;


import likelion.helloworld.domain.Member;
import likelion.helloworld.exception.IdNotFoundException;
import likelion.helloworld.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtility jwtUtility;

    public Member tokenToMember(String token){
        return memberRepository.findByUserId(jwtUtility.validateToken(token).getSubject());
    }

    @Transactional
    public Member changeName(String token, String nickname){
        Member member = tokenToMember(token);
        if(member==null) return null;
        member.setNickname(nickname);
        return member;
    }
    @Transactional
    public Member signUp(String userId, String password, String nickname){
        Member member=memberRepository.findByUserId(userId);
        if(member!=null) return null;
        return memberRepository.save(new Member(userId, password, nickname));
    }
    public Member findById(Long id){return memberRepository.findById(id);}

    public String login(String userId, String passwd){
        Member member=findByUserId(userId);
        if(member!=null && member.checkPassword(passwd)){
            return jwtUtility.generateToken(member.getUserId());
        }
        return null;
    }

    public Member findByUserId(String userId){
        Member member =memberRepository.findByUserId((userId));
        if(member == null) throw new IdNotFoundException();
        return member;
    }

    public List<Member> findByName(String name){ return memberRepository.findByName(name);}

    public List<Member> findAll(){return memberRepository.findAll();}

    public boolean deleteMember(String token){
        Member member=tokenToMember(token);
        if(member==null) return false;
        memberRepository.deleteMember(member);
        return true;
    }
}
