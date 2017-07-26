package sample.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import sample.Model;
import sample.model.Dictionary.Category;

/**
 * Created by VAUst on 29.10.2016.
 */
public class InfoCategoryWindowController {
    private Model model;
    private Category category;

    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button closeButton;

    public void setModel (Model model) {
        this.model = model;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @FXML
    private void initialize() {
        if (category!=null) {
            textField.setText(category.getValue());
            textArea.setText(category.getDescription());
        }
    }

    @FXML
    private void handleClose() {
        model.getDialogStage().close();
    }

    @FXML
    private void handleSave(){
        if (category == null) {
            if (textField.getText().length() > 1) {
                category = new Category(textField.getText());
                category.setDescription(textArea.getText());
            }
            model.getDictionary().addCategoryInDictionary(category);
        }
        else {
            if (textField.getText().length() > 1) {
                category = model.getDictionary().getCategory(textField.getText());
                if (category == null) {
                    category = new Category(textField.getText());
                    category.setDescription(textArea.getText());
                    model.getDictionary().addCategoryInDictionary(category);
                }
                if (textArea.getText().length() > 1) {
                        category.setDescription(textArea.getText());
                }
            }
        }
        textArea.setText("");
        textField.setText("");
    }

    @FXML
    private void handleDelete(){
        if (textField.getText().length() > 1) {
            if (model.getDictionary().containCategory(textField.getText())) {
                model.getDictionary().getCategories().remove(model.getDictionary().getCategory(textField.getText()));
                category = null;
            }
            textArea.setText("");
            textField.setText("");
        }
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
                    textField.setText(textField.getText().toLowerCase());
                    if (model.getDictionary().containCategory(textField.getText())) {
                        category = model.getDictionary().getCategory(textField.getText());
                    }
                    if (category != null) {
                        textArea.setText(category.getDescription());
                    }
                }
                if (backspaceKey.match(event)) {
                    if (textField.getText().length() <=1) {
                        textArea.setText("");
                    }
                }
                if (deleteKey.match(event)) {
                    if (textField.getText().length() <= 1) {
                        textArea.setText("");
                    }
                }
                if (deleteAllKey.match(event)) {
                    textArea.setText("");
                    textField.setText("");
                }
            }
        });
    }
}