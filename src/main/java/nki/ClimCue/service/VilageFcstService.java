package nki.ClimCue.service;

import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItems;

public interface VilageFcstService {
    VilageFcstUltraSrtItems ultraSrtNcst(VilageFcstNxNyDto vilageFcstNxNyDto) throws Exception;
}
