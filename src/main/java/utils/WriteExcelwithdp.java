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
        File file = new File(path);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            XSSFWorkbook workbook = null;
            
            // Safe Load Check: Prevents NotOfficeXmlFileException crashes due to file corruption
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                } catch (Exception e) {
                    System.err.println("⚠️ Existing Excel file was corrupted or unreadable. Resetting template file structural integrity...");
                    workbook = null; // Forces fresh generation below
                }
            }

            // Fallback generation logic if the file is fresh, empty, or corrupted
            if (workbook == null) {
                workbook = new XSSFWorkbook();
            }

            // Kept only your data-driven inputs and output tracking sheets
            String[] sheets = {"FormInputsData", "SmokeSuite", "RegressionSuite"};
            for (String sheetName : sheets) {
                if (workbook.getSheet(sheetName) == null) {
                    Sheet sheet = workbook.createSheet(sheetName);
                    Row header = sheet.createRow(0);
                    
                    if (sheetName.endsWith("Suite")) {
                        header.createCell(0).setCellValue("Test Case / Scenario Name");
                        header.createCell(1).setCellValue("Status");
                        header.createCell(2).setCellValue("Execution Timestamp");
                    } else if (sheetName.equals("FormInputsData")) {
                        header.createCell(0).setCellValue("Name");
                        header.createCell(1).setCellValue("Email");
                        header.createCell(2).setCellValue("Phone");
                        header.createCell(3).setCellValue("Address");
                        
                        Row dataRow = sheet.createRow(1);
                        dataRow.createCell(0).setCellValue("Bhargav");
                        dataRow.createCell(1).setCellValue("bhargav@gmail.com");
                        dataRow.createCell(2).setCellValue("9876543210");
                        dataRow.createCell(3).setCellValue("Kakinada");
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                fos.flush();
            }
            workbook.close();
            System.out.println("✅ Master Excel File verified/initialized safely at: " + path);

        } catch (Exception e) {
            System.err.println("❌ Failed to verify excel path structure: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Object[][] readInputMatrix(String sheetName) {
        Object[][] data = null;
        File file = new File(path);
        if (!file.exists()) return new Object[0][0];

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
                    if (row == null) {
                        data[i - 1][j] = "";
                    } else {
                        Cell cell = row.getCell(j);
                        data[i - 1][j] = formatter.formatCellValue(cell).trim();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error reading data input matrix: " + e.getMessage());
        }
        return data;
    }

    // ================= SAFE MULTI-ROW SUITE RESULTS LOGGER =================
    public synchronized void logSuiteResult(String suiteSheetName, String testName, String status) {
        File file = new File(path);
        XSSFWorkbook workbook = null;

        // 1. ALWAYS load current live layout from disk to preserve row 1 before writing row 2
        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = new XSSFWorkbook(fis);
        } catch (Exception e) {
            System.err.println("❌ Error opening Excel for scenario status logging: " + e.getMessage());
            return;
        }

        try {
            Sheet sheet = workbook.getSheet(suiteSheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(suiteSheetName);
            }

            // 2. Safe calculation: finds the true bottom of the sheet dynamically
            int nextRowIndex = sheet.getPhysicalNumberOfRows(); 
            if (nextRowIndex == 0) {
                // Create headers if the sheet is completely blank
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Test Case / Scenario Name");
                header.createCell(1).setCellValue("Status");
                header.createCell(2).setCellValue("Execution Timestamp");
                nextRowIndex = 1;
            }

            Row row = sheet.createRow(nextRowIndex);
            String timeStampStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // 3. Append the incoming scenario iteration data seamlessly
            row.createCell(0).setCellValue(testName);
            row.createCell(1).setCellValue(status); 
            row.createCell(2).setCellValue(timeStampStr);

            // 4. Force save back to the file system immediately
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                fos.flush();
            }
            workbook.close();
            System.out.println("📌 Saved to Excel -> Sheet: [" + suiteSheetName + "] at Row: " + nextRowIndex + " for data profile.");

        } catch (Exception e) {
            System.err.println("❌ Error appending iteration data: " + e.getMessage());
        }
    }
}