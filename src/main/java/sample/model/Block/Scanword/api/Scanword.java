package sample.model.Block.Scanword.api;

import sample.model.Cell.api.Cell;
import java.util.List;

public interface Scanword {
	public String getName();
	public int[] getSize();
	public void setArray(Cell[][] array);
	public Cell[][] getArray();
	public boolean setCell(Cell cell, int row, int column);
	public Cell getCell(int row, int column);
}
