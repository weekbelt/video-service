package me.weekbelt.wetube.modules.member.validator;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.member.form.ChangeNameForm;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ChangeNameFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ChangeNameForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangeNameForm changeNameForm = (ChangeNameForm) target;

        if (memberRepository.existsByName(changeNameForm.getName())) {
            errors.rejectValue("name", "invalid.name", "이미 사용중인 이름입니다.");
        }
    }
}
