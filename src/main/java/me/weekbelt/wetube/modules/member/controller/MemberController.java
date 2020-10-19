package me.weekbelt.wetube.modules.member.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberDtoFactory;
import me.weekbelt.wetube.modules.member.form.ChangeEmailForm;
import me.weekbelt.wetube.modules.member.form.MemberUpdateForm;
import me.weekbelt.wetube.modules.member.service.MemberService;
import me.weekbelt.wetube.modules.member.validator.ChangeEmailFormValidator;
import me.weekbelt.wetube.modules.member.validator.MemberUpdateFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberUpdateFormValidator memberUpdateFormValidator;
    private final ChangeEmailFormValidator changeEmailFormValidator;

    @InitBinder("memberUpdateForm")
    public void memberUpdateFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberUpdateFormValidator);
    }

    @InitBinder("changeEmailForm")
    public void changeEmailFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(changeEmailFormValidator);
    }

    @GetMapping("/profile")
    public String userDetail(@CurrentMember Member member, Model model) {
        model.addAttribute("pageTitle", "Member Detail");
        model.addAttribute("member", member);
        return "users/userDetail";
    }

    @GetMapping("/edit-profile")
    public String editProfileView(@CurrentMember Member member, Model model) {
        MemberUpdateForm memberUpdateForm = MemberDtoFactory.memberToMemberUpdateForm(member);
        model.addAttribute("memberUpdateForm", memberUpdateForm);
        model.addAttribute("pageTitle", "Edit Profile");
        return "users/editProfile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(@CurrentMember Member member, @Valid MemberUpdateForm memberUpdateForm,
                              Errors errors, Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute("pageTitle", "Member Detail");
            return "users/editProfile";
        }
        memberService.updateProfile(member, memberUpdateForm);
        attributes.addFlashAttribute("message", "정보를 수정하였습니다.");
        return "redirect:/members/profile";
    }

    @GetMapping("/change-email")
    public String changeEmailView(@CurrentMember Member member, Model model) {
        ChangeEmailForm changeEmailForm = MemberDtoFactory.memberToChangeEmailForm(member);
        model.addAttribute("changeEmailForm", changeEmailForm);
        model.addAttribute("pageTitle", "Edit Email");
        return "users/changeEmail";
    }

    @PostMapping("/change-email")
    public String changeEmail(@CurrentMember Member member, @Valid ChangeEmailForm changeEmailForm,
                            Errors errors, Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute("pageTitle", "Member Detail");
            return "users/changeEmail";
        }
        memberService.changeEmail(member, changeEmailForm);
        attributes.addFlashAttribute("message", "이메일을 수정하였습니다.");
        return "redirect:/members/profile";
    }

    @GetMapping("/change-password")
    public String changePassword(@CurrentMember Member member, Model model) {
        model.addAttribute("pageTitle", "Change Password");
        return "users/changePassword";
    }
}
