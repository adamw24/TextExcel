package textexcel;

public class ValueCell extends RealCell {
	public ValueCell(String s) {
		realValue = Double.parseDouble(s);
		stringValue = s;

	}

	public String fullCellText() {
		return stringValue;
	}

	public String abbreviatedCellText() {
		String temp = stringValue;
		if (stringValue.indexOf('.') == -1) {
			temp = stringValue + ".0";
		} else {
			while (temp.charAt(temp.length() - 1) == '0') {
				temp = temp.substring(0, temp.length() - 1);
			}
			if (temp.charAt(temp.length() - 1) == '.') {
				temp = temp + "0";
			}
		}
		return String.format("%-10.10s", temp);
	}
}
