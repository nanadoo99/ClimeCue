package nki.ClimCue.service;

import nki.ClimCue.model.member.PersonalConditionDto;
import nki.ClimCue.model.member.UserDetailsImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
class PersonalConditionServiceTest {
    @Autowired
    PersonalConditionService service;

    @Test
    void findByUserId() {
    }

    @Test
    void existPersonalConditionByUserId(){
        createPersonalCondition();
        boolean result = service.existPersonalConditionByUserId(4L);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void existPersonalConditionByUserId_X(){
        boolean result = service.existPersonalConditionByUserId(1L);
        Assertions.assertThat(result).isFalse();
    }


    @Test
    void createPersonalCondition() {
        UserDetailsImpl user = new UserDetailsImpl();
        user.setId(4L);

        PersonalConditionDto personalCondition = PersonalConditionDto.builder()
                .age(99).bodyType("뚱뚱하다.").hobby("잔디깍이").exercise("산책").medical("당뇨").build();

        PersonalConditionDto result = service.createOrUpdatePersonalCondition(user, personalCondition);
        Assertions.assertThat(result.getUserId()).isEqualTo(user.getId());
        Assertions.assertThat(result.getBodyType()).isEqualTo("뚱뚱하다.");

    }

    @Test
    void updatePersonalCondition() {
        UserDetailsImpl user = new UserDetailsImpl();
        user.setId(4L);

        PersonalConditionDto personalCondition1 = PersonalConditionDto.builder()
                .age(99).bodyType("뚱뚱하다.").hobby("잔디깍이").exercise("산책").medical("당뇨").build();

        service.createOrUpdatePersonalCondition(user, personalCondition1);

        PersonalConditionDto personalCondition2 = PersonalConditionDto.builder()
                .age(99).bodyType("").hobby("").exercise("산책").medical("").build();

        PersonalConditionDto result = service.createOrUpdatePersonalCondition(user, personalCondition2);
        Assertions.assertThat(result.getUserId()).isEqualTo(user.getId());
        Assertions.assertThat(result.getBodyType()).isEqualTo("");

    }


    @Test
    void deleteByUserId() {
        UserDetailsImpl user = new UserDetailsImpl();
        user.setId(4L);

        PersonalConditionDto personalCondition = PersonalConditionDto.builder()
                .age(99).bodyType("뚱뚱하다.").hobby("잔디깍이").exercise("산책").medical("당뇨").build();

        service.createOrUpdatePersonalCondition(user, personalCondition);

        service.deleteByUserId(4L);
        boolean existPersonalConditionById = service.existPersonalConditionByUserId(4L);

        Assertions.assertThat(existPersonalConditionById).isFalse();
    }
}