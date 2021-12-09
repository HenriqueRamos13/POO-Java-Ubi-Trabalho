package Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.*;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import Product.Product;
import Product.Gifts;

public class Stock {
	List<Product> productList = new ArrayList<>();
	List<Gifts> giftsList = new ArrayList<>();

	public Stock() {
		try {
			File file = new File("./DB/products.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String st;
			while ((st = br.readLine()) != null) {
				String[] productInfo = st.split(":");

				if (productInfo.length == 8) {
					Product product = new Product(productInfo[0], productInfo[1], Double.parseDouble(productInfo[2]),
							Integer.parseInt(productInfo[3]), Integer.parseInt(productInfo[4]),
							Integer.parseInt(productInfo[5]), Integer.parseInt(productInfo[6]),
							Boolean.parseBoolean(productInfo[7]));
					productList.add(product);
				} else {
					Gifts gifts = new Gifts(productInfo[0], productInfo[1], Double.parseDouble(productInfo[2]),
							Integer.parseInt(productInfo[3]), Integer.parseInt(productInfo[4]),
							Integer.parseInt(productInfo[5]), Integer.parseInt(productInfo[6]),
							Boolean.parseBoolean(productInfo[7]), Long.parseLong(productInfo[8]));
					giftsList.add(gifts);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Product createProductTemplate(String prodName, Product toEdit, boolean isGift) {
		double price;
		int quantity;
		int year;
		int month;
		int day;

		boolean isNewProduct = false;
		if (prodName == null || prodName.length() == 0) {
			isNewProduct = true;
			System.out.println("Enter product name: ");
			prodName = System.console().readLine();

			while (prodName == null || prodName.length() == 0 || this.verifyProductExist(prodName)) {
				System.out.println(prodName == null || prodName.length() == 0 ? "Insira um nome válido"
						: "Produto já cadastrado, insira um novo nome para este produto: ");
				prodName = System.console().readLine();
			}
		}

		System.out.println(
				"Enter product description" + (isNewProduct ? "" : "(" + toEdit.getDescription() + ")") + ": ");
		String description = System.console().readLine();
		if (description == null || description.length() == 0 && !isNewProduct) {
			description = toEdit.getDescription();
		}

		try {
			System.out.println("Enter product price" + (isNewProduct ? "" : "(" + toEdit.getPrice() + ")") + ": ");
			price = Double.parseDouble(System.console().readLine());
		} catch (NumberFormatException e) {
			if (!isNewProduct) {
				price = toEdit.getPrice();
			} else {
				return null;
			}
		}

		try {
			System.out
					.println("Enter product quantity" + (isNewProduct ? "" : "(" + toEdit.getQuantity() + ")") + ": ");
			quantity = Integer.parseInt(System.console().readLine());
		} catch (NumberFormatException e) {
			if (!isNewProduct) {
				quantity = toEdit.getQuantity();
			} else {
				return null;
			}
		}

		try {
			System.out.println("Enter product year" + (isNewProduct ? "" : "(" + toEdit.getYear() + ")") + ": ");
			year = Integer.parseInt(System.console().readLine());
		} catch (NumberFormatException e) {
			if (!isNewProduct) {
				year = toEdit.getYear();
			} else {
				return null;
			}
		}

		try {
			System.out.println("Enter product month" + (isNewProduct ? "" : "(" + toEdit.getMonth() + ")") + ": ");
			month = Integer.parseInt(System.console().readLine());
		} catch (NumberFormatException e) {
			if (!isNewProduct) {
				month = toEdit.getMonth();
			} else {
				return null;
			}
		}

		try {
			System.out.println("Enter product day" + (isNewProduct ? "" : "(" + toEdit.getDay() + ")") + ": ");
			day = Integer.parseInt(System.console().readLine());
		} catch (NumberFormatException e) {
			if (!isNewProduct) {
				day = toEdit.getDay();
			} else {
				return null;
			}
		}

		if (isGift) {
			Gifts gift = new Gifts(prodName, description, price, quantity, year, month, day, isGift,
					Calendar.getInstance().getTimeInMillis());

			try {
				FileWriter fw = new FileWriter("./DB/products.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.write(
						gift.getName() + ":" + gift.getDescription() + ":" + gift.getPrice() + ":" + gift.getQuantity()
								+ ":" + gift.getYear() + ":" + gift.getMonth() + ":" + gift.getDay() + ":"
								+ gift.getGift() + ":" + gift.getCode() + "\n");
				out.close();

				giftsList.add(gift);
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		Product prod = new Product(prodName, description, price, quantity, year, month, day, isGift);
		return prod;
	}

	public void addProduct() {
		boolean isGift = false;

		try {
			System.out.println("Produt is a gift? (false): ");
			isGift = Boolean.parseBoolean(System.console().readLine());
		} catch (NumberFormatException e) {
			System.out.println("Produto definido como não sendo gift.");
		}

		Product prod = createProductTemplate(null, null, isGift);

		if (!isGift) {
			try {
				FileWriter fw = new FileWriter("./DB/products.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.write(prod.getName() + ":" + prod.getDescription() + ":" + prod.getPrice() + ":" + prod.getQuantity()
						+ ":" + prod.getYear() + ":" + prod.getMonth() + ":" + prod.getDay() + ":" + prod.getGift() + "\n");
				out.close();

				productList.add(prod);
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	public void removeProduct(String name) {
		boolean productExist = verifyProductExist(name);

		if (productExist) {
			productList.removeIf(product -> product.getName().equals(name));
			try {
				File file = new File("./DB/products.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String st;
				StringBuilder sb = new StringBuilder();
				while ((st = br.readLine()) != null) {
					if (!st.split(":")[0].equals(name)) {
						sb.append(st + "\n");
					}
				}
				br.close();

				FileWriter fw = new FileWriter("./DB/products.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.write(sb.toString());
				out.close();
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		} else {
			System.out.println("Product not found\n\n");
		}

	}

	public Product getOne(String name) {
		for (Product product : productList) {
			if (product.getName().equals(name)) {
				return product;
			}
		}
		return null;
	}

	public void findProductByName(String productName) {
		this.productList.stream().filter(p -> p.getName().equals(productName)).forEach(p -> {
			System.out.println(p.toString());
			System.out.println();
		});
	}

	public boolean verifyProductExist(String productName) {
		return this.productList.stream().anyMatch(p -> p.getName().equals(productName));
	}

	public int verifyProductValidateDate(String productName) {
		Product product = this.productList.stream().filter(p -> p.getName().equals(productName)).findFirst().get();

		LocalDate validateDate = LocalDate.of(product.getYear(), product.getMonth(), product.getDay());
		LocalDate today = LocalDate.now();
		long days = ChronoUnit.DAYS.between(today, validateDate);
		return (int) days;

	}

	public void listAllProducts() {
		this.productList.stream().forEach(p -> {
			System.out.println(p.toString());
			System.out.println("-----------------");
		});
	}

	public void listGifts() {
		this.giftsList.stream().forEach(p -> {
			System.out.println(p.toString());
			System.out.println("-----------------");
		});
	}

	public boolean editProduct(String productName, Product product) {
		boolean productFound = false;
		for (int i = 0; i < this.productList.size(); i++) {
			if (this.productList.get(i).getName().equals(productName)) {
				this.productList.set(i, product);
				productFound = true;
			}
		}
		if (productFound) {
			try {
				File file = new File("./DB/products.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String st;
				String newFile = "";
				while ((st = br.readLine()) != null) {
					String[] productInfo = st.split(":");
					if (productInfo[0].equals(productName)) {
						newFile += product.getName() + ":" + product.getDescription() + ":" + product.getPrice() + ":"
								+ product.getQuantity() + ":" + product.getYear() + ":" + product.getMonth() + ":"
								+ product.getDay() + "\n";
					} else {
						newFile += st + "\n";
					}
				}
				br.close();
				FileWriter fw = new FileWriter("./DB/products.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.write(newFile);
				out.close();
				return true;
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}

	public void getProductsToValidate() {
		this.productList.stream().filter(p -> this.verifyProductValidateDate(p.getName()) < 10
				&& this.verifyProductValidateDate(p.getName()) > -1).forEach(p -> {
					System.out.println(p.toString());
					System.out.println("-----------------");
				});
	}

	public void getProductsExpired() {
		this.productList.stream().filter(p -> this.verifyProductValidateDate(p.getName()) < 0).forEach(p -> {
			System.out.println(p.toString());
			System.out.println("-----------------");
		});
	}
}
