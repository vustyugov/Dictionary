package sample.model.dao;

import sample.model.Dictionary.Category;

import java.sql.*;

/**
 * Created by VAUst on 31.10.2016.
 */
public class SQLiteDAOFactory extends DAOFactory{

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String PATH = "jdbc:sqlite:src/main/resources/sedb.s3db";
    public static Connection conn;
    public static Statement st;
    public static ResultSet rs;

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(PATH);
        return conn;
    }

    @Override
    public WordDAO getWordDAO() {
        return new SQLiteWordDao();
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new SQLiteCategoryDao();
    }

    @Override
    public ScanwordDAO getScanwordDAO() {
        return null;
    }

    public static void closeConnection () throws SQLException {
        conn.close();
    }
}