package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given 뭔가 주어졌을 때
        Member member = new Member();
        member.setName("hello");

        //when 이걸 실행 했을 때
        Long saveId = memberService.join(member);

        //then 이런 결과가 나와야 한다는 문법이라고 함
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        //assertThrows로 e에 발생한 에러를 할당받음. assertThrows(에러 class, 에러가 발생해야하는 로직)
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //이후 assertJ의 assertThat을 이용해 에러 메시지를 비교해서 내가 기대하는 에러 메시지와 코드 실행 중 발생한 에러가 같으면 테스트 성공.
        //주어진 e.getMessage()는 MemberService의 ("이미 존재하는 회원입니다.")와 일치하니 참.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

 */
        //then
    }

    @Test
    void findMember () {
    }

    @Test
    void findOne () {
    }
}
