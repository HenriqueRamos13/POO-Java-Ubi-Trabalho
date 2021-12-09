package Product;

public class Gifts extends Product {
	private long code;

	public Gifts(String name, String description, double price, int quantity, int year, int month, int day, boolean isGift, long code) {
		super(name, description, price, quantity, year, month, day, true);
		this.code = code;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return super.toString() + "\nCode: " + code;
	}
}
