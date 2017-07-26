package sample.model.dao;

import sample.model.Dictionary.Word;

import java.util.List;

/**
 * Created by VAUst on 31.10.2016.
 */
public interface WordDAO {
    public int exist (String word);
    public int create (Word word);
    public List<Word> readAll ();
    public boolean update (Word word);
    public boolean delete (Word word);
}
