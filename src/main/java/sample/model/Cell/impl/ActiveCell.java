package sample.model.Cell.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActiveCell extends TotalCell{

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder("[AC - ");
		buf.append(" ");
		if (firstLink.equals("")) {
			buf.append("   ");
		}
		else {
			buf.append(firstLink);
		}
		buf.append(" , ");
		if (secondLink.equals("")) {
			buf.append("   ");
		}
		else {
			buf.append(secondLink);
		}
		buf.append(" ]");
		return buf.toString();
	}
}