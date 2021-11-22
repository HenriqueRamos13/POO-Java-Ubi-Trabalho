import Menu.Menu;
import Stock.Stock;

public class Main {

	public static void main(String[] args) {
		Stock stock = new Stock();
		Menu menu = new Menu(stock);

		Menu.start();
		menu.show();
	}
}
