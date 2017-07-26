package sample.model.Block.Scanword;

import sample.model.Block.Scanword.api.Scanword;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by VAUst on 31.10.2016.
 */
public class Block {
    private List<Scanword> scanwords;
    private List<String[]> wordsList;

    public Block(List<Scanword> list) {
        scanwords = list;
    }

    public List<Scanword> getScanwords () {
        return scanwords;
    }

    public void setWordsList(List<String[]> list) {
        wordsList = list;
    }

    public List<String[]> getWordsList() {
        return wordsList;
    }

    private String convertTemplate(String template) {
        String word = template.toUpperCase().trim();
        StringBuilder buf = new StringBuilder();
        for(int index = 0; index < word.length(); index++) {
            if(template.charAt(index) == '?') {
                buf.append('.');
            }
            else {
                buf.append(word.charAt(index));
            }
        }
        return buf.toString();
    }

    public List<String[]> findWords (String template) {
        List<String[]> list = new LinkedList<String[]>();
        Pattern pattern = Pattern.compile(convertTemplate(template));
        for (String[] element: wordsList) {
            Matcher matcher = pattern.matcher(element[1]);
            if (matcher.matches()) {
                list.add(element);
            }

        }
        return  list;
    }

    public List<String[]> findRepeatedWords(List<String[]> list, String template) {
        List<String[]> result = new LinkedList<String[]> ();
        if (template == null) {
            return null;
        }
        String word = template;
        for (String[] line: list) {
            if(word.equals(line[1])) {
                result.add(line);
            }
        }
        return result;
    }
}