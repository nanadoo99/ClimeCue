package nki.ClimCue.model.api.vilageFcst;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nki.ClimCue.validation.VilageFcstPointCheck;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VilageFcstLatLngDto {
    @NotNull
    private Double lat;

    @NotNull
    private Double lng;
}
