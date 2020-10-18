package me.weekbelt.wetube.modules.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.Role;
import me.weekbelt.wetube.modules.member.UserMember;
import me.weekbelt.wetube.modules.member.form.MemberJoinForm;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Member processNewMember(MemberJoinForm memberJoinForm) {
        return saveNewMember(memberJoinForm);
    }

    private Member saveNewMember(MemberJoinForm memberJoinForm) {
        Member member = Member.builder()
                .email(memberJoinForm.getEmail())
                .name(memberJoinForm.getName())
                .password(passwordEncoder.encode(memberJoinForm.getPassword()))
                .build();
        return memberRepository.save(member);
    }

    public void login(Member member) {
        Set<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(member);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserMember(member),
                member.getPassword(),
                grantedAuthorities);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }

    private Set<GrantedAuthority> getGrantedAuthorities(Member member) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (member.getName().equals("weekbelt")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return grantedAuthorities;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(emailOrNickname).orElse(null);
        if (member == null) {
            member = memberRepository.findByName(emailOrNickname).orElseThrow(
                    () -> new UsernameNotFoundException("찾는 아이디나 이메일이 없습니다."));
        }
        return new UserMember(member);
    }
}
