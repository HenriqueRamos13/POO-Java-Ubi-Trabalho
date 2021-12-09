package Menu;

import Product.Product;
import Stock.Stock;
import java.util.Scanner;

public class Menu {
	Stock stock;

	public Menu(Stock stock) {
		this.stock = stock;
	}

	public void show() {
		int choice;

		do {
			System.out.println(
					"1. Adicionar produto\n2. Remover produto\n3. Listar Produtos\n4. Verificar dados de um produto\n5. Verificar se um produto está valido\n6. Editar produto\n7. Verificar produtos que vão passar da validade\n8. Listar produtos que já passaram da validade\n9. Listar Gifts\n12. Sair\nEnter your choice: \n");
			try {
				choice = Integer.parseInt(System.console().readLine());
			} catch (NumberFormatException e) {
				choice = -1;
			}
			System.out.println("\n");

			switch (choice) {
			case 1:
				try {
					System.out.println("How many products you wanna add?:");
					int n = Integer.parseInt(System.console().readLine());

					for (int i = 0; i < n; i++) {
						this.stock.addProduct();
					}
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 2:
				try {
					System.out.println("Enter product name to remove: ");
					String prodName = System.console().readLine();
					this.stock.removeProduct(prodName);
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 3:
				try {
					this.stock.listAllProducts();
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 4:
				try {
					System.out.println("Enter product name: ");
					String productName = System.console().readLine();

					this.stock.findProductByName(productName);

					waitEnter();
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 5:
				try {
					System.out.println("Enter product name: ");
					String productToVerify = System.console().readLine();
					int productDaysToValid = this.stock.verifyProductValidateDate(productToVerify);
					if (productDaysToValid >= 0) {
						System.out.println("\nProduto ainda válido\nRestam " + productDaysToValid + " dias para o produto expirar\n");
					} else {
						System.out.println("\nProduto não válido\nJá faz " + Math.abs(productDaysToValid) + " dias que o produto expirou\n");
					}

					waitEnter();
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 6:
				try {
					System.out.println("Enter product name: ");
					String toEdit = System.console().readLine();
					Product toEditCopy = this.stock.getOne(toEdit);

					if (toEditCopy != null) {
						Product newProduct = this.stock.createProductTemplate(toEdit, toEditCopy, toEditCopy.getGift());

						this.stock.editProduct(toEdit, newProduct);
					} else {
						System.out.println("Product not found");
					}
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 7:
				try {
					this.stock.getProductsToValidate();
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 8:
				try {
					this.stock.getProductsExpired();
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 9:
				try {
					this.stock.listGifts();
				} catch (Exception e) {
					System.out.println("Invalid input\n\n");
				}
				break;
			case 12:
				System.out.println("You choose exit");
				break;
			default:
				System.out.println("You choose wrong menu");
				break;
			}
		} while (choice != 12);
	}

	public static void loading(int showText) {
		if (showText == 1) {
			System.out.println("Loading...");
		}

		for (int i = 0; i < 3; i++) {
			System.out.print(".");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\n");
	}

	public static void start() {
		System.out.println("\n\nWelcome to our menu");
		loading(0);
		System.out.println("Please choose menu\n\n");
	}

	public static void waitEnter() {
		Scanner s = new Scanner(System.in);

		System.out.println("Press enter to continue.....");

		s.nextLine();
	}
}
