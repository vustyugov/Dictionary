package sample.model.dao;

import sample.model.Dictionary.Category;
import sample.model.Dictionary.Dictionary;

import java.sql.Connection;

/**
 * Created by VAUst on 31.10.2016.
 */
abstract public class DAOFactory {
    public static final int SQLITE = 0;
    public static final int FILE = 1;

    public abstract WordDAO getWordDAO();
    public abstract CategoryDAO getCategoryDAO();
    public abstract ScanwordDAO getScanwordDAO();

    public static DAOFactory getDAOFactory (int whichFactory) {
        switch (whichFactory) {
            case SQLITE:
                return new SQLiteDAOFactory();
            case FILE:
                return new FileDAOFactory();
            default:
                return null;
        }
    }
}
