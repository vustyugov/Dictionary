package sample.model.Block.Scanword.impl;

import sample.model.Block.Scanword.api.Scanword;
import sample.model.Cell.api.Cell;

public class SquaredScanword implements Scanword {
	private String name;
	private int[] size;
	private Cell[][] array;

	public SquaredScanword(String name) {
		this.name = name;
		this.size = new int[2];
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int[] getSize() {
		return size;
	}

	@Override
	public void setArray(Cell[][] array) {
		this.array = array;
		size[0] = array.length;
		size[1] = array[0].length;
	}

	@Override
	public Cell[][] getArray() {
		return array;
	}

	@Override
	public boolean setCell(Cell cell, int row, int column) {
		if(array != null){
			if ((row >= 0 && row < size[0])
					&& (column >= 0 && column < size[1])) {
				if(array[row][column] == null) {
					array[row][column] = cell;
				} else {
					if(!array[row][column].equals(cell)) {
						array[row][column] = cell;
					}
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Cell getCell(int row, int column) {
		if(array != null){
			if ((row >= 0 && row < size[0])
					&& (column >= 0 && column < size[1])) {
						if(array[row][column] == null) {
							return null;
						}
						else {
							return array[row][column];
						}
			}
		}
		return null;
	}
}