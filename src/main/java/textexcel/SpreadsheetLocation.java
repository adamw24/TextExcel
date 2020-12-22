package textexcel;

public class SpreadsheetLocation implements Location {
	private int colNumber = 0;
	private int rowNumber = 0;

	public SpreadsheetLocation(String cellName) {
		colNumber = Character.toUpperCase(cellName.charAt(0)) - 'A';
		rowNumber = Integer.parseInt(cellName.substring(1)) - 1;
	}

	public SpreadsheetLocation(int r, int c) {
		colNumber = c;
		rowNumber = r;
	}

	@Override
	public int getRow() {
		return rowNumber;
	}

	@Override
	public int getCol() {
		return colNumber;
	}
}
