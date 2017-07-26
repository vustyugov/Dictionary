package sample.model.dao;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import sample.model.Block.Scanword.Block;
import sample.model.Block.Scanword.api.Scanword;
import sample.model.Block.Scanword.impl.SquaredScanword;
import sample.model.Cell.api.*;
import sample.model.Cell.impl.*;

import java.io.*;
import java.util.*;

/**
 * Created by VAUst on 01.11.2016.
 */
public class XLSXFileScanwordDao implements ScanwordDAO {
    @Override
    public int create(Scanword scanword) {
        return 0;
    }

    private void copyArrays(Object[][] largeArray, Object[][] smallArray, int rowCounts, int columnCounts) {
        for (int row = 0; row < rowCounts; row++) {
            for (int column = 0; column < columnCounts; column++) {
                smallArray[row][column] = largeArray[row][column];
            }
        }
        largeArray = smallArray;
    }

    private boolean equal(byte[] first, byte[] second) {
        boolean flag = true;
        for(int i = 0; i < 3; i++) {
            if(first[i] != second[i]) {
                flag = false;
            }
        }return flag;
    }


    private int[] getPosition (XSSFSheet sheet, byte[] whiteColorByte, byte[] blackColorByte) {
        int[] position = new int[2];
        for (int rowIndex = 0; rowIndex < 15; rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            for (int columnIndex = 0; columnIndex < 15; columnIndex++) {
                XSSFCell cell = row.getCell(columnIndex);
                if(cell!= null) {
                    XSSFColor color = cell.getCellStyle().getFillBackgroundXSSFColor();
                    if (color != null) {
                        position[0] = rowIndex;
                        position[1] = columnIndex;
                        return position;
                    }
                }
            }
        }
        return position;
    }

    private int[] getSize (XSSFSheet sheet, int[] position, byte[] whiteColorByte, byte[] blackColorByte) {
        int[] size = new int[2];
        int rSize = 0;
        int cSize = 0;
        for (int rowIndex = position[0]; rowIndex < 40; rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                cSize = 0;
                for (int columnIndex = position[1]; columnIndex < 50; columnIndex++) {
                    XSSFCell cell = row.getCell(columnIndex);
                    if(cell!= null) {
                        XSSFColor color = cell.getCellStyle().getFillBackgroundXSSFColor();
                        if (color != null) {
                            cSize++;
                            rSize = rowIndex;
                        }
                    }
                }
            }
            else {
                size[0] = rSize;
                size[1] = cSize;
            }
        }
        return size;

    }

    private Cell[][] getArray(XSSFSheet sheet) {
        XSSFColor whiteColor = new XSSFColor();
        XSSFColor blackColor = new XSSFColor();
        XSSFColor greyColor = new XSSFColor();
        whiteColor.setIndexed(IndexedColors.WHITE.getIndex());
        blackColor.setIndexed(IndexedColors.BLACK.getIndex());
        greyColor.setIndexed(IndexedColors.GREY_25_PERCENT.getIndex());

        byte[] whiteColorByte = whiteColor.getRGB();
        byte[] blackColorByte = blackColor.getRGB();
        byte[] greyColorByte = greyColor.getRGB();

        int[] position = getPosition(sheet, whiteColorByte, blackColorByte);
        int[] size = getSize(sheet, position, whiteColorByte, blackColorByte);
        int counter = 0;
        Cell[][] array = new Cell[size[0]][size[1]];

        for(int rowIndex = position[0]; rowIndex < size[0]+position[0]; rowIndex++) {
            StringBuffer line = new StringBuffer();
            XSSFRow row = sheet.getRow(rowIndex);
            for (int cellIndex = position[1]; cellIndex < size[1]+position[1]; cellIndex++) {
                Cell myCell = null;
                XSSFCell cell = row.getCell(cellIndex);
                if (cell != null) {
                    XSSFColor color = cell.getCellStyle().getFillForegroundXSSFColor();
                    if (color != null) {
                        byte[] colorByte = color.getRGB();
                        if (equal(colorByte, greyColorByte)) {
                            myCell = new DisableCell();
                        } else if (equal(colorByte, blackColorByte)) {
                            myCell = new CommentCell();
                        } else if (equal(colorByte, whiteColorByte)) {
                            myCell = new SimpleCell();
                        }
                        myCell.setLetter(cell.getStringCellValue());
                        array[rowIndex-position[0]][cellIndex-position[1]] = myCell;
                    }
                }
            }
        }
        return array;
    }

    @Override
    public List<Scanword> readAll(String path) {
        try{
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(path));
            int sheetsCount = book.getNumberOfSheets();
            List<Scanword> scanwordList = new LinkedList<Scanword>();
            Scanword scanword = null;
            String[][] array = null;
            for (int index = 0; index < sheetsCount; index++) {
                XSSFSheet sheet = book.getSheetAt(index);
                scanword = new SquaredScanword(sheet.getSheetName());
                scanword.setArray(getArray(sheet));
                scanwordList.add(scanword);
            }
            return scanwordList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Scanword scanword) {
        return false;
    }

    @Override
    public boolean delete(Scanword scanword) {
        return false;
    }
}
