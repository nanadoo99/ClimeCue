package nki.ClimCue.model.api.openAi;

import nki.ClimCue.model.member.PersonalConditionDto;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItem;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItems;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OpenAIRequestMessageBuilder {
    private List<OpenAIRequestMessage> messages = new ArrayList<>();
    private final ResourceBundle messageBundle;

    public OpenAIRequestMessageBuilder() {
        messageBundle = ResourceBundle.getBundle("openAiMessages");
    }

    public static OpenAIRequestDto buildRequest(PersonalConditionDto personalConditionDto, List<VilageFcstUltraSrtItem> vilageFcstUltraSrtItems) {
        System.out.println("OpenAIRequestMessageBuilder.buildRequest");
        return new OpenAIRequestMessageBuilder()
                .addPersonalConditionMessage(personalConditionDto == null ? new PersonalConditionDto() : personalConditionDto)
                .addVilageFcstUltraSrtItemMessage(vilageFcstUltraSrtItems).build();
    }

    private OpenAIRequestMessageBuilder addPersonalConditionMessage(PersonalConditionDto dto) {
        StringBuffer message = new StringBuffer();
        String commonMsg = messageBundle.getString("common.message");

        if (dto != null) {
            // String: immutable >> 값 변경시 새로운 객체로 변경됨
            // StringBuffer: mutable >> 기존 객체에서 변경 이루어짐.
            // StringBuilder: mutable & 멀티스레드 환경에서 안정적이지 않으나 싱글스레드 환경에서 StringBuffer 보다 빠름.
            boolean hasContent = false;

            if (dto.getAge() != null) {
                message.append("연령은 ").append(dto.getAge()).append("세이다. ");
                hasContent = true;
            }
            if (dto.getBodyType() != null && !dto.getBodyType().isEmpty()) {
                message.append("체질은 ").append(dto.getBodyType()).append("편이다. ");
                hasContent = true;
            }
            if (dto.getHobby() != null && !dto.getHobby().isEmpty()) {
                message.append("취미는 ").append(dto.getHobby()).append("이다. ");
                hasContent = true;
            }
            if (dto.getExercise() != null && !dto.getExercise().isEmpty()) {
                message.append("선호하는 운동은 ").append(dto.getExercise()).append("이다. ");
                hasContent = true;
            }
            if (dto.getMedical() != null && !dto.getMedical().isEmpty()) {
                message.append("건강 상태는 ").append(dto.getMedical()).append("이다. ");
                hasContent = true;
            }

            if (hasContent) {
                message.append(messageBundle.getString("custom.message"));
            }
        }
            message.append(commonMsg);
            addOpenAIRequestMessage(OpenAIRole.SYSTEM, message);

        return this;
    }

    private OpenAIRequestMessageBuilder addVilageFcstUltraSrtItemMessage(List<VilageFcstUltraSrtItem> vilageFcstUltraSrtItems) {
        StringBuffer message = new StringBuffer();

        for(VilageFcstUltraSrtItem vilageFcstUltraSrtItem : vilageFcstUltraSrtItems) {
            message.append(vilageFcstUltraSrtItem.getCategoryName()).append(": ").append(vilageFcstUltraSrtItem.getObsrValueWithUnit()).append("; ");
        }

        addOpenAIRequestMessage(OpenAIRole.USER, message);

        return this;
    }

    private void addOpenAIRequestMessage(OpenAIRole role, StringBuffer sb) {
        messages.add(new OpenAIRequestMessage(role, sb.toString().strip()));
    }

    private OpenAIRequestDto build(){
        return new OpenAIRequestDto(messages);
    }
}