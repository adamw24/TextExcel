package textexcel;

import java.util.*;

public class TextExcel {
	public static void main(String[] args) {
		// add your command loop here
		Spreadsheet my = new Spreadsheet();
		System.out.println(my.getGridText());
		Scanner userInput = new Scanner(System.in);
		String in = userInput.nextLine();
		while (in.toLowerCase().indexOf("quit") == -1) {
			System.out.println(my.processCommand(in));
			in = userInput.nextLine();
		}
		System.out.println("Program quit.");
		userInput.close();
	}
}
