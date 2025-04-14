package nki.ClimCue.util;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Component
public class ValidVilageFcstPointsOrg {

    @Setter
    @Value("${resources.excel.vilageFcst}")
    private Resource vilageFcst_file;

    private Set<Point> validPoints = new HashSet<>();

    @PostConstruct
    public void init() {
        readExcelData(vilageFcst_file);
    }

    private void readExcelData(Resource filePath) {
        System.out.println("ValidVilageFcstPoints.readExcelData");

        try (InputStream is = vilageFcst_file.getInputStream()) {

            ZipSecureFile.setMinInflateRatio(0);
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);

            // 첫번째 열은 건너뛴다
            boolean isFirstSheet = true;
            for (Row row : sheet) {
                if (isFirstSheet) {
                    isFirstSheet = false;
                    continue;
                }

                Cell cellNx = row.getCell(5);
                Cell cellNy = row.getCell(6);

                if (cellNx != null && cellNy != null) {
                    int nx = cellToInt(cellNx);
                    int ny = cellToInt(cellNy);
                    validPoints.add(new Point(nx, ny));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("validPoints = " + validPoints);
    }

    private int cellToInt(Cell cell) {
        if(cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else {
            return Integer.parseInt(cell.getStringCellValue());
        }
    }

    public boolean isValidPoint(VilageFcstNxNyDto vilageFcstNxNyDto) {
        return validPoints.contains(new Point(vilageFcstNxNyDto.getNx(), vilageFcstNxNyDto.getNy()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValidVilageFcstPoints{");
        sb.append("vilageFcst_file='").append(vilageFcst_file).append('\'');
        sb.append(", validPoints=");
        if (validPoints.isEmpty()) {
            sb.append("No valid points");
        } else {
            sb.append(validPoints);
        }
        sb.append('}');
        return sb.toString();
    }


}

