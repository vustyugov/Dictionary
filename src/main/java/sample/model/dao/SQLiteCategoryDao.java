package sample.model.dao;

import sample.model.Dictionary.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by VAUst on 31.10.2016.
 */
public class SQLiteCategoryDao implements CategoryDAO {
    public void createConnection() {
        try {
            SQLiteDAOFactory.createConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int exist(String categoryValue) {
        return -1;
    }

    @Override
    public int create(Category category) {

        return 0;
    }

    @Override
    public List<Category> readAll() {
        Statement st = null;
        ResultSet rs = null;
        Category category = null;
        List<Category> list = new LinkedList<Category>();
        try {
            st = SQLiteDAOFactory.conn.createStatement();
            rs = st.executeQuery("SELECT * FROM categories;");
            while (rs.next()) {
                category = new Category(rs.getString("value"));
                category.setDescription(rs.getString("description"));
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean update(Category category) {
        return false;
    }

    @Override
    public boolean delete(Category category) {
        return false;
    }

    public void closeConnection() {
        try {
            SQLiteDAOFactory.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
