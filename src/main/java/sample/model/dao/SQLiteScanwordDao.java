package sample.model.dao;

import sample.model.Block.Scanword.api.Scanword;

import java.util.List;

/**
 * Created by VAUst on 31.10.2016.
 */
public class SQLiteScanwordDao implements ScanwordDAO {
    @Override
    public int create(Scanword scanword) {
        return 0;
    }

    @Override
    public List<Scanword> readAll(String path) {
        return null;
    }

    @Override
    public boolean update(Scanword scanword) {
        return false;
    }

    @Override
    public boolean delete(Scanword scanword) {
        return false;
    }
}
