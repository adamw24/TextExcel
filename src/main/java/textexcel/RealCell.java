package textexcel;

public class RealCell implements Cell{
	protected String stringValue;
	protected double realValue;
	
	public double getDoubleValue() {
		return realValue;
	}

	public String abbreviatedCellText() {
		return String.format("%-10.10s", stringValue);
	}

	public String fullCellText() {
		return Double.toString(realValue);
	}
}
