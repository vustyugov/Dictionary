package sample.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import sample.*;
import sample.model.Dictionary.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by VAUst on 29.10.2016.
 */
public class InfoWordWindowController {
    private Model model;
    private Word word;
    private int wordId;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;

    public InfoWordWindowController() {
    }

    public InfoWordWindowController(Model model) {
        this.model = model;
    }

    public void setWord (Word word) {
        this.word = word;
    }

    @FXML
    private void initialize() {
        if (model.getDictionary().getCategories()!= null & model.getDictionary().getCategories().size()>0) {
            List<Category> cath = new LinkedList<Category>(model.getDictionary().getCategories());
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            Insets ins = new Insets(10, 10, 10, 10);
            vBox.setPadding(ins);
            for (int index = 0; index < cath.size(); index++) {
                HBox hBox = new HBox();
                hBox.setSpacing(15);
                CheckBox cBox = new CheckBox();
                Label label = new Label(cath.get(index).getValue());
                hBox.getChildren().add(cBox);
                hBox.getChildren().add(label);
                vBox.getChildren().add(hBox);
            }
            scrollPane.setContent(vBox);
        }
        selectedCategories(word);
        if (word != null) {
            textField.setText(word.getValue());
            textArea.setText(word.getDescription());
        }
    }

    private void changeCategories(Word word) {
        if (scrollPane.getContent()!= null) {
            VBox box = (VBox)scrollPane.getContent();
            for (Node node: box.getChildren()) {
                Label label = (Label) ((HBox)node).getChildren().get(1);
                CheckBox chBox = (CheckBox) ((HBox) node).getChildren().get(0);
                if (!word.containsCategory(label.getText()) && chBox.isSelected()) {
                    word.addCategory(model.getDictionary().getCategory(label.getText()));
                    chBox.setSelected(true);
                }
                else if (word.containsCategory(label.getText()) && !chBox.isSelected()) {
                    word.removeCategory(model.getDictionary().getCategory(label.getText()));
                    chBox.setSelected(false);
                }
            }
        }
    }

    private void selectedCategories(Word word) {
        if (scrollPane.getContent()!= null) {
            VBox box = (VBox)scrollPane.getContent();
            for (Node node: box.getChildren()) {
                Label label = (Label) ((HBox)node).getChildren().get(1);
                CheckBox chBox = (CheckBox) ((HBox) node).getChildren().get(0);
                if (word != null) {
                    if(word.getCategories() != null) {
                        for (Category cath: word.getCategories()) {
                            if (cath != null & label.getText().equals(cath.getValue())) {
                                chBox.setSelected(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private void clearCategoriesOnScrollPane() {
        if (scrollPane.getContent()!= null) {
            VBox box = (VBox)scrollPane.getContent();
            for (Node node: box.getChildren()) {
                Label label = (Label) ((HBox)node).getChildren().get(1);
                CheckBox chBox = (CheckBox) ((HBox) node).getChildren().get(0);
                if (chBox.isSelected()) {
                    chBox.setSelected(false);
                }
            }
        }
    }

    @FXML
    private void handleSave() {
        if (!textField.getText().equals(null)) {
            String value = textField.getText();
            String description = textArea.getText();
            Word newWord = new Word(value);
            newWord.setDescription(description);
            if (model.getDictionary().containWord(newWord.getValue())) {
                newWord.setCategories(model.getDictionary().getWord(newWord.getValue()).getCategories());
            }
            changeCategories(newWord);

            if (!model.getDictionary().containWord(newWord.getValue())) {
                model.getDictionary().addWordInDictionary(newWord);
                List<Word> words = new LinkedList<Word>();
                words.add(newWord);
                model.insertWordsInDictionary(words);
            }
            else {
                model.getDictionary().addWordInDictionary(newWord);
                model.updateWordsInDictionary(model.getDictionary().getOldWords());
            }
        }
        textField.setText("");
        textArea.setText("");
        clearCategoriesOnScrollPane();
    }

    @FXML
    private void handleDelete() {
        if (model.getDictionary().containWord(textField.getText())) {
            model.getDictionary().getWords().remove(model.getDictionary().getWord(textField.getText()));
            word = null;
        }
        textArea.setText("");
        textField.setText("");
        clearCategoriesOnScrollPane();
    }

    @FXML
    private void handleClose() {
        model.getDialogStage().close();
    }

    @FXML
    private void handlePressEnterKey() {
        final KeyCombination enterKey = new KeyCodeCombination(KeyCode.ENTER);
        final KeyCombination backspaceKey = new KeyCodeCombination(KeyCode.BACK_SPACE);
        final KeyCodeCombination deleteKey = new KeyCodeCombination(KeyCode.DELETE);
        final KeyCodeCombination deleteAllKey = new KeyCodeCombination(KeyCode.DELETE, KeyCombination.CONTROL_DOWN);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (enterKey.match(event)) {
                    textField.setText(textField.getText().toUpperCase());
                    if (model.getDictionary().containWord(textField.getText())) {
                        word = model.getDictionary().getWord(textField.getText());
                        textArea.setText(word.getDescription());
                        selectedCategories(word);
                    }
                    textField.setFocusTraversable(false);
                }
                if (backspaceKey.match(event)) {
                    if (textField.getText().length() <=1) {
                        textArea.setText("");
                        clearCategoriesOnScrollPane();
                    }
                }
                if (deleteKey.match(event)) {
                    if (textField.getText().length() <= 1) {
                        textArea.setText("");
                        clearCategoriesOnScrollPane();
                    }
                }
                if (deleteAllKey.match(event)) {
                    textArea.setText("");
                    textField.setText("");
                    clearCategoriesOnScrollPane();
                }
            }
        });
    }
}