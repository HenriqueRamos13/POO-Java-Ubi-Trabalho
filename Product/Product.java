package Product;
import java.util.Calendar;

public class Product {
	private String name;
	private String description;
	private double price;
	private int quantity;
	private Calendar validateDate;
	private boolean gift;

	public Product(String name, String description, double price, int quantity, int year, int month, int day, boolean gift) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.gift = gift;
		validateDate = Calendar.getInstance();
		validateDate.set(year, month - 1, day);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Calendar getValidateDate() {
		return validateDate;
	}

	public int getYear() {
		return validateDate.get(Calendar.YEAR);
	}

	public int getMonth() {
		return validateDate.get(Calendar.MONTH) + 1;
	}

	public int getDay() {
		return validateDate.get(Calendar.DAY_OF_MONTH);
	}

	public void setValidateDate(int year, int month, int day) {
		validateDate.set(year, month, day);
	}

	public boolean getGift() {
		return gift;
	}

	public void setGift(boolean gift) {
		this.gift = gift;
	}

	public void changePrice(double percentage) {
		price = price + (price * percentage / 100);
	}

	public boolean isValid() {
		Calendar today = Calendar.getInstance();
		if (today.after(validateDate)) {
			return false;
		} else {
			return true;
		}
	}

	public String toString() {
		return "Name: " + name + "\nDescription: " + description + "\nPrice: " + price + "\nQuantity: " + quantity
				+ "\nValidate Date: " + validateDate.get(Calendar.YEAR) + "/" + (validateDate.get(Calendar.MONTH) + 1) + "/"
				+ validateDate.get(Calendar.DAY_OF_MONTH);
	}
}
