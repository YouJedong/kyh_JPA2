package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {

        Member member = new Member();
        member.setName("you");

        Long saveId = memberService.join(member);

        Assert.assertEquals(member, memberRepository.findOne(saveId));



    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원_예외() throws Exception {
        Member member = new Member();
        member.setName("you");

        Member member1 = new Member();
        member1.setName("you");

        memberService.join(member);
        memberService.join(member1);

        Assert.fail("실패한 테스트입니다.");
    }


}