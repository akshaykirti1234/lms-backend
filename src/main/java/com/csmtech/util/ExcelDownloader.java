package com.csmtech.util;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelDownloader {

private static final Logger log = LoggerFactory.getLogger(ExcelDownloader.class);

@SuppressWarnings("unchecked")
public static Workbook buildExcelDocument(Map<String, Object> model) throws Exception {
    Workbook workbook = null;
    try {
        workbook = new XSSFWorkbook();
        Map<String, String[]> map = (HashMap<String, String[]>) model.get("sampleData");

        // Order of the creation of these sheets is important as the 2nd sheet is hidden
        // 1st sheet
        XSSFSheet realSheet = ((XSSFWorkbook) workbook).createSheet("Sheet1");
        realSheet.setDefaultColumnWidth(12);
        XSSFRow headRow = realSheet.createRow(0);
        // 2nd sheet
        XSSFSheet hiddenSheet = ((XSSFWorkbook) workbook).createSheet("hidden");
        hiddenSheet.setDefaultColumnWidth(12);

        // Converting all columns to text type
        DataFormat fmt = workbook.createDataFormat();
        CellStyle textStyle = workbook.createCellStyle();
        textStyle.setDataFormat(fmt.getFormat("@"));
        for(int i=0;i<9;i++)
            realSheet.setDefaultColumnStyle(i, textStyle);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 8);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headRow.createCell(0).setCellValue("Module Type");
        headRow.createCell(1).setCellValue("SubModule");
        headRow.createCell(2).setCellValue("ScheduleFor");
        headRow.createCell(3).setCellValue("Question");
        headRow.createCell(4).setCellValue("Option-1");
        headRow.createCell(5).setCellValue("Option-2");
        headRow.createCell(6).setCellValue("Option-3");
        headRow.createCell(7).setCellValue("Option-4");
        headRow.createCell(8).setCellValue("Answer");
        headRow.setRowStyle(headerCellStyle);

        Name moduleRange = workbook.createName();
        moduleRange.setNameName("moduleRange");
        Name subModuleRange = workbook.createName();
        subModuleRange.setNameName("subModuleRange");
        Name scheduleForRange = workbook.createName();
        scheduleForRange.setNameName("scheduleForRange");

        String[] moduleTypeList = map.get("moduleTypeList");
        String[] subModuleList = map.get("subModuleList");
        String[] scheduleForList = map.get("scheduleForList");

        int limit = 500;

        int hiddenRows = Arrays.asList(moduleTypeList.length,subModuleList.length,
            scheduleForList.length)
            .stream().mapToInt(v->v).max().orElse(0);
        for(int i=0;i<hiddenRows;i++) {
            hiddenSheet.createRow(i);
        }

        for(int i=0;i<moduleTypeList.length;i++) {
            hiddenSheet.getRow(i).createCell(0).setCellValue(moduleTypeList[i]);
        }
        for(int i=0;i<subModuleList.length;i++) {
            hiddenSheet.getRow(i).createCell(1).setCellValue(subModuleList[i]);
        }
        for(int i=0;i<scheduleForList.length;i++) {
            hiddenSheet.getRow(i).createCell(2).setCellValue(scheduleForList[i]);
        }

        DataValidation dataValidation = null;
        DataValidationConstraint constraint = null;
        DataValidationHelper validationHelper = null;
        validationHelper = new XSSFDataValidationHelper(realSheet);

        // Gender column
        CellRangeAddressList addressList = new CellRangeAddressList(1, limit, 0, 0);
        moduleRange.setRefersToFormula("hidden!$A$1:$A$"+moduleTypeList.length);
        constraint = validationHelper.createFormulaListConstraint("moduleRange");
        dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        dataValidation.createErrorBox("Invalid Data", "Please select valid " + realSheet.getRow(0).getCell(0).getStringCellValue());
        if(dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        else
            dataValidation.setSuppressDropDownArrow(false);
        realSheet.addValidationData(dataValidation);

        // User Category column
        addressList = new CellRangeAddressList(1, limit, 1, 1);
        subModuleRange.setRefersToFormula("hidden!$B$1:$B$"+subModuleList.length);
        constraint = validationHelper.createFormulaListConstraint("subModuleRange");
        dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        dataValidation.createErrorBox("Invalid Data", "Please select valid " + realSheet.getRow(0).getCell(1).getStringCellValue());
        if(dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        else
            dataValidation.setSuppressDropDownArrow(false);
        realSheet.addValidationData(dataValidation);

        // Thematic Area column
        addressList = new CellRangeAddressList(1, limit, 2, 2);
        scheduleForRange.setRefersToFormula("hidden!$C$1:$C$"+scheduleForList.length);
        constraint = validationHelper.createFormulaListConstraint("scheduleForRange");
        dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        dataValidation.createErrorBox("Invalid Data", "Please select valid " + realSheet.getRow(0).getCell(2).getStringCellValue());
        if(dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        else
            dataValidation.setSuppressDropDownArrow(false);
        realSheet.addValidationData(dataValidation);

    } catch (Exception e) {
        log.error("error ExcelDownloader :: buildExcelDocument() !!", e);
    }

    return workbook;
}
}

