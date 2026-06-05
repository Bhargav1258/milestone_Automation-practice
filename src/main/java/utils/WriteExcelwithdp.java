package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelwithdp {

    private final String path = "C:\\Users\\Admin\\exxcel1\\TestData123.xlsx";

    public void createExcelFile() {
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (file.exists()) {
                file.delete();
            }

            XSSFWorkbook workbook = new XSSFWorkbook();

            // Inputs
            Sheet formSheet = workbook.createSheet("FormInputsData");
            Row headerRow = formSheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Email");
            headerRow.createCell(2).setCellValue("Phone");
            headerRow.createCell(3).setCellValue("Address");

            // Seed sample row so DataProvider functions correctly on first run
            Row dataRow = formSheet.createRow(1);
            dataRow.createCell(0).setCellValue("Bhargav");
            dataRow.createCell(1).setCellValue("bhargav@gmail.com");
            dataRow.createCell(2).setCellValue("9876543210");
            dataRow.createCell(3).setCellValue("Kakinada");

            workbook.createSheet("WikipediaKeywords");

            // Outputs
            workbook.createSheet("SmokeSuite");
            workbook.createSheet("RegressionSuite");

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            workbook.close();
            System.out.println("✅ Unified Excel workbook initialized at: " + path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[][] readInputMatrix(String sheetName) {
        Object[][] data = null;
        File file = new File(path);

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return new Object[0][0];

            int rowCount = sheet.getLastRowNum();
            if (rowCount < 1) return new Object[0][0];

            int colCount = sheet.getRow(0).getLastCellNum();
            data = new Object[rowCount][colCount];
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = (row != null) ? row.getCell(j) : null;
                    data[i - 1][j] = formatter.formatCellValue(cell).trim();
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error reading data input matrix: " + e.getMessage());
        }
        return data;
    }

    // ================= UPGRADED SAFE OUTPUT RECORDER ENGINE =================
    public synchronized void writeSuiteResults(String suiteSheetName, String testCaseName, String executionStatus) {
        File file = new File(path);
        XSSFWorkbook workbook = null;

        // 1. Read existing workbook layout safely into memory
        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = new XSSFWorkbook(fis);
        } catch (Exception e) {
            System.err.println("❌ Error opening Excel data stream: " + e.getMessage());
            return;
        }

        // 2. Append metrics row safely to the specified sheet
        try {
            Sheet sheet = workbook.getSheet(suiteSheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(suiteSheetName);
            }

            // High-safety row index calculation
            int nextRowIndex = 0;
            if (sheet.getPhysicalNumberOfRows() > 0) {
                nextRowIndex = sheet.getLastRowNum() + 1;
            }
            
            Row row = sheet.createRow(nextRowIndex);
            String timeStampStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Write test results directly into the spreadsheet columns
            row.createCell(0).setCellValue(testCaseName);
            row.createCell(1).setCellValue(executionStatus);
            row.createCell(2).setCellValue(timeStampStr);

            // 3. Force save data to disk and release file lock instantly
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                fos.flush();
            }
            workbook.close();
            System.out.println("📌 Saved runtime execution log to: [" + suiteSheetName + "] at row " + nextRowIndex);

        } catch (Exception e) {
            System.err.println("❌ Error saving runtime data row to report sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }
}