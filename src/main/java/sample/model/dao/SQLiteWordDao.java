package sample.model.dao;

import sample.model.Dictionary.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by VAUst on 31.10.2016.
 */
public class SQLiteWordDao implements WordDAO {
    private String insertWord = "insert into words (value, description) values (?,?)";
    private String insertLinkWC = "insert into link_wc values (?,?)";
    private String deleteLinkWC = "delete from link_wc where word_id = ?";
    private String updateWord = "update words set description = ? where value = ?";

    private String readWordId = "select id from words where value = ?";
    private String readCategoryId = "select id from categories where value = ?";

    @Override
    public int exist (String word) {
        int id = -1;
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = SQLiteDAOFactory.conn.prepareStatement(readWordId);
            pst.setString(1, word);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int create(Word word) {
        try {
            ResultSet rs = null;
            int wordId = -1;
            List<Integer> categoriesId = new LinkedList<Integer>();
            PreparedStatement pstmt = SQLiteDAOFactory.conn.prepareStatement(insertWord);
            pstmt.setString(1, word.getValue());
            pstmt.setString(2, word.getDescription());
            pstmt.executeUpdate();

            pstmt = SQLiteDAOFactory.conn.prepareStatement(readWordId);
            pstmt.setString(1, word.getValue());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wordId = rs.getInt("id");
            }

            pstmt = SQLiteDAOFactory.conn.prepareStatement(readCategoryId);
            for (Category category: word.getCategories()) {
                pstmt.setString(1, category.getValue());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    categoriesId.add(rs.getInt("id"));
                }
            }

            pstmt = SQLiteDAOFactory.conn.prepareStatement(insertLinkWC);
            for (Integer categoryId: categoriesId) {
                pstmt.setInt(1, wordId);
                pstmt.setInt(2, categoryId.intValue());
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<Category> getCategories(int wordId) {
        Statement st = null;
        ResultSet rs = null;
        Category category = null;
        List<Category> list = new LinkedList<Category>();
        StringBuffer buf = new StringBuffer("select value, description ");
        buf.append("from categories, link_wc ");
        buf.append("where id = category_id and word_id = ");
        buf.append(wordId);
        buf.append(";");
        try {
            st = SQLiteDAOFactory.conn.createStatement();
            rs = st.executeQuery(buf.toString());
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
    public List<Word> readAll() {
        Statement st = null;
        ResultSet rs = null;
        Word word = null;
        List<Word> list = new LinkedList<Word>();
        try {
            st = SQLiteDAOFactory.conn.createStatement();
            rs = st.executeQuery("SELECT * from words");
            while (rs.next()) {
                word = new Word(rs.getString("value"));
                word.setDescription(rs.getString("description"));
                word.setCategories(getCategories(rs.getInt("id")));
                list.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean update(Word word) {
        int wordId = -1;
        try {
            ResultSet rs = null;
            List<Integer> categoriesId = new LinkedList<Integer>();
            PreparedStatement pstmt = SQLiteDAOFactory.conn.prepareStatement(updateWord);
            pstmt.setString(1, word.getDescription());
            pstmt.setString(2, word.getValue());
            pstmt.executeUpdate();

            pstmt = SQLiteDAOFactory.conn.prepareStatement(readWordId);
            pstmt.setString(1, word.getValue());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wordId = rs.getInt("id");
            }

            pstmt = SQLiteDAOFactory.conn.prepareStatement(readCategoryId);
            for (Category category: word.getCategories()) {
                pstmt.setString(1, category.getValue());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    categoriesId.add(rs.getInt("id"));
                }
            }

            pstmt = SQLiteDAOFactory.conn.prepareStatement(deleteLinkWC);
            pstmt.setInt(1, wordId);
            pstmt.executeUpdate();

            for (Integer categoryId: categoriesId) {
                pstmt = SQLiteDAOFactory.conn.prepareStatement(insertLinkWC);
                pstmt.setInt(1, wordId);
                pstmt.setInt(2, categoryId.intValue());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Word word) {
        return false;
    }
}