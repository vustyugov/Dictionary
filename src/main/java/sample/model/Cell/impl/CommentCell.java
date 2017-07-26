package sample.model.Cell.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentCell extends TotalCell {
	private String regex = "[0-8]{1}.[0-8]{1}.[0-8]{1}";
	
	public CommentCell() {
		super();
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
		return super.firstLink;
	}

}