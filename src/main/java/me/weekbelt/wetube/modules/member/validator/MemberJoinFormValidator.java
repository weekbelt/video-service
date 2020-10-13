package me.weekbelt.wetube.modules.member.validator;

import me.weekbelt.wetube.modules.member.form.MemberJoinForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberJoinFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberJoinForm memberJoinForm = (MemberJoinForm) target;
        if (!memberJoinForm.getPassword().equals(memberJoinForm.getPassword2())) {
            errors.rejectValue("password", "wrong.value", "입력한 패스워드가 일치하지 않습니다.");
        }

//        TODO: 사용중인 이름과 이메일인지 검증
    }
}
