package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by VAUst on 03.11.2016.
 */
public class View {
    private String word;
    private String storage;
    private String newWord;
    private String repeate;
    private String incorrect;

    public View(String word, String storage, String repeate, String newWord, String incorrect) {
        this.word = word;
        this.storage = storage;
        this.repeate = repeate;
        this.newWord = newWord;
        this.incorrect = incorrect;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getRepeate() {
        return repeate;
    }

    public void setRepeate(String repeate) {
        this.repeate = repeate;
    }

    public String getNewWord() {
        return newWord;
    }

    public void setNewWord(String newWord) {
        this.newWord = newWord;
    }

    public String getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(String incorrect) {
        this.incorrect = incorrect;
    }
}
