package nki.ClimCue.model.api.vilageFcst;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import nki.ClimCue.validation.VilageFcstPointCheck;

@VilageFcstPointCheck
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VilageFcstNxNyDto {
    @NotNull
    private Integer nx;

    @NotNull
    private Integer ny;
}
