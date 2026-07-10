package api.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility {

    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;

    public XLUtility(String path){
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowcount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return  rowcount;
    }

    public int getCellCount(String sheetName, int rownum) throws IOException{
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellCount = row.getLastCellNum();
        workbook.close();
        fi.close();
        return cellCount;

    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException{
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        DataFormatter formatter = new DataFormatter();

        String data;
        try{
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";//to handle null data of cell
        }

        //we can also use data = cell.toString();
        workbook.close();
        fi.close();
        return data;


    }

    public void setCellData(String xlfile, String xlsheet, int rownum, int colnum, String data) throws IOException {
        fi = new FileInputStream(xlfile);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlsheet);
        row = sheet.getRow(rownum);
        cell = row.createCell(colnum);
        cell.setCellValue(data);
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
//        cell = row.createCell(colnum);
//        cell.setCellValue(data);
//        fo = new FileOutputStream(xlfile);
//        workbook.write(fo);
//        workbook.close();
//        fi.close();
//        fo.close();
    }

    public void fillGreenColour(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(xlfile);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlsheet);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        fo = new FileOutputStream(xlfile);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();

    }

    public void fillRedColour(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(xlfile);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlsheet);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        fo = new FileOutputStream(xlfile);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();

    }


    //DataProviders for Data Driven test
    //@Dataproviders provide the data of a sheet to method
    //stores data in 2d array for parsing


}
