package com.exmaple.demo.utils;

import com.exmaple.demo.domain.model.Exceldata;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WriteExcelDataUtil {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_CREDIT_NUM = 1;
    public static final int COLUMN_INDEX_COURSE_NAME = 2;
    public static final int COLUMN_INDEX_TEACHER_NAME = 3;
    public static final int COLUMN_INDEX_ll = 4;
    public static final int COLUMN_INDEX_NUMBER_OF_PERIODS = 5;
    public static final int COLUMN_INDEX_COEFFICIENT = 6;
    public static final int COLUMN_INDEX_NUMBER_OF_STUDENT = 7;
    public static final int COLUMN_INDEX_GRADE_COEFFICIENT = 8;
    public static final int COLUMN_INDEX_COMPLIANCE_STANDARD = 9;
    public static final int COLUMN_INDEX_NOTE = 10;
    private static CellStyle cellStyleFormatNumber = null;

    public static void writeExcel(List<Exceldata> exceldataList, HttpServletResponse response) throws IOException {
        // Create Workbook
        Workbook workbook = new HSSFWorkbook();
        // Create sheet
        Sheet sheet = workbook.createSheet("Exceldata"); // Create sheet with sheet name


        int rowIndex = 0;
//        Write title
        writeTitle(sheet, rowIndex);
        rowIndex++;

        // Write header
        writeHeader(sheet, rowIndex);
        rowIndex++;
        // Write data

        for (Exceldata exceldata : exceldataList) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            Exceldata(exceldata, row);
            rowIndex++;
        }

        // Write footer
//        writeFooter(sheet, rowIndex);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, response);
    }

    // Create dummy data
    private static List<Exceldata> getExceldataList() {
        List<Exceldata> listExceldata = new ArrayList<>();
        Exceldata exceldata;
        for (int i = 1; i <= 5; i++) {
            exceldata = new Exceldata();
            listExceldata.add(exceldata);
        }
        return listExceldata;
    }

    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TT");

        cell = row.createCell(COLUMN_INDEX_CREDIT_NUM);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Số TC");

        cell = row.createCell(COLUMN_INDEX_COURSE_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Lớp học phần");

        cell = row.createCell(COLUMN_INDEX_TEACHER_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Giáo Viên");

        cell = row.createCell(COLUMN_INDEX_ll);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("LL");

        cell = row.createCell(COLUMN_INDEX_NUMBER_OF_PERIODS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Số tiết CTĐT");

        cell = row.createCell(COLUMN_INDEX_COEFFICIENT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Hệ số T7/CN");

        cell = row.createCell(COLUMN_INDEX_NUMBER_OF_STUDENT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Số SV");

        cell = row.createCell(COLUMN_INDEX_GRADE_COEFFICIENT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Hệ số lớp đông");

        cell = row.createCell(COLUMN_INDEX_COMPLIANCE_STANDARD);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("QUY CHUẨN");

        cell = row.createCell(COLUMN_INDEX_NOTE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ghi chú");
    }

    // Write title with format
    private static void writeTitle(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForTitle(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("SỐ TIẾT QUY CHUẨN HỌC KỲ 1 NĂM HỌC 2023-2024 (TTTH)");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
    }

    // Write data
    private static void Exceldata(Exceldata exceldata, Row row) {
        CellStyle cellStyleFormat = row.getSheet().getWorkbook().createCellStyle();
        cellStyleFormat.setAlignment(HorizontalAlignment.CENTER); // Căn giữa theo chiều ngang
        cellStyleFormat.setVerticalAlignment(VerticalAlignment.CENTER);

        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }
        DecimalFormat df = new DecimalFormat("#.#");

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(exceldata.getId());

        cell = row.createCell(COLUMN_INDEX_CREDIT_NUM);
        cell.setCellValue(exceldata.getCreditNum());

        cell = row.createCell(COLUMN_INDEX_COURSE_NAME);
        cell.setCellValue(exceldata.getCourseName());

        cell = row.createCell(COLUMN_INDEX_TEACHER_NAME);
        cell.setCellValue(exceldata.getTeacherName());

        cell = row.createCell(COLUMN_INDEX_ll);
        cell.setCellValue(exceldata.getLL());


        cell = row.createCell(COLUMN_INDEX_NUMBER_OF_PERIODS);
        cell.setCellValue(exceldata.getNumberOfPeriods());

        cell = row.createCell(COLUMN_INDEX_COEFFICIENT);
        cell.setCellValue(df.format(exceldata.getCoefficient().setScale(1, RoundingMode.HALF_UP)));

        cell = row.createCell(COLUMN_INDEX_NUMBER_OF_STUDENT);
        cell.setCellValue(exceldata.getNumberOfStudents());

        cell = row.createCell(COLUMN_INDEX_GRADE_COEFFICIENT);
        cell.setCellValue(df.format(exceldata.getGradeCoefficient().setScale(1, RoundingMode.HALF_UP)));

        cell = row.createCell(COLUMN_INDEX_COMPLIANCE_STANDARD);
        cell.setCellValue(exceldata.getComplianceStandard());

        cell = row.createCell(COLUMN_INDEX_NOTE);
        cell.setCellValue(exceldata.getNote());

//        // Create cell formula
//        // totalMoney = price * quantity
//        cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellStyle(cellStyleFormatNumber);
//        int currentRow = row.getRowNum() + 1;
//        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
//        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
//        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 13); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
//        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // Căn giữa theo chiều ngang
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Căn giữa theo chiều dọc
        return cellStyle;
    }

    // Create CellStyle for title
    private static CellStyle createStyleForTitle(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // Căn giữa theo chiều ngang
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Căn giữa theo chiều dọc

        return cellStyle;
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, HttpServletResponse response) throws IOException {
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }
}
