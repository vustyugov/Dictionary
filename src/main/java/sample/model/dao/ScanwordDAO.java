package sample.model.dao;

import sample.model.Block.Scanword.api.Scanword;
import java.util.List;

/**
 * Created by VAUst on 31.10.2016.
 */
public interface ScanwordDAO {
    public int create(Scanword scanword);
    public List<Scanword> readAll(String path);
    public boolean update(Scanword scanword);
    public boolean delete(Scanword scanword);
}
