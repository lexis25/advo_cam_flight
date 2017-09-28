import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class FlightScheduleExcell {

    private static final String[] arrival = {"№ Рейса", "Прилет", "Ст", "Время прилета"};
    private static final String[] departing = {"№ Рейса", "Вылет", "Ст", "Время регистрации", "Время вылета", "П", "Б"};

    public static void main(String[] args) throws IOException {
        create();
    }

    public static void create() throws IOException {


        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("прилет");
        Sheet sheet1 = book.createSheet("вылет");

        CellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);


        Font font = book.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        style.setFont(font);


        Row row = sheet.createRow(0);

        for (int i = 0; i < arrival.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(arrival[i]);

        }



        FileOutputStream fos = new FileOutputStream("H:/FOLDER/123.xls");
        book.write(fos);
        fos.close();
    }

}
