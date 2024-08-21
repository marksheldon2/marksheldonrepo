package cpsc4620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/*
 * This file is where the front end magic happens.
 *
 * You will have to write the methods for each of the menu options.
 *
 * This file should not need to access your DB at all, it should make calls to the DBNinja that will do all the connections.
 *
 * You can add and remove methods as you see necessary. But you MUST have all of the menu methods (including exit!)
 *
 * Simply removing menu methods because you don't know how to implement it will result in a major error penalty (akin to your program crashing)
 *
 * Speaking of crashing. Your program shouldn't do it. Use exceptions, or if statements, or whatever it is you need to do to keep your program from breaking.
 *
 */

public class Menu {

	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static int numPizzas = 0;

	public static void main(String[] args) throws SQLException, IOException {

		System.out.println("Welcome to Pizzas-R-Us!");

		int menu_option = 0;

		// present a menu of options and take their selection

		PrintMenu();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String option = reader.readLine();
		menu_option = Integer.parseInt(option);

		while (menu_option != 9) {
			switch (menu_option) {
				case 1:// enter order
					EnterOrder();
					break;
				case 2:// view customers
					viewCustomers();
					break;
				case 3:// enter customer
					EnterCustomer();
					break;
				case 4:// view order
					// open/closed/date
					ViewOrders();
					break;
				case 5:// mark order as complete
					MarkOrderAsComplete();
					break;
				case 6:// view inventory levels
					ViewInventoryLevels();
					break;
				case 7:// add to inventory
					AddInventory();
					break;
				case 8:// view reports
					PrintReports();
					break;
			}
			PrintMenu();
			option = reader.readLine();
			menu_option = Integer.parseInt(option);
		}

	}

	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException
	{
		Order lastOrder = DBNinja.getLastOrder();
		int orderNum = lastOrder.getOrderID() + 1;
		/*
		 * EnterOrder should do the following:
		 *
		 * Ask if the order is delivery, pickup, or dinein
		 *   if dine in....ask for table number
		 *   if pickup...
		 *   if delivery...
		 *
		 * Then, build the pizza(s) for the order (there's a method for this)
		 *  until there are no more pizzas for the order
		 *  add the pizzas to the order
		 *
		 * Apply order discounts as needed (including to the DB)
		 *
		 * return to menu
		 *
		 * make sure you use the prompts below in the correct order!
		 */

		// User Input Prompts...
		System.out.println("Is this order for: \n1.) Dine-in\n2.) Pick-up\n3.) Delivery\nEnter the number of your choice:");
		String input = reader.readLine();
		int orderType = Integer.parseInt(input);

		Order order;
		Date date = new Date();
		Customer c = null;

		// Order is Pick-up or delivery
		if (orderType != 1) {
			System.out.println("Is this order for an existing customer? Answer y/n: ");
			String customerChoice = reader.readLine();
			ArrayList<Customer> custs = DBNinja.getCustomerList();
			c = custs.get(0);
			do {
				if (customerChoice.charAt(0) == 'y') {
					System.out.println("Here's a list of the current customers: ");
					viewCustomers();
					System.out.println("Which customer is this order for? Enter ID Number:");
					input = reader.readLine();
					int custID = Integer.parseInt(input);
					for (Customer cust : custs) {
						if (cust.getCustID() == custID) {
							c = cust;
							break;
						}
					}
				} else if (customerChoice.charAt(0) == 'n') {
					EnterCustomer();
					c = custs.get(custs.size() - 1);
				} else {
					System.out.println("ERROR: I don't understand your input for: Is this order an existing customer?");
				}
			}while(customerChoice.charAt(0) != 'n' && customerChoice.charAt(0) != 'y');
		}
		switch(orderType){
			case(1):
				System.out.println("What is the table number for this order?");
				input = reader.readLine();
				int tableNum = Integer.parseInt(input);
				order = new DineinOrder(orderNum, -1, date.toString(), 0.00, 0.00, 0, tableNum);
				break;
			case(2):
				order = new PickupOrder(orderNum, c.getCustID(), date.toString(), 0.00, 0.00, 0,0);
				break;
			case(3):
				order = new DeliveryOrder(orderNum, c.getCustID(), date.toString(), 0.00, 0.00, 0,
						c.getAddress());
				break;
			default:
				order = new PickupOrder(orderNum, c.getCustID(), date.toString(), 0.00, 0.00, 0,0);
				break;

		}
		DBNinja.addOrder(order);
		boolean firstTime = true;
		while(true) {
			if(firstTime) {System.out.println("Let's build a pizza!");	firstTime = false;}
			Pizza ret = buildPizza(orderNum);
			order.addPizza(ret);
			order.setBusPrice(order.getBusPrice() + ret.getBusPrice());
			order.setCustPrice(order.getCustPrice() + ret.getCustPrice());
			System.out.println("Enter -1 to stop adding pizzas...Enter anything else to continue adding pizzas to the order.");
			input = reader.readLine();
			if(input.equals("-1")){
				break;
			}
		}

		System.out.println("Do you want to add discounts to this order? Enter y/n?");
		input = reader.readLine();
		if (input.charAt(0) == 'y') {
			while(true) {
				System.out.println("Which Order Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
				input = reader.readLine();
				int discountCode = Integer.parseInt(input);
				if(discountCode == -1){
					break;
				}
				ArrayList<Discount> discounts = DBNinja.getDiscountList();
				for (Discount d : discounts) {
					if (d.getDiscountID() == discountCode) {
						order.addDiscount(d);
						DBNinja.useOrderDiscount(order, d);
						if(d.isPercent()){
							order.setCustPrice(order.getCustPrice() * (1 - d.getAmount()));
						}
						else{
							order.setCustPrice(order.getCustPrice() - d.getAmount());
						}
						break;
					}
				}

			}
		}
		// If order is a delivery order
		if (orderType == 3) {
			System.out.println("What is the House/Apt Number for this order? (e.g., 111)");
			String houseNum = reader.readLine();
			System.out.println("What is the Street for this order? (e.g., Smile Street)");
			String street = reader.readLine();
			System.out.println("What is the City for this order? (e.g., Greenville)");
			String city = reader.readLine();
			System.out.println("What is the State for this order? (e.g., SC)");
			String state = reader.readLine();
			System.out.println("What is the Zip Code for this order? (e.g., 20605)");
			String zip = reader.readLine();
			c.setAddress(houseNum+" "+street, city, state, zip);
			((DeliveryOrder)order).setAddress(c.getAddress());
		}
		DBNinja.updateOrder(order);
		System.out.println("Finished adding order...Returning to menu...");
	}


	public static void viewCustomers() throws SQLException, IOException
	{
		/*
		 * Simply print out all of the customers from the database.
		 */
		ArrayList<Customer> customers = DBNinja.getCustomerList();

		for(Customer c: customers){
			System.out.println("CustID="+c.getCustID()+" | Name= "+c.getFName()+" "+c.getLName()+", Phone= "+c.getPhone());
		}

	}


	// Enter a new customer in the database
	public static void EnterCustomer() throws SQLException, IOException {
		/*
		 * Ask for the name of the customer:
		 *   First Name <space> Last Name
		 *
		 * Ask for the  phone number.
		 *   (##########) (No dash/space)
		 *
		 * Once you get the name and phone number, add it to the DB
		 */
		ArrayList<Customer> custs = DBNinja.getCustomerList();
		// User Input Prompts...
		System.out.println("What is this customer's name (first <space> last");
		String names = reader.readLine();
		String splitNames[] = names.split(" ");

		System.out.println("What is this customer's phone number (##########) (No dash/space)");
		String phone = reader.readLine();
		Customer customer = new Customer(custs.get(custs.size()-1).getCustID() +1, splitNames[0], splitNames[1], phone);
		DBNinja.addCustomer(customer);

	}

	// View any orders that are not marked as completed
	public static void ViewOrders() throws SQLException, IOException
	{
		/*
		 * This method allows the user to select between three different views of the Order history:
		 * The program must display:
		 * a.	all open orders
		 * b.	all completed orders
		 * c.	all the orders (open and completed) since a specific date (inclusive)
		 *
		 * After displaying the list of orders (in a condensed format) must allow the user to select a specific order for viewing its details.
		 * The details include the full order type information, the pizza information (including pizza discounts), and the order discounts.
		 *
		 */


		// User Input Prompts...
		System.out.println("Would you like to:\n(a) display all orders [open or closed]\n(b) display all open orders\n(c) display all completed [closed] orders\n(d) display orders since a specific date");
		String option = reader.readLine();
		if(option.charAt(0) == 'a'){
			ArrayList<Order> orders = DBNinja.getOrders(false);
			if(orders.size() == 0){
				System.out.println("No orders to display, returning to menu.");
				return;
			}
			for(Order o : orders){
				System.out.println(o.toSimplePrint());
			}
			System.out.println("Which order would you like to see in detail? Enter the number (-1 to exit): ");
			String input = reader.readLine();
			int orderNum = Integer.parseInt(input);
			if(orderNum != -1){
				for(Order o : orders){
					if(o.getOrderID() == orderNum){
						System.out.println(o.toString());
						for (Pizza p : o.getPizzaList()) {
							System.out.println(p.toString());
						}
						for (Discount d : o.getDiscountList()) {
							System.out.println(d.toString());
						}
					}
				}
			}
		}
		else if (option.charAt(0) == 'b'){
			ArrayList<Order> orders = DBNinja.getOrders(true);
			if(orders.size() == 0){
				System.out.println("No orders to display, returning to menu.");
				return;
			}
			for(Order o : orders){
				System.out.println(o.toSimplePrint());
			}
			System.out.println("Which order would you like to see in detail? Enter the number (-1 to exit): ");
			String input = reader.readLine();
			int orderNum = Integer.parseInt(input);
			if(orderNum != -1){
				for(Order o : orders){
					if(o.getOrderID() == orderNum){
						if(o.getOrderID() == orderNum){
							System.out.println(o.toString());
							for (Pizza p : o.getPizzaList()) {
								System.out.println(p.toString());
							}
							for (Discount d : o.getDiscountList()) {
								System.out.println(d.toString());
							}
						}
					}
				}
			}
		}
		else if(option.charAt(0) == 'c'){
			ArrayList<Order> orders = DBNinja.getOrders(false);
			if(orders.size() == 0){
				System.out.println("No orders to display, returning to menu.");
				return;
			}
			for(Order o : orders){
				if(o.getIsComplete() == 1) {
					System.out.println(o.toSimplePrint());
				}
			}
			System.out.println("Which order would you like to see in detail? Enter the number (-1 to exit): ");
			String input = reader.readLine();
			int orderNum = Integer.parseInt(input);
			if(orderNum != -1){
				for(Order o : orders){
					if(o.getOrderID() == orderNum){
						System.out.println(o.toString());
						for (Pizza p : o.getPizzaList()) {
							System.out.println(p.toString());
						}
						for (Discount d : o.getDiscountList()) {
							System.out.println(d.toString());
						}
					}
				}
			}
		}
		else if(option.charAt(0) == 'd'){
			System.out.println("What is the date you want to restrict by? (FORMAT= YYYY-MM-DD)");
			String date = reader.readLine();
			String dateParts[] = date.split("-");
			if(dateParts.length == 3) {
				ArrayList<Order> orders = DBNinja.getOrders(false);
				if(orders.size() == 0) {
					System.out.println("No orders to display, returning to menu.");
					return;
				}
				int numOrders = 0;
				for (Order o : orders) {
					String orderDateParts[] = o.getDate().split("-"); //format: YYYY-MM-DD
					if ((Integer.parseInt(dateParts[0]) < Integer.parseInt(orderDateParts[0])) ||
							((Integer.parseInt(dateParts[0]) == Integer.parseInt(orderDateParts[0])) && (Integer.parseInt(dateParts[1]) < Integer.parseInt(orderDateParts[1]))) ||
							((Integer.parseInt(dateParts[0]) == Integer.parseInt(orderDateParts[0])) && (Integer.parseInt(dateParts[1]) == Integer.parseInt(orderDateParts[1])) && (Integer.parseInt(dateParts[2]) <= Integer.parseInt(orderDateParts[2])))) {
						if(numOrders == 0){
//							System.out.println("OrderNum\tOrderType\tOrder Date\tTotalOrderPrice\tTotalOrderCost\tOrderStatus");
						}
//						System.out.println(o.getOrderID() + "\t" + o.getOrderType() + "\t" + o.getDate() + "\t" + o.getBusPrice() + "\t" + o.getCustPrice() + "\t" + o.getIsComplete());
						System.out.println(o.toSimplePrint());
						numOrders++;
					}
				}
				if(numOrders != 0) {
					System.out.println("Which order would you like to see in detail? Enter the number (-1 to exit): ");
					String input = reader.readLine();
					int orderNum = Integer.parseInt(input);
					if (orderNum != -1) {
						for (Order o : orders) {
							if (o.getOrderID() == orderNum) {
								System.out.println(o.toString());
								for (Pizza p : o.getPizzaList()) {
									System.out.println(p.toString());
								}
								for (Discount d : o.getDiscountList()) {
									System.out.println(d.toString());
								}
							}
						}
					}
				}
				else{
					System.out.println("No orders to display, returning to menu.");
				}
			}
			else{
				System.out.println("I don't understand that input, returning to menu");
			}
		}
		else{
			System.out.println("Incorrect entry, returning to menu.");
		}



	}


	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException
	{
		/*
		 * All orders that are created through java (part 3, not the orders from part 2) should start as incomplete
		 *
		 * When this method is called, you should print all of the "opoen" orders marked
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 *
		 */
		ArrayList<Order> orders = DBNinja.getOrders(true);
		if(orders.size() == 0){
			System.out.println("There are no open orders currently... returning to menu...");
			return;
		}
		System.out.println("OrderNum\tOrderType\tOrder Date\tTotalOrderPrice\tTotalOrderCost");
		for(Order o : orders){
			System.out.println(o.getOrderID()+"\t"+o.getOrderType()+"\t"+o.getDate()+"\t"+o.getBusPrice()+"\t"+o.getCustPrice());
		}
		System.out.println("Which order would you like mark as complete? Enter the OrderID: ");
		String input = reader.readLine();
		int orderNum = Integer.parseInt(input);
		for(Order o : orders){
			if(o.getOrderID() == orderNum){
				DBNinja.completeOrder(o);
				return;
			}
		}
		System.out.println("Incorrect entry, not an option");
	}

	public static void ViewInventoryLevels() throws SQLException, IOException
	{
		/*
		 * Print the inventory. Display the topping ID, name, and current inventory
		 */
		ArrayList<Topping> toppings = DBNinja.getToppingList();
		System.out.println("ToppingID\tName\tCurrent Inventory");
		for(Topping t : toppings){
			System.out.println(t.getTopID()+"\t"+t.getTopName()+"\t"+t.getCurINVT());
		}

	}


	public static void AddInventory() throws SQLException, IOException
	{
		/*
		 * This should print the current inventory and then ask the user which topping (by ID) they want to add more to and how much to add
		 */

		// User Input Prompts...
		ArrayList<Topping> toppings = DBNinja.getToppingList();
		System.out.println("Which topping do you want to add inventory to? Enter the number: ");
		ViewInventoryLevels();
		String option = reader.readLine();
		int toppID = Integer.parseInt(option);
		for (Topping t : toppings) {
			if (t.getTopID() == toppID) {
				System.out.println("How many units would you like to add? ");
				option = reader.readLine();
				int amnt = Integer.parseInt(option);
				DBNinja.addToInventory(t, amnt);
				break;
			}
		}
	}

	// A method that builds a pizza. Used in our add new order method
	public static Pizza buildPizza(int orderID) throws SQLException, IOException
	{

		/*
		 * This is a helper method for first menu option.
		 *
		 * It should ask which size pizza the user wants and the crustType.
		 *
		 * Once the pizza is created, it should be added to the DB.
		 *
		 * We also need to add toppings to the pizza. (Which means we not only need to add toppings here, but also our bridge table)
		 *
		 * We then need to add pizza discounts (again, to here and to the database)
		 *
		 * Once the discounts are added, we can return the pizza
		 */



		// User Input Prompts...
		System.out.println("What size is the pizza?");
		System.out.println("1."+DBNinja.size_s);
		System.out.println("2."+DBNinja.size_m);
		System.out.println("3."+DBNinja.size_l);
		System.out.println("4."+DBNinja.size_xl);
		System.out.println("Enter the corresponding number: ");
		String input = reader.readLine();
		int size = Integer.parseInt(input);
		String sizeString = "";
		switch(size){
			case(1):
				sizeString = DBNinja.size_s;
				break;
			case(2):
				sizeString = DBNinja.size_m;
				break;
			case(3):
				sizeString = DBNinja.size_l;
				break;
			case(4):
				sizeString = DBNinja.size_xl;
				break;
		}
		System.out.println("What crust for this pizza?");
		System.out.println("1."+DBNinja.crust_thin);
		System.out.println("2."+DBNinja.crust_orig);
		System.out.println("3."+DBNinja.crust_pan);
		System.out.println("4."+DBNinja.crust_gf);
		System.out.println("Enter the corresponding number: ");
		input = reader.readLine();
		int crustType = Integer.parseInt(input);
		String crustTypeString = "";
		switch(crustType){
			case(1):
				crustTypeString = DBNinja.crust_thin;
				break;
			case(2):
				crustTypeString = DBNinja.crust_orig;
				break;
			case(3):
				crustTypeString = DBNinja.crust_pan;
				break;
			case(4):
				crustTypeString = DBNinja.crust_gf;
				break;
		}
		Order lastOrder = DBNinja.getLastOrder();
		Pizza ret = new Pizza(lastOrder.getPizzaList().get(lastOrder.getPizzaList().size() -1).getPizzaID() + 1, sizeString, crustTypeString, orderID, "Processing", lastOrder.getDate(), DBNinja.getBaseCustPrice(sizeString, crustTypeString),
				DBNinja.getBaseBusPrice(sizeString, crustTypeString));
		DBNinja.addPizza(ret);


		while(true) {
			System.out.println("Available Toppings:");
			ArrayList<Topping> toppings = DBNinja.getToppingList();
			DBNinja.printInventory();
			System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings: ");
			input = reader.readLine();
			int topID = Integer.parseInt(input);
			if(topID == -1){
				break;
			}
			else{
				Topping selected = toppings.get(0);
				for(Topping t : toppings){
					if(t.getTopID() == topID){
						selected = t;
						break;
					}
				}
				System.out.println("Do you want to add extra topping? Enter y/n");
				input = reader.readLine();
				if(input.charAt(0) == 'y'){
					if(selected.getCurINVT() >= selected.getMinINVT()+2) {
						DBNinja.useTopping(ret, selected, true);
						ret.addToppings(selected, true);
						ret.setBusPrice(ret.getBusPrice() + (selected.getBusPrice()*2));
						ret.setCustPrice(ret.getCustPrice() + (selected.getCustPrice()*2));
					}
					else{
						System.out.println("We don't have enough of that topping to add it...");
					}
				}
				else{
					if(selected.getCurINVT() >= selected.getMinINVT()+1) {
						DBNinja.useTopping(ret, selected, false);
						ret.addToppings(selected, false);
						ret.setBusPrice(ret.getBusPrice() + selected.getBusPrice());
						ret.setCustPrice(ret.getCustPrice() + selected.getCustPrice());
					}
					else{
						System.out.println("We don't have enough of that topping to add it...");
					}
				}

			}
		}
		System.out.println("Do you want to add discounts to this Pizza? Enter y/n?");
		input = reader.readLine();
		if(input.charAt(0) == 'y'){
			String moreDiscounts = "";
			do{
				System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
				ArrayList<Discount> discounts = DBNinja.getDiscountList();
				for(Discount d : discounts){
					System.out.println(d);
				}
				input = reader.readLine();
				int discID = Integer.parseInt(input);
				if(discID == -1){
					break;
				}

				for(Discount d : discounts){
					if(d.getDiscountID() == discID){
						DBNinja.usePizzaDiscount(ret, d);
						if(d.isPercent()) {
							ret.setCustPrice(ret.getCustPrice()* (1-d.getAmount()));
						}
						else{
							ret.setCustPrice(ret.getCustPrice() - d.getAmount());
						}
						ret.addDiscounts(d);
						break;
					}
				}

			}while(true);
		}
		return ret;
	}


	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		/*
		 * This method asks the use which report they want to see and calls the DBNinja method to print the appropriate report.
		 *
		 */
		System.out.println("Which report do you wish to print? Enter\n(a) ToppingPopularity\n(b) ProfitByPizza\n(c) ProfitByOrderType:");
		String option = reader.readLine();
		char viewNumber = option.charAt(0);

		// User Input Prompts...
		switch (viewNumber) {
			case 'a':// topping pop
				System.out.println("Topping\tToppingCount");
				DBNinja.printToppingPopReport();
				break;
			case 'b':// profit by pizza
				System.out.println("Pizza Size\tPizza Crust\tProfit\tLastOrderDate");
				DBNinja.printProfitByPizzaReport();
				break;
			case 'c':// profit by pizza
				System.out.println("Order Type\tOrderMonth\tTotal Order Price\tTotal Order Cost\tProfit");
				DBNinja.printProfitByOrderType();
				break;
			default:
				System.out.println("I don't understand that input... returning to menu...");
				break;
		}

	}

	//Prompt - NO CODE SHOULD TAKE PLACE BELOW THIS LINE
	// DO NOT EDIT ANYTHING BELOW HERE, THIS IS NEEDED TESTING.
	// IF YOU EDIT SOMETHING BELOW, IT BREAKS THE AUTOGRADER WHICH MEANS YOUR GRADE WILL BE A 0 (zero)!!

	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}

	/*
	 * autograder controls....do not modiify!
	 */

	public final static String autograder_seed = "6f1b7ea9aac470402d48f7916ea6a010";


	private static void autograder_compilation_check() {

		try {
			Order o = null;
			Pizza p = null;
			Topping t = null;
			Discount d = null;
			Customer c = null;
			ArrayList<Order> alo = null;
			ArrayList<Discount> ald = null;
			ArrayList<Customer> alc = null;
			ArrayList<Topping> alt = null;
			double v = 0.0;
			String s = "";

			DBNinja.addOrder(o);
			DBNinja.addPizza(p);
			DBNinja.useTopping(p, t, false);
			DBNinja.usePizzaDiscount(p, d);
			DBNinja.useOrderDiscount(o, d);
			DBNinja.addCustomer(c);
			DBNinja.completeOrder(o);
			alo = DBNinja.getOrders(false);
			o = DBNinja.getLastOrder();
			alo = DBNinja.getOrdersByDate("01/01/1999");
			ald = DBNinja.getDiscountList();
			d = DBNinja.findDiscountByName("Discount");
			alc = DBNinja.getCustomerList();
			c = DBNinja.findCustomerByPhone("0000000000");
			alt = DBNinja.getToppingList();
			t = DBNinja.findToppingByName("Topping");
			DBNinja.addToInventory(t, 1000.0);
			v = DBNinja.getBaseCustPrice("size", "crust");
			v = DBNinja.getBaseBusPrice("size", "crust");
			DBNinja.printInventory();
			DBNinja.printToppingPopReport();
			DBNinja.printProfitByPizzaReport();
			DBNinja.printProfitByOrderType();
			s = DBNinja.getCustomerName(0);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}


}


