package sample.model.dao;

import sample.model.Dictionary.Category;

import java.sql.Connection;
import java.util.List;

/**
 * Created by VAUst on 31.10.2016.
 */
public interface CategoryDAO {
    public void createConnection();
    public int exist(String category);
    public int create(Category category);
    public List<Category> readAll();
    public boolean update(Category category);
    public boolean delete(Category category);
    public void closeConnection();
}
