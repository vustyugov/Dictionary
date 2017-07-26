package sample.model.dao;

import sample.model.Dictionary.Category;

/**
 * Created by VAUst on 31.10.2016.
 */
public class FileDAOFactory extends DAOFactory {
    @Override
    public WordDAO getWordDAO() {
        return null;
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return null;
    }

    @Override
    public ScanwordDAO getScanwordDAO() {
        return new XLSXFileScanwordDao();
    }
}
