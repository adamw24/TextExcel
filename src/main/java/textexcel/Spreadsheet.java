package textexcel;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class Spreadsheet implements Grid {
	@Override
	public String processCommand(String command) {
		if (command.equals(""))
			return command;
		String[] com = command.split(" ");
		try {
			if (command.toLowerCase().equals("clear")) {
				clear();
			} else if (com[0].toLowerCase().equals("save")) {
				PrintWriter writer = new PrintWriter(com[1], "UTF-8");
				for (int r = 0; r <= 20; r++) {
					char C = 'A';
					for (int c = 0; c <= 12; c++, C++) {
						SpreadsheetLocation l = new SpreadsheetLocation(r, c);
						Cell cell = getCell(l);
						if (cell instanceof ValueCell) {
							writer.write("" + C + (r + 1) + ",ValueCell," + cell.fullCellText() + "\n");
						} else if (cell instanceof PercentCell) {
							writer.write("" + C + (r + 1) + ",PercentCell," + cell.fullCellText() + "\n");
						} else if (cell instanceof TextCell) {
							writer.write("" + C + (r + 1) + ",TextCell," + cell.fullCellText() + "\n");
						} else if (cell instanceof FormulaCell) {
							writer.write("" + C + (r + 1) + ",FormulaCell," + cell.fullCellText() + "\n");
						}
					}
				}
				writer.close();
			} else if (com[0].toLowerCase().equals("open")) {
				File f = new File(com[1]);
				Scanner in = new Scanner(f);
				while (in.hasNextLine()) {
					String current = in.nextLine();
					String[] info = current.split(",");
					String content = info[2];
					SpreadsheetLocation loc = new SpreadsheetLocation(info[0]);
					if (info[1].equals("TextCell")) {
						sheet[loc.getRow()][loc.getCol()] = new TextCell(content);
					} else if (info[1].equals("PercentCell")) {
						sheet[loc.getRow()][loc.getCol()] = new PercentCell(content);
					} else if (info[1].equals("FormulaCell")) {
						sheet[loc.getRow()][loc.getCol()] = new FormulaCell(content, this);
					} else {
						sheet[loc.getRow()][loc.getCol()] = new ValueCell(content);
					}
				}
				in.close();
			} else if (com[0].toLowerCase().equals("clear")) {
				SpreadsheetLocation loc = new SpreadsheetLocation(com[1]);
				sheet[loc.getRow()][loc.getCol()] = new EmptyCell();
			} else if (com.length == 1) {
				SpreadsheetLocation loc = new SpreadsheetLocation(com[0]);
				return getCell(loc).fullCellText();
			} else if (com[1].equals("=")) {
				com = command.split(" = ");
				SpreadsheetLocation loc = new SpreadsheetLocation(com[0]);
				String f = command.substring(com[0].length() + 3, command.length());
				
				if (com[1].charAt(0) == '"') {
					sheet[loc.getRow()][loc.getCol()] = new TextCell(f);
				} else if (command.charAt(command.length() - 1) == '%') {
					sheet[loc.getRow()][loc.getCol()] = new PercentCell(f);
				} else if (com[1].charAt(0) == '(') {
					sheet[loc.getRow()][loc.getCol()] = new FormulaCell(f, this);
				} else {
					sheet[loc.getRow()][loc.getCol()] = new ValueCell(f);
				}
			}
		} catch (IOException ex) {
			System.out.println("Oops, there was an error :(");
		}

		return getGridText();
	}

	@Override
	public int getRows() {
		return 20;
	}

	@Override
	public int getCols() {
		return 12;
	}

	@Override
	public Cell getCell(Location loc) {
		return sheet[loc.getRow()][loc.getCol()];
	}

	private String space9 = "         ";

	private Cell[][] sheet = new Cell[21][13];

	private void clear() {
		for (int r = 0; r <= 20; r++) {
			for (int c = 0; c <= 12; c++) {
				sheet[r][c] = new EmptyCell();
			}
		}
	}

	public Spreadsheet() {
		clear();
	}

	@Override
	public String getGridText() {
		String result = "   ";
		for (char c = 'A'; c <= 'L'; c++) {
			result += "|" + c + space9;
		}
		result += "|\n";
		for (int r = 0; r < getRows(); r++) {
			result += String.format("%-3s", r + 1);
			for (int c = 0; c < getCols(); c++) {
				result += "|" + sheet[r][c].abbreviatedCellText();
			}
			result += "|\n";
		}
		return result;
	}
}
