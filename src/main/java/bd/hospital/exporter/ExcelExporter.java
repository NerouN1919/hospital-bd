package bd.hospital.exporter;

import bd.hospital.dto.SummaryDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public byte[] exportToExcel(List<SummaryDto> summaryList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Summary");

        Row headerRow = sheet.createRow(0);
        createCell(headerRow, 0, "ID");
        createCell(headerRow, 1, "Имя");
        createCell(headerRow, 2, "Фамилия");
        createCell(headerRow, 3, "Отчество");
        createCell(headerRow, 4, "Диагноз");
        createCell(headerRow, 5, "Палата");
        createCell(headerRow, 6, "Вместимость палаты");

        for (int i = 0; i < summaryList.size(); i++) {
            SummaryDto summary = summaryList.get(i);
            Row row = sheet.createRow(i + 1);
            createCell(row, 0, summary.getId().toString());
            createCell(row, 1, summary.getFirstName());
            createCell(row, 2, summary.getLastName());
            createCell(row, 3, summary.getPatherName());
            createCell(row, 4, summary.getDiagnosisName());
            createCell(row, 5, summary.getWardName());
            createCell(row, 6, summary.getMaxCount().toString());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    private void createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }
}
