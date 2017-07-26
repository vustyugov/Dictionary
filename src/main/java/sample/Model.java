package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import sample.controller.*;
import sample.model.Block.Scanword.Block;
import sample.model.Dictionary.*;
import sample.model.View;
import sample.model.dao.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by VAUst on 30.10.2016.
 */
public class Model {
    private String pathToScanwordFile;
    private Dictionary dictionary;
    private Stage primaryStage;
    private Stage dialogStage;
    private Block block;

    public  Model () {
        loadDictionary();
    }

    public void setPathScanwordFiles (String path) {
        pathToScanwordFile = path;
    }

    public String getPathScanwowrdFiles() {
        return pathToScanwordFile;
    }

    private void loadDictionary () {
        dictionary = Dictionary.getInstance();
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
        CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
        WordDAO wordDAO = daoFactory.getWordDAO();
        categoryDAO.createConnection();
        dictionary.addCategoriesInDictionary(categoryDAO.readAll());
        dictionary.addWordsInDictionary(wordDAO.readAll());
        categoryDAO.closeConnection();
    }

    public void updateWordsInDictionary(List<Word> words) {
        if (words.size() > 0) {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            WordDAO wordDAO = daoFactory.getWordDAO();
            categoryDAO.createConnection();
            for (Word word: words) {
                Word dWord = dictionary.getWord(word.getValue());
                wordDAO.update(dWord);
            }
            categoryDAO.closeConnection();
        }
    }

    public void insertWordsInDictionary(List<Word> words) {
        if (words.size() > 0) {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            WordDAO wordDao = daoFactory.getWordDAO();
            categoryDAO.createConnection();
            for (Word word: words) {
                if (wordDao.exist(word.getValue()) == -1) {
                   wordDao.create(word);
                }
            }
            categoryDAO.closeConnection();
        }
    }

    public void updateCategoiesInDictionary(List<Category> categories) {
        if (categories.size() > 0) {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            categoryDAO.createConnection();
            for (Category category: categories) {
                categoryDAO.update(dictionary.getCategory(category.getValue()));
            }
            categoryDAO.closeConnection();
        }
    }

    public void saveDictionary (){
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
        CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
        WordDAO wordDAO = daoFactory.getWordDAO();
        categoryDAO.createConnection();
        for (Word word: dictionary.getWords()) {
            if (wordDAO.exist(word.getValue()) != -1) {
                wordDAO.update(word);
            }
        }
        for (Category category: dictionary.getCategories()) {
            if(categoryDAO.exist(category.getValue()) != -1) {
                categoryDAO.update(category);
            }
        }
        categoryDAO.closeConnection();
    }

    public Dictionary getDictionary () {
        return dictionary;
    }

    public void setPrimaryStage (Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public void showMainWindow () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainWindow.fxml"));
            MainWindowController controller = new MainWindowController(this);
            loader.setController(controller);
            Parent root = (Parent) loader.load();

            this.primaryStage.setTitle("Dictionary");
            this.primaryStage.setScene(new Scene(root, 300, 600));
            this.primaryStage.setResizable(false);
            this.primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showInfoWordWindow (Word word) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/infoWordWindow.fxml"));
            InfoWordWindowController controller = new InfoWordWindowController(this);
            loader.setController(controller);
            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("Word information");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showInfoCategoryWindow (Category category) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/infoCategoryWindow.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("Category information");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            InfoCategoryWindowController controller = loader.getController();
            controller.setModel(this);
            controller.setCategory(category);
            dialogStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWordsListWindow () {
        try {
            if (dialogStage != null && dialogStage.isShowing()) {
                dialogStage.close();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/wordsListWindow.fxml"));
            WordsListWindowController controller = new WordsListWindowController(this);
            loader.setController(controller);
            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("Words List");
            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getObservableListForListView(String template, String categoryName) {
        List<String[]> scanList = new LinkedList<String[]>();
        List<String[]> repeatedScanList = new LinkedList<String[]>();
        List<String> catList = new LinkedList<String>();
        List<String> dicList = new LinkedList<String>();
        List<String> totalList = new LinkedList<String> ();

        scanList.addAll(block.findWords(template));
        for (Category category: dictionary.getCategories()) {
            catList.add(category.getValue());
        }
        if (categoryName.contains(dictionary.ALL_CATEGORIES)) {
//            dicList.addAll(dictionary.getWords(template, catList));
            dicList.addAll(dictionary.getWords(template));
        }
        else {
            List<String> innerList = new LinkedList<String>();
            innerList.add(categoryName);
            dicList.addAll(dictionary.getWords(template,innerList));
        }
        StringBuilder buf = null;
        for (String[] word: scanList) {
            buf = new StringBuilder();
            repeatedScanList = block.findRepeatedWords(scanList, word[1]);
            if (repeatedScanList.size()>1) {
                List<String> tmpList = new LinkedList<String>();
                for(String[] iWord: repeatedScanList) {
                    buf = new StringBuilder();
                    buf.append(iWord[1]);
                    buf.append("\t\t\t");
                    buf.append(word[0]);
                    buf.append("\t\t\t");
                    if (dictionary.getWord(word[1]).containsCategory(dictionary.INCORRECT_CATEGORY)) {
                        buf.append("i,r,s");
                    }
                    else {
                        buf.append("r,s");
                    }
                    tmpList.add(buf.toString());
                }
                if (!totalList.containsAll(tmpList)) {
                    totalList.add(buf.toString());
                }
            }
            else if (dicList.contains(word[1])) {
                buf.append(word[1]);
                buf.append("\t\t\t");
                buf.append(word[0]);
                buf.append("\t\t\t");
                if (dictionary.getWord(word[1]).containsCategory(dictionary.INCORRECT_CATEGORY)) {
                    buf.append("i,s");
                }
                else {
                    buf.append("s");
                }
            }
            else if (!dicList.contains(word[1])){
                buf.append(word[1]);
                buf.append("\t\t\t");
                buf.append(word[0]);
                buf.append("\t\t\t");
                buf.append("n");
            }
            if (!totalList.contains(buf.toString())) {
                totalList.add(buf.toString());
            }
        }

        for (String word: dicList) {
            buf = new StringBuilder();
            boolean flag = false;
            for (String[] sWord: scanList) {
                if(word.trim().equals(sWord[1].trim())) {
                    flag = true;
                }
            }
            if (!flag) {
                buf.append(word);
                buf.append("\t\t\t\t\t\t");
                if (dictionary.getWord(word).containsCategory(dictionary.INCORRECT_CATEGORY)) {
                    buf.append("i,d");
                }
                else {
                    buf.append("d");
                }
            }
            totalList.add(buf.toString());
        }
        Iterator<String> iter = totalList.iterator();
        while (iter.hasNext()) {
            String line = iter.next();
            if (line.length() <2) {
                iter.remove();
            }
        }
        return FXCollections.observableArrayList(totalList);
    }
}