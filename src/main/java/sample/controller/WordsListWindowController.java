package sample.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model;
import sample.model.View;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by VAUst on 02.11.2016.
 */
public class WordsListWindowController {
    private Model model;
    private final ObservableList<String[]> data = FXCollections.observableArrayList();
    @FXML
    private TableView<View> wordsTable;
    @FXML
    private TableColumn<View, String> wordColumn;
    @FXML
    private TableColumn<View, String> storageColumn;
    @FXML
    private TableColumn<View, String> newColumn;
    @FXML
    private TableColumn<View, String> repeateColumn;
    @FXML
    private TableColumn<View, String> incorrectColumn;

    public WordsListWindowController() {

    }

    public WordsListWindowController(Model model) {
        this.model = model;
    }

    private ObservableList<View> initData() {
        ObservableList<View> data = FXCollections.observableArrayList();
        List<String[]> scanList = model.getBlock().getWordsList();
        List<String[]> resultList = new LinkedList<String[]>();
        List<String> catList = new LinkedList<String>();
        catList.add(model.getDictionary().INCORRECT_CATEGORY);
        List<String> list = new LinkedList<String>();
        View view = null;

        for (String[] line: scanList) {
            view = new View("","","","","");
            view.setWord(line[1]);
            view.setStorage(line[0]);
            if (!model.getDictionary().containWord(line[1])) {
                view.setNewWord("+");
            } else {
                view.setNewWord("-");
            }

            if (model.getBlock().findRepeatedWords(scanList, line[1]).size() > 1) {
                view.setRepeate("+");
            } else {
                view.setRepeate("-");
            }

            if (model.getDictionary().getWords(line[1], catList).size() > 0) {
                view.setIncorrect("+");
            } else {
                view.setIncorrect("-");
            }
            data.add(view);
        }
        return data;
    }

    @FXML
    private void initialize () {
        if (model==null) {
            return;
        }
        ObservableList<View> data = initData();

        wordColumn.setCellValueFactory(new PropertyValueFactory<View, String>("word"));
        storageColumn.setCellValueFactory(new PropertyValueFactory<View, String>("storage"));
        newColumn.setCellValueFactory(new PropertyValueFactory<View, String>("newWord"));
        repeateColumn.setCellValueFactory(new PropertyValueFactory<View, String>("repeate"));
        incorrectColumn.setCellValueFactory(new PropertyValueFactory<View, String>("incorrect"));

        wordsTable.setItems(data);
    }
}
