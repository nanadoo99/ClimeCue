package nki.ClimCue.service;

import nki.ClimCue.model.member.PersonalCondition;
import nki.ClimCue.model.member.PersonalConditionDto;
import nki.ClimCue.model.member.UserDetailsImpl;
import nki.ClimCue.repository.PersonalConditionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonalConditionService {

    private final PersonalConditionRepository personalConditionRepository;

    public PersonalConditionService(PersonalConditionRepository personalConditionRepository) {
        this.personalConditionRepository = personalConditionRepository;
    }

    @Transactional
    public boolean existPersonalConditionByUserId(Long userId) {
        return personalConditionRepository.existsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public PersonalConditionDto findByUserId(Long userId) {
        PersonalCondition personalCondition =  personalConditionRepository.findByUserId(userId).orElse(new PersonalCondition());
        System.out.println("PersonalConditionService.findByUserId.personalCondition = " + personalCondition);
        return PersonalConditionDto.toDto(personalCondition);
    }
/*
    @Transactional(readOnly = true)
    public Optional<PersonalConditionDto> findByUserId(Long userId) {
        return personalConditionRepository.findByUserId(userId).map(PersonalConditionDto :: toDto);
    }*/

/*    @Transactional
    public PersonalConditionDto createPersonalCondition(UserDetailsImpl user, PersonalConditionDto personalConditionDto) {
        personalConditionDto.setUserId(user.getId());
        PersonalCondition personalCondition = personalConditionRepository.save(personalConditionDto.toEntity());
        return PersonalConditionDto.toDto(personalCondition);
    }

    @Transactional
    public PersonalConditionDto updatePersonalCondition(UserDetailsImpl user, PersonalConditionDto personalConditionDto) {
        personalConditionDto.setUserId(user.getId());
        PersonalCondition personalCondition =  personalConditionRepository.save(personalConditionDto.toEntity());
        return PersonalConditionDto.toDto(personalCondition);
    }*/

    @Transactional
    public PersonalConditionDto createOrUpdatePersonalCondition(UserDetailsImpl user, PersonalConditionDto personalConditionDto) {
        Optional<PersonalCondition> existingEntity = personalConditionRepository.findByUserId(user.getId());
        PersonalCondition result;

        if (existingEntity.isPresent()) {
            PersonalCondition foundEntity = existingEntity.get();
            foundEntity.updatePersonalCondition(personalConditionDto.toEntity());
            result = personalConditionRepository.save(foundEntity);
        } else {
            personalConditionDto.setUserId(user.getId());
            result = personalConditionRepository.save(personalConditionDto.toEntity());
        }
        return PersonalConditionDto.toDto(result);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        personalConditionRepository.deleteByUserId(userId);
    }
}
