package sample.controller;

import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import sample.Model;
import sample.model.Block.Scanword.Block;
import sample.model.Block.Scanword.ScanwordUtil;
import sample.model.Block.Scanword.api.Scanword;
import sample.model.Dictionary.Category;
import sample.model.dao.DAOFactory;
import sample.model.dao.ScanwordDAO;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by VAUst on 29.10.2016.
 */
public class MainWindowController {
    private Model model;
    private Block block;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField textField;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ListView listView;

    public MainWindowController () {
    }

    public MainWindowController (Model model) {
        this.model = model;
    }

    @FXML
    private void initialize() {
        List<String> list = null;
        int index = 0;
        if (model.getDictionary() == null | model.getDictionary().getCategories() == null) {
            list = new ArrayList<String>(1);
            list.add(index, "все");
        }
        else {
            list = new ArrayList<String>(model.getDictionary().getCategories().size()+1);
            list.add(index, "все");
            for (Category category:model.getDictionary().getCategories()) {
                list.add(++index, category.getValue());
            }
        }
        ObservableList<String> oList = FXCollections.observableArrayList();
        oList.addAll(list);
        choiceBox.setItems(oList);
    }

    @FXML
    private void handleExit() {
        model.saveDictionary();
        model.getPrimaryStage().close();
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
        String path = fileChooser.showOpenDialog(model.getPrimaryStage()).getPath();
        model.setPathScanwordFiles(path);
        openFile(path);
    }

    private void openFile (String path) {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.FILE);
        ScanwordDAO scanwordDAO = daoFactory.getScanwordDAO();
        model.setBlock(new Block(scanwordDAO.readAll(path)));
        List<String[]> list = new LinkedList<String[]>();
        for (Scanword scanword: model.getBlock().getScanwords()) {
            list.addAll(ScanwordUtil.getWords(scanword));
        }
        model.getBlock().setWordsList(list);
    }

    @FXML
    private void handleClose() {}

    @FXML
    private void handleAbout() {}

    @FXML
    private void handleEditWord() {
        model.showInfoWordWindow(null);
    }

    @FXML
    private void handleEditCategory() {
        model.showInfoCategoryWindow(null);
    }

    @FXML
    private void handleGenerateWord() {
        openFile(model.getPathScanwowrdFiles());
        model.showWordsListWindow();
    }

    @FXML
    private void handlePressEnterKey() {
        final KeyCodeCombination updateKey = new KeyCodeCombination(KeyCode.F5);
        final KeyCombination enterKey = new KeyCodeCombination(KeyCode.ENTER);
        final KeyCombination backspaceKey = new KeyCodeCombination(KeyCode.BACK_SPACE);
        final KeyCodeCombination deleteKey = new KeyCodeCombination(KeyCode.DELETE);
        final KeyCodeCombination deleteAllKey = new KeyCodeCombination(KeyCode.DELETE, KeyCombination.CONTROL_DOWN);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (enterKey.match(event)) {
                    ObservableList<String> list = model.getObservableListForListView(textField.getText().toUpperCase(), choiceBox.getValue());
                    if (list != null) {
                        listView.setItems(list);
                    }
                }
                if (backspaceKey.match(event)) {
                }
                if (deleteKey.match(event)) {
                }
                if (deleteAllKey.match(event)) {
                }
            }
        });
    }
}
