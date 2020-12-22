package textexcel;

public class PercentCell extends RealCell {
	public PercentCell(String p) {
		p = p.substring(0, p.length() - 1);
		realValue = Double.parseDouble(p) / 100.0;
		if (p.indexOf('.') != -1) {
			String[] parts = p.split("\\.");
			stringValue = parts[0] + "%";
		} else {
			stringValue = p + "%";

		}

	}
}
