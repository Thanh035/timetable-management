package com.exmaple.demo.utils;

import com.exmaple.demo.domain.model.Exceldata;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadExcelDataUtil {
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


    public static List<Exceldata> readExcel(String excelFilePath, InputStream inputStream) throws IOException {
        List<Exceldata> exceldataList = new ArrayList<>();

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0 || nextRow.getRowNum() == 1) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            Exceldata exceldata = new Exceldata();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }

                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_ID:
                        exceldata.setId(((Number) cellValue).longValue());
                        break;
                    case COLUMN_INDEX_CREDIT_NUM:
                        exceldata.setCreditNum(((Number) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_COURSE_NAME:
                        exceldata.setCourseName(String.valueOf(cellValue));
                        break;
                    case COLUMN_INDEX_TEACHER_NAME:
                        exceldata.setTeacherName(String.valueOf(cellValue));
                        break;
                    case COLUMN_INDEX_ll:
                        exceldata.setLL(((Number) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_NUMBER_OF_PERIODS:
                        exceldata.setNumberOfPeriods(((Number) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_COEFFICIENT:
                        exceldata.setCoefficient(convertToBigDecimal(cellValue));
                        break;
                    case COLUMN_INDEX_NUMBER_OF_STUDENT:
                        exceldata.setNumberOfStudents(((Number) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_GRADE_COEFFICIENT:
                        exceldata.setGradeCoefficient(convertToBigDecimal(cellValue));
                        break;
                    case COLUMN_INDEX_COMPLIANCE_STANDARD:
                        exceldata.setComplianceStandard(((Number) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_NOTE:
                        exceldata.setNote(String.valueOf(cellValue));
                        break;
                    default:
                        break;
                }

            }
            exceldataList.add(exceldata);
        }

        workbook.close();
        inputStream.close();

        return exceldataList;
    }

    private static BigDecimal convertToBigDecimal(Object obj) {
        if (obj instanceof String) {
            return new BigDecimal((String) obj);
        } else if (obj instanceof Double) {
            return BigDecimal.valueOf((Double) obj);
        } else if (obj instanceof Integer) {
            return BigDecimal.valueOf((Integer) obj);
        } else if (obj instanceof Long) {
            return BigDecimal.valueOf((Long) obj);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
        }
    }

    // Get Workbook
    public static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        if (excelFilePath == null || (!excelFilePath.endsWith(".xlsx") && !excelFilePath.endsWith(".xls"))) {
            throw new IllegalArgumentException("The specified file is not an Excel file");
        }

        Workbook workbook;
        try {
            if (excelFilePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            throw new IOException("Error reading Excel file", e);
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return null;
        }

//        return cellValue;
    }
}
