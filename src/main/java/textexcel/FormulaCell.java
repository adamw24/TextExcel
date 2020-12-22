package textexcel;

public class FormulaCell extends RealCell {
	private Spreadsheet sheet;

	public FormulaCell(String p, Spreadsheet s) {
		stringValue = p.substring(2, p.length() - 2);
		sheet = s;
	}

	public double getDoubleValue() {
		String[] parts = stringValue.split(" ");
		double result = 0.0;
		if (parts[0].toLowerCase().contentEquals("sum") || parts[0].toLowerCase().contentEquals("avg")) {
			int count = 0;
			String[] range = parts[1].split("-");
			SpreadsheetLocation start = new SpreadsheetLocation(range[0]);
			SpreadsheetLocation end = new SpreadsheetLocation(range[1]);
			int minCol = Math.min(start.getCol(), end.getCol());
			int maxCol = Math.max(start.getCol(), end.getCol());
			int minRow = Math.min(start.getRow(), end.getRow());
			int maxRow = Math.max(start.getRow(), end.getRow());
			for (int r = minRow; r <= maxRow; r++) {
				for (int c = minCol; c <= maxCol; c++) {
					SpreadsheetLocation l = new SpreadsheetLocation(r, c);
					result += ((RealCell) sheet.getCell(l)).getDoubleValue();
					count++;
				}
			}
			if (parts[0].toLowerCase().contentEquals("avg")) {
				result = result / count;
			}
		} else {
			result = getCellValue(parts[0]);
			for (int i = 1; i < parts.length; i += 2) {
				char s = parts[i].charAt(0);
				double v2 = getCellValue(parts[i + 1]);
				if (s == '+') {
					result += v2;
				} else if (s == '-') {
					result -= v2;
				} else if (s == '*') {
					result *= v2;
				} else if (s == '/') {
					result /= v2;
				}
			}
		}
		return result;
	}

	public double getCellValue(String s) {
		char f = s.charAt(0);

		double v2 = 0.0;
		if ((f >= 'A' && f <= 'Z') || (f >= 'a' && f <= 'z')) {
			SpreadsheetLocation loc = new SpreadsheetLocation(s);
			RealCell c = (RealCell) sheet.getCell(loc);
			v2 = c.getDoubleValue();
		} else {
			v2 = Double.parseDouble(s);
		}
		return v2;
	}

	public String abbreviatedCellText() {
		return String.format("%-10.10s", getDoubleValue());
	}

	public String fullCellText() {
		return "( " + stringValue + " )";
	}

}
