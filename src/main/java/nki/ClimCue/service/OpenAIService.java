package nki.ClimCue.service;

import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItem;
import nki.ClimCue.model.member.PersonalConditionDto;
import nki.ClimCue.model.api.openAi.OpenAIResponseCompletion;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItems;

import java.util.List;

public interface OpenAIService {
    OpenAIResponseCompletion prompt(PersonalConditionDto personalConditionDto, List<VilageFcstUltraSrtItem> vilageFcstUltraSrtItems);
}
