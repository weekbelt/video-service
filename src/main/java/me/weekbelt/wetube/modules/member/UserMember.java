package me.weekbelt.wetube.modules.member;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

@Getter
public class UserMember extends User {

    private final Member member;

    public UserMember(Member member, Set<GrantedAuthority> grantedAuthorities) {
        super(member.getName(), member.getPassword(), grantedAuthorities);
        this.member = member;
    }
}
