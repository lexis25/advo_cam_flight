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


    FlightScheduleExcel(List<Flight> arrival, List<Flight> departing) throws IOException {
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

        setHat(ARRIVAL,rowArrival,styleHat);

        int counter = 1;
        for (int i = 0; i < arrivalList.size(); i++) {
            rowArrival = arrivalSheet.createRow(counter);
            rowArrival.setHeightInPoints(28);
            Cell cell2 = rowArrival.createCell(0);
            cell2.setCellStyle(styleContains);
            cell2.setCellValue(arrivalList.get(i).getNumberFlight());
            Cell cell3 = rowArrival.createCell(1);
            cell3.setCellStyle(styleContains);
            cell3.setCellValue(arrivalList.get(i).getDirectionFlight());
            Cell cell4 = rowArrival.createCell(3);
            cell4.setCellStyle(styleCenterCell);
            cell4.setCellValue(String.valueOf(PATTERN.format(arrivalList.get(i).getTimeFlight().getTime())));
            counter++;
        }
        counter = 1;

        Sheet departingSheet = book.createSheet("вылет");

        for (int i = 0; i < DEPARTING.length; i++) {
            departingSheet.setColumnWidth(i, arraySize[i]);
        }

        Row rowDeparting = departingSheet.createRow(0);
        rowDeparting.setHeightInPoints(28);

        setHat(DEPARTING,rowDeparting,styleHat);

        for (int i = 0; i < departingList.size(); i++) {// ((( could be better
            rowDeparting = departingSheet.createRow(counter);
            rowDeparting.setHeightInPoints(28);
            Cell cell2 = rowDeparting.createCell(0);
            cell2.setCellStyle(styleContains);
            cell2.setCellValue(departingList.get(i).getNumberFlight());
            Cell cell3 = rowDeparting.createCell(1);
            cell3.setCellStyle(styleContains);
            cell3.setCellValue(departingList.get(i).getDirectionFlight());
            Cell cell4 = rowDeparting.createCell(4);
            cell4.setCellStyle(styleCenterCell);
            cell4.setCellValue(String.valueOf(PATTERN.format(departingList.get(i).getTimeFlight().getTime())));
            counter++;
        }

        FileOutputStream fos = new FileOutputStream(PATH);
        book.write(fos);
        fos.close();
    }

    private static  void setHat(String [] arrivalOrDeparting, Row row, CellStyle style){
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

    private static void makeBorders(CellStyle style){
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }

}