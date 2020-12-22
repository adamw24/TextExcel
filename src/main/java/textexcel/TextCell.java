package textexcel;

public class TextCell implements Cell {
	private String value = "";

	public TextCell(String s) {
		value = s.substring(1,s.length()-1);
	}

	public String abbreviatedCellText() {
		return String.format("%-10.10s", value);
	}

	public String fullCellText() {
		return "\"" + value + "\"";
	}
	
}
