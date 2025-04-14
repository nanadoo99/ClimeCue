package nki.ClimCue.util;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class ValidVilageFcstPoints {

    private static final int NX_COLUMN_INDEX = 5;
    private static final int NY_COLUMN_INDEX = 6;

    @Setter
    @Value("${resources.excel.vilageFcst}")
    private Resource vilageFcstFile;

    private final Set<GridPoint> validPoints = new HashSet<>();

    @PostConstruct
    public void init() {
        readExcelData(vilageFcstFile);
    }

    private void readExcelData(Resource file) {
        log.info("Loading valid village forecast points from Excel");

        try (InputStream is = file.getInputStream()) {
            ZipSecureFile.setMinInflateRatio(0);
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                Cell cellNx = row.getCell(NX_COLUMN_INDEX);
                Cell cellNy = row.getCell(NY_COLUMN_INDEX);

                if (cellNx != null && cellNy != null) {
                    int nx = parseCellToInt(cellNx);
                    int ny = parseCellToInt(cellNy);
                    validPoints.add(new GridPoint(nx, ny));
                }
            }

            log.info("Loaded {} valid grid points", validPoints.size());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read village forecast Excel file", e);
        }
    }

    private int parseCellToInt(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Integer.parseInt(cell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Unsupported cell type for parsing: " + cell.getCellType());
        }
    }

    public boolean isValidPoint(VilageFcstNxNyDto dto) {
        return validPoints.contains(new GridPoint(dto.getNx(), dto.getNy()));
    }

    @Override
    public String toString() {
        return "ValidVilageFcstPoints{" +
                "vilageFcstFile=" + vilageFcstFile +
                ", validPointsCount=" + validPoints.size() +
                '}';
    }

    private static class GridPoint {
        private final int nx;
        private final int ny;

        public GridPoint(int nx, int ny) {
            this.nx = nx;
            this.ny = ny;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GridPoint)) return false;
            GridPoint other = (GridPoint) o;
            return nx == other.nx && ny == other.ny;
        }

        @Override
        public int hashCode() {
            return Objects.hash(nx, ny);
        }

        @Override
        public String toString() {
            return "(" + nx + ", " + ny + ")";
        }
    }
}
