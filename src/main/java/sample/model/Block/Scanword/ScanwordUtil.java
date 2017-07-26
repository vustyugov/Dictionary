package sample.model.Block.Scanword;

import sample.model.Block.Scanword.api.Scanword;
import sample.model.Cell.api.Cell;
import sample.model.Cell.impl.*;
import java.util.*;

/**
 * Created by VAUst on 31.10.2016.
 */
public class ScanwordUtil {

    public static List<String[]> getWords (Scanword scanword) {
        List<String[]> list = new LinkedList<String[]>();
        List<String> wordsList = new LinkedList<String>(getHorizontalWordsList(scanword.getArray()));
        wordsList.addAll(getVerticalWordsList(scanword.getArray()));
        for (String word: wordsList) {
            String[] line = new String[2];
            line[0] = scanword.getName();
            line[1] = word;
            list.add(line);
        }
        return list;
    }

    private static List<String> getHorizontalWordsList (Cell[][] array) {
        StringBuffer buf = new StringBuffer("");
        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                if (array[row][column] instanceof ActiveCell || array[row][column] instanceof SimpleCell) {
                    buf.append(array[row][column].getLetter());
                }
                else {
                    buf.append(" ");
                }
            }
            buf.append(" ");
        }
        return parseLine(buf.toString());
    }

    private  static List<String> getVerticalWordsList (Cell[][] array) {
        StringBuffer buf = new StringBuffer("");
        for (int column = 0; column < array[0].length; column++) {
            for (int row = 0; row < array.length; row++) {
                if (array[row][column] instanceof SimpleCell || array[row][column] instanceof ActiveCell) {
                    buf.append(array[row][column].getLetter());
                }
                else {
                    buf.append(" ");
                }
            }
            buf.append(" ");
        }
        return parseLine(buf.toString());
    }

    private static List<String> parseLine (String line) {
        List<String> list = new ArrayList<String>(Arrays.asList(line.replaceAll("\\s+"," ").split("\\s")));
        Iterator<String> listIterator = list.iterator();
        while (listIterator.hasNext()) {
            String word = listIterator.next();
            if (word.length() < 2) {
                listIterator.remove();
            }
        }
        return list;
    }

}
