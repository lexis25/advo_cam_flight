package com.super_cargo.IO;

import com.super_cargo.Flight;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FlightScheduleExcel {

    private static final String[] ARRIVAL = {"№ Рейса", "Прилет", "Ст", "Время "};
    private static final String[] DEPARTING = {"№ Рейса", "Вылет", "Ст", "Регист.", "Время", "П", "Б"};
    private static final int[] arraySize = {3500, 7500, 1500, 3000, 3000, 1800, 1800};
    private static final SimpleDateFormat PATTERN = new SimpleDateFormat("HH:mm");
    private static final String PATH = "H:/FOLDER/scheduleExcel.xls";

    private static HSSFWorkbook book;


    public FlightScheduleExcel(List<Flight> arrival, List<Flight> departing) throws IOException {
        create(arrival, departing);
    }


    private static void create(List<Flight> arrivalList, List<Flight> departingList) throws IOException {

        book = new HSSFWorkbook();
        Sheet arrivalSheet = book.createSheet("прилет");

        for (int i = 0; i < ARRIVAL.length; i++) {
            arrivalSheet.setColumnWidth(i, arraySize[i]);
        }

        CellStyle styleHat = book.createCellStyle();
        styleHat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHat.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleHat.setAlignment(HorizontalAlignment.CENTER);
        styleHat.setVerticalAlignment(VerticalAlignment.CENTER);
        makeBorders(styleHat);

        Font fontHat = createFont("Arial", (short) 14);
        fontHat.setBold(true);
        styleHat.setFont(fontHat);

        CellStyle styleContains = book.createCellStyle();
        styleContains.setVerticalAlignment(VerticalAlignment.CENTER);
        makeBorders(styleContains);

        Font fontContains = createFont("Arial", (short) 14);
        styleContains.setFont(fontContains);

        CellStyle styleCenterCell = book.createCellStyle();
        styleCenterCell.setVerticalAlignment(VerticalAlignment.CENTER);
        styleCenterCell.setAlignment(HorizontalAlignment.CENTER);
        makeBorders(styleCenterCell);
        styleCenterCell.setFont(fontContains);

        Row rowArrival = arrivalSheet.createRow(0);
        rowArrival.setHeightInPoints(28);

        setHat(ARRIVAL, rowArrival, styleHat);

        fillCell(arrivalList, true, rowArrival, arrivalSheet, styleContains, styleCenterCell);

        Sheet departingSheet = book.createSheet("вылет");

        for (int i = 0; i < DEPARTING.length; i++) {
            departingSheet.setColumnWidth(i, arraySize[i]);
        }

        Row rowDeparting = departingSheet.createRow(0);
        rowDeparting.setHeightInPoints(28);

        setHat(DEPARTING, rowDeparting, styleHat);

        fillCell(departingList, false, rowDeparting, departingSheet, styleContains, styleCenterCell);

        FileOutputStream fos = new FileOutputStream(PATH);
        book.write(fos);
        fos.close();
    }

    private static void setHat(String[] arrivalOrDeparting, Row row, CellStyle style) {
        for (int i = 0; i < arrivalOrDeparting.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(arrivalOrDeparting[i]);
        }

    }

    private static Font createFont(String nameFont, short size) {
        Font font = book.createFont();
        font.setFontName(nameFont);
        font.setFontHeightInPoints(size);
        return font;
    }

    private static void makeBorders(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }

    private static void fillCell(List<Flight> schedule, boolean arrivalOrDeparting,
                                 Row row, Sheet sheet, CellStyle styleOne, CellStyle styleTwo) {
        int counter = 1;
        for (int i = 0; i < schedule.size(); i++) {
            row = sheet.createRow(counter);
            row.setHeightInPoints(28);
            if (arrivalOrDeparting) {
                Cell cell_zero = row.createCell(0);
                cell_zero.setCellStyle(styleOne);
                cell_zero.setCellValue(schedule.get(i).getNumberFlight());

                Cell cell_one = row.createCell(1);
                cell_one.setCellStyle(styleOne);
                cell_one.setCellValue(schedule.get(i).getDirectionFlight());

                Cell cell_two = row.createCell(2);
                cell_two.setCellStyle(styleOne);

                Cell cell_three = row.createCell(3);
                cell_three.setCellStyle(styleTwo);
                cell_three.setCellValue(String.valueOf(PATTERN.format(schedule.get(i).getTimeFlight().getTime())));

            } else {
                Cell cell_zero = row.createCell(0);
                cell_zero.setCellStyle(styleOne);
                cell_zero.setCellValue(schedule.get(i).getNumberFlight());

                Cell cell_one = row.createCell(1);
                cell_one.setCellStyle(styleOne);
                cell_one.setCellValue(schedule.get(i).getDirectionFlight());

                Cell cell_two = row.createCell(2);
                cell_two.setCellStyle(styleOne);

                Cell cell_three = row.createCell(3);
                cell_three.setCellStyle(styleOne);

                Cell cell_four = row.createCell(4);
                cell_four.setCellStyle(styleTwo);
                cell_four.setCellValue(String.valueOf(PATTERN.format(schedule.get(i).getTimeFlight().getTime())));

                Cell cell_five = row.createCell(5);
                cell_five.setCellStyle(styleOne);

                Cell cell_six = row.createCell(6);
                cell_six.setCellStyle(styleOne);

            }
            counter++;
        }
    }
}