package sample.model.Cell.impl;

import sample.model.Cell.api.Cell;
import java.util.regex.*;

public class TotalCell implements Cell {

	protected String firstLink = "";
	protected String secondLink = "";
	protected String regex = "[\\u0410-\\u044F\\u0401\\0451]";
	protected String letter;
	
	public TotalCell() {
		letter = "";
	}
	
	public TotalCell(String letter) {
		this();
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(letter);
		if(m.matches()) {
			this.letter = letter;	
		}
	}
	
	@Override
	public boolean setLetter(String letter) {
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(letter);
		if(m.matches()) {
			this.letter = letter;
			return true;
		}
		return false;
	}
	
	@Override
	public String getLetter() {
		return letter;
	}
}