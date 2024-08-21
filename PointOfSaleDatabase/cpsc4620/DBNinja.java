package cpsc4620;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 *
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 *
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 *
 *
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery, and sizes and crusts
	public final static String pickup = "pickup";
	public final static String delivery = "delivery";
	public final static String dine_in = "dinein";

	public final static String size_s = "Small";
	public final static String size_m = "Medium";
	public final static String size_l = "Large";
	public final static String size_xl = "XLarge";

	public final static String crust_thin = "Thin";
	public final static String crust_orig = "Original";
	public final static String crust_pan = "Pan";
	public final static String crust_gf = "Gluten-Free";




	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			if(conn == null) System.out.println("connection null");
			return true;
		} catch (SQLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}


	public static void addOrder(Order o) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * add code to add the order to the DB. Remember that we're not just
		 * adding the order to the order DB table, but we're also recording
		 * the necessary data for the delivery, dinein, and pickup tables
		 *
		 */
		String query = "insert into ordered(OrderDate, OrderType, OrderPriceCustomer, OrderCostBusiness, OrderStatus)" +
				"values(?, ?, ?, ?, ?);";
		PreparedStatement ps = conn.prepareStatement(query);
		String[] dateParts = o.getDate().split(" ");
		String monthNUM = "";
		switch (dateParts[1]) {
			case "Jan":
				monthNUM = "01";
				break;
			case "Feb":
				monthNUM = "02";
				break;
			case "Mar":
				monthNUM = "03";
				break;
			case "Apr":
				monthNUM = "04";
				break;
			case "May":
				monthNUM = "05";
				break;
			case "Jun":
				monthNUM = "06";
				break;
			case "Jul":
				monthNUM = "07";
				break;
			case "Aug":
				monthNUM = "08";
				break;
			case "Sep":
				monthNUM = "09";
				break;
			case "Oct":
				monthNUM = "10";
				break;
			case "Nov":
				monthNUM = "11";
				break;
			default:
				monthNUM = "12";
				break;
		}
		ps.setString(1, dateParts[5] + "-" + monthNUM + "-" + dateParts[2]);
		switch (o.getOrderType()) {
			case DBNinja.delivery:
				ps.setString(2, "Delivery");
			case DBNinja.pickup:
				ps.setString(2, "Takeout");
			default:
				ps.setString(2, "DineIn");
		}
		ps.setDouble(3, o.getCustPrice());
		ps.setDouble(4, o.getBusPrice());
		if (o.getIsComplete() == 1) {
			ps.setString(5, "Completed");
		} else {
			ps.setString(5, "Processing");
		}
		ps.execute();

		String getPizza = "select max(OrderID) from ordered;";
		Statement s = conn.createStatement();
		ResultSet rset = s.executeQuery(getPizza);
		while (rset.next()) {
			o.setOrderID(rset.getInt(1));
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	public static void updateOrder(Order o) throws SQLException, IOException
	{
		connect_to_db();
		//if the order exists update the prices
		String query = "update ordered " +
				"set OrderCostBusiness = ?, OrderPriceCustomer = ? " +
				"where OrderID = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setDouble(1, o.getBusPrice());
		ps.setDouble(2, o.getCustPrice());
		ps.setDouble(3, o.getOrderID());
		ps.executeUpdate();

		conn.close();
	}

	public static void addPizza(Pizza p) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Add the code needed to insert the pizza into the database.
		 * Keep in mind adding pizza discounts and toppings associated with the pizza,
		 * there are other methods below that may help with that process.
		 *
		 */

		String idQuery = "select BasePriceID from base_price where BaseCrust = ? and BaseSize = ?;";
		PreparedStatement prepState;
		prepState = conn.prepareStatement(idQuery);
		prepState.setString(1, p.getCrustType());
		prepState.setString(2, p.getSize());
		ResultSet results = prepState.executeQuery();

		int baseID = -1;
		if (results.next()) {
			baseID = results.getInt(1);
		}

		String query = "insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaState, " +
				"PizzaBasePriceID, PizzaPriceCustomer, PizzaCostBusiness) " +
				"values(?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement ps;
		ps = conn.prepareStatement(query);
		ps.setInt(1, p.getOrderID());
		ps.setString(2, p.getCrustType());
		ps.setString(3, p.getSize());
		ps.setString(4, p.getPizzaState());
		ps.setInt(5, baseID);
		ps.setDouble(6, p.getCustPrice());
		ps.setDouble(7, p.getBusPrice());
		ps.execute();

		String getPizza = "select max(PizzaID) from pizza;";
		Statement s  = conn.createStatement();
		ResultSet rset = s.executeQuery(getPizza);
		while(rset.next()){
			p.setPizzaID(rset.getInt(1));
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}


	public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException //this method will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
	{
		connect_to_db();
		/*
		 * This method should do 2 two things.
		 * - update the topping inventory every time we use t topping (accounting for extra toppings as well)
		 * - connect the topping to the pizza
		 *   What that means will be specific to your implementation.
		 *
		 * Ideally, you shouldn't let toppings go negative....but this should be dealt with BEFORE calling this method.
		 *
		 */
		String updateToppingQuery = "update topping set ToppingCurrInventory = ToppingCurrInventory - ?" +
				" where ToppingID = ?;";
		PreparedStatement ps1;
		ps1 = conn.prepareStatement(updateToppingQuery);
		ps1.setInt(1, (isDoubled? 2:1));
		ps1.setInt(2, t.getTopID());
		ps1.executeUpdate();

		String toppingOnPizzaQuery = "insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID) " +
				"values(?, ?);";
		PreparedStatement ps2 = conn.prepareStatement(toppingOnPizzaQuery);
		ps2.setInt(1, p.getPizzaID());
		ps2.setInt(2, t.getTopID());
		ps2.executeUpdate();

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}


	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * This method connects a discount with a Pizza in the database.
		 *
		 * What that means will be specific to your implementation.
		 */
		String query = "insert into pizza_discount (PizzaDiscountDiscountCode, PizzaDiscountPizzaID) " +
				"values(?, ?);";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, d.getDiscountID());
		stmt.setInt(2, p.getPizzaID());
		stmt.execute();




		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * This method connects a discount with an order in the database
		 *
		 * You might use this, you might not depending on where / how to want to update
		 * this information in the dabast
		 */
		String query = "insert into order_discount (OrderDiscountDiscountCode, OrderDiscountOrderID) " +
				"values(?, ?);";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, o.getOrderID());
		stmt.setInt(2, d.getDiscountID());
		stmt.execute();




		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void addCustomer(Customer c) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This method adds a new customer to the database.
		 *
		 */
		if(c.getAddress() != null) {
			String[] addressParts = c.getAddress().split("\n");
			String query = "insert into customer(CustomerFName, CustomerLName, CustomerPhone, CustomerAddress, CustomerCity, CustomerState, CustomerZip) " +
					"values (?,?,?,?,?,?,?);";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getFName());
			stmt.setString(2, c.getLName());
			stmt.setString(3, c.getPhone());
			stmt.setString(3, addressParts[0]);
			stmt.setString(4, addressParts[1]);
			stmt.setString(5, addressParts[2]);
			stmt.setString(6, addressParts[3]);
			stmt.execute();
		}
		else{
			String query = "insert into customer(CustomerFName, CustomerLName, CustomerPhone) " +
					"values (?,?,?);";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getFName());
			stmt.setString(2, c.getLName());
			stmt.setString(3, c.getPhone());
			stmt.execute();
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void completeOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Find the specifed order in the database and mark that order as complete in the database.
		 *
		 */

		String query = "update ordered set OrderStatus = 'Complete' where OrderID = ?;";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, o.getOrderID());
		stmt.executeUpdate();

		//im not sure if order parameter needs to be updated too

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}


	public static ArrayList<Order> getOrders(boolean openOnly) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Return an arraylist of all of the orders.
		 * 	openOnly == true => only return a list of open (ie orders that have not been marked as completed)
		 *           == false => return a list of all the orders in the database
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 *
		 * Don't forget to order the data coming from the database appropriately.
		 *
		 */
		String query = "select OrderID, OrderCustomerID, OrderType, OrderDate, OrderPriceCustomer," +
				"OrderCostBusiness, OrderStatus from ordered";
		if(openOnly){
			query += " where OrderStatus LIKE 'Processing';";
		}
		else{
			query +=";";
		}
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		ArrayList<Order> orders = new ArrayList<>();
		while(rset.next()){
			Order newOrder = new Order(rset.getInt(1), rset.getInt(2), rset.getString(3),
					rset.getDate(4).toString(), rset.getDouble(5), rset.getDouble(6), 0);
			if(rset.getString(7).equals("Completed")){
				newOrder.setIsComplete(1);
			}
			orders.add(newOrder);
			String pizzaQuery = "select PizzaID, PizzaSize, PizzaCrustType, PizzaOrderID, PizzaState, PizzaPriceCustomer, PizzaCostBusiness " +
					"from pizza where PizzaOrderID = ?;";
			PreparedStatement pizzaStmt = conn.prepareStatement(pizzaQuery);
			pizzaStmt.setInt(1, newOrder.getOrderID());
			ResultSet pizzaRset = pizzaStmt.executeQuery();
			while(pizzaRset.next()) {
				newOrder.addPizza(new Pizza(pizzaRset.getInt(1), pizzaRset.getString(2), pizzaRset.getString(3),
						pizzaRset.getInt(4), pizzaRset.getString(5), newOrder.getDate(), pizzaRset.getDouble(6),
						pizzaRset.getDouble(7)));
			}

			String discountQuery = "select DiscountCode, DiscountName, DiscountDollarAmount, DiscountPercent from discount " +
					"join order_discount on discount.DiscountCode=order_discount.OrderDiscountDiscountCode " +
					"where OrderDiscountOrderID = ?;";
			PreparedStatement discountStmt = conn.prepareStatement(discountQuery);
			discountStmt.setInt(1, newOrder.getOrderID());
			ResultSet discountRset = discountStmt.executeQuery();
			while(discountRset.next()) {
				if(discountRset.getDouble(4) == 0) {
					newOrder.addDiscount(new Discount(discountRset.getInt(1), discountRset.getString(2),
							discountRset.getDouble(3), false));
				}
				else{
					newOrder.addDiscount(new Discount(discountRset.getInt(1), discountRset.getString(2),
							discountRset.getDouble(4), true));
				}
			}
		}


		//MAY HAVE TO GET PIZZA AND DISCOUNTS FOR ORDERS TOO


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return orders;
	}

	public static Order getLastOrder(){
		/*
		 * Query the database for the LAST order added
		 * then return an Order object for that order.
		 * NOTE...there should ALWAYS be a "last order"!
		 */

		ArrayList<Order> orders = new ArrayList<>();
		try {
			orders = getOrders(false);
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
		Collections.sort(orders, (d2, d1) -> d2.getDate().compareTo(d1.getDate()));
		return orders.get(0);
	}

	public static ArrayList<Order> getOrdersByDate(String date){

		/*
		 * Query the database for ALL the orders placed on a specific date
		 * and return a list of those orders.
		 *
		 */
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			connect_to_db();
			String query = "select OrderID, OrderCustomerID, OrderType, OrderDate, OrderPriceCustomer," +
					"OrderCostBusiness, OrderStatus from ordered where OrderDate = date;";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()){
				orders.add(new Order(rset.getInt(1), rset.getInt(2), rset.getString(3),
						rset.getString(4), rset.getDouble(5), rset.getDouble(4), 0
				));
				if(rset.getString(7).equals("Completed")){
					orders.get(orders.size()-1).setIsComplete(1);
				}
			}
			conn.close();
		}catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


		return orders;
	}

	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database for all the available discounts and
		 * return them in an arrayList of discounts.
		 *
		 */
		String query = "select DiscountCode, DiscountName, DiscountDollarAmount, DiscountPercent from discount;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		ArrayList<Discount> discounts = new ArrayList<>();
		while(rset.next()){
			discounts.add(new Discount(rset.getInt(1), rset.getString(2), rset.getDouble(3),
					false));
			if(rset.getDouble(4) != 0){
				discounts.get(discounts.size() -1 ).setAmount(rset.getDouble(4));
				discounts.get(discounts.size() -1 ).setPercent(true);
			}
		}






		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return discounts;
	}

	public static Discount findDiscountByName(String name){
		/*
		 * Query the database for a discount using it's name.
		 * If found, then return an OrderDiscount object for the discount.
		 * If it's not found....then return null
		 *
		 */

		ArrayList<Discount> discounts = new ArrayList<>();
		try {
			discounts = getDiscountList();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (discounts != null) {
			for (int i = 0; i < discounts.size(); i++) {
				if (discounts.get(i).getDiscountName() == name) {
					return discounts.get(i);
				}
			}
		}
		return null;
	}


	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the data for all the customers and return an arrayList of all the customers.
		 * Don't forget to order the data coming from the database appropriately.
		 *
		 */
		String query = "select CustomerID, CustomerFName, CustomerLName, CustomerAddress, CustomerZip," +
				"CustomerCity, CustomerState, CustomerPhone from customer;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		ArrayList<Customer> customers = new ArrayList<>();
		while(rset.next()){
			customers.add(new Customer(rset.getInt(1), rset.getString(2), rset.getString(3),
					rset.getString(5)));
			customers.get(customers.size() - 1).setAddress(rset.getString(4),
					rset.getString(6), rset.getString(7), rset.getString(5));
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return customers;
	}

	public static Customer findCustomerByPhone(String phoneNumber){
		/*
		 * Query the database for a customer using a phone number.
		 * If found, then return a Customer object for the customer.
		 * If it's not found....then return null
		 *
		 */
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			customers = getCustomerList();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (customers != null) {
			for (int i = 0; i < customers.size(); i++) {
				if (customers.get(i).getPhone() == phoneNumber) {
					return customers.get(i);
				}
			}
		}
		return null;
	}


	public static ArrayList<Topping> getToppingList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database for the aviable toppings and
		 * return an arrayList of all the available toppings.
		 * Don't forget to order the data coming from the database appropriately.
		 *
		 */
		String query = "select ToppingID, ToppingName, ToppingCostBusiness, ToppingPriceCustomer, " +
				"ToppingMinInventory, ToppingCurrInventory, TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt " +
				"from topping order by ToppingName;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		ArrayList<Topping> toppings = new ArrayList<>();
		while(rset.next()){
			toppings.add(new Topping(rset.getInt(1), rset.getString(2),
					rset.getInt(7), rset.getInt(8), rset.getInt(9),
					rset.getInt(10), rset.getDouble(3), rset.getDouble(4),
					rset.getInt(5), rset.getInt(6)));
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return toppings;
	}

	public static Topping findToppingByName(String name){
		/*
		 * Query the database for the topping using it's name.
		 * If found, then return a Topping object for the topping.
		 * If it's not found....then return null
		 *
		 */
		ArrayList<Topping> toppings = new ArrayList<>();
		try {
			toppings = getToppingList();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (toppings != null) {
			for (int i = 0; i < toppings.size(); i++) {
				if (toppings.get(i).getTopName() == name) {
					return toppings.get(i);
				}
			}
		}
		return null;
	}


	public static void addToInventory(Topping t, double quantity) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Updates the quantity of the topping in the database by the amount specified.
		 *
		 * */
		int newTotal = t.getCurINVT() + (int)quantity;
		String query = "update topping set ToppingCurrInventory = ? where ToppingID = ?;";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, newTotal);
		stmt.setInt(2, t.getTopID());
		stmt.executeUpdate();

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database fro the base customer price for that size and crust pizza.
		 *
		 */
		String query = "select BaseSize, BaseCrust, BasePrice from base_price where BaseSize = ? " +
				"and BaseCrust = ?;";
		PreparedStatement ps;
		ps = conn.prepareStatement(query);
		ps.setString(1, size);
		ps.setString(2, crust);
		ResultSet rset = ps.executeQuery();

		//DO NOT FORGET TO CLOSE YOUR CONNECTION

		if(rset.next()) {
			double retVal = rset.getDouble(3);
			conn.close();
			return retVal;
		}
		else {
			conn.close();
			return 0.0;
		}


	}

	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database fro the base business price for that size and crust pizza.
		 *
		 */

		String query = "select BaseSize, BaseCrust, BaseCost from base_price where BaseSize = ? " +
				"and BaseCrust = ?;";
		PreparedStatement ps;
		ps = conn.prepareStatement(query);
		ps.setString(1, size);
		ps.setString(2, crust);
		ResultSet rset = ps.executeQuery();

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		if (rset.next()) {
			double retVal = rset.getDouble(3);
			conn.close();
			return retVal;
		}
		else {
			conn.close();
			return 0.0;
		}
	}

	public static void printInventory() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Queries the database and prints the current topping list with quantities.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		ArrayList<Topping> toppings = getToppingList();
		System.out.println("ID\tName\tCurINVT");
		for(Topping t : toppings){
			System.out.println(t.getTopID()+"\t"+t.getTopName()+"\t"+t.getCurINVT());
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();

	}

	public static void printToppingPopReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ToppingPopularity view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		String query = "select Topping, ToppingCount from ToppingPopularity;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next()){
			System.out.println(rset.getString(1)+"\t"+rset.getInt(2));
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void printProfitByPizzaReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByPizza view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		String query = "select Size, Crust, Profit, 'Order Month'  from ProfitByPizza;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next()){
			System.out.println(rset.getString(1)+"\t"+rset.getString(2)+"\t"+
					rset.getDouble(3)+"\t"+rset.getString(4));
		}




		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void printProfitByOrderType() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByOrderType view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		String query = "select OrderType, 'Order Month', TotalOrderPrice, TotalOrderCost, Profit from ProfitByOrderType;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next()){
			System.out.println(rset.getString(1)+"\t"+rset.getString(2)+"\t"+
					rset.getDouble(3) +"\t"+ rset.getDouble(4) +"\t" + rset.getDouble(5));
		}





		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}



	public static String getCustomerName(int CustID) throws SQLException, IOException
	{
		/*
		 * This is a helper method to fetch and format the name of a customer
		 * based on a customer ID. This is an example of how to interact with
		 * your database from Java.  It's used in the model solution for this project...so the code works!
		 *
		 * OF COURSE....this code would only work in your application if the table & field names match!
		 *
		 */

		connect_to_db();

		/*
		 * an example query using a constructed string...
		 * remember, this style of query construction could be subject to sql injection attacks!
		 *
		 */
		String cname1 = "";
		String query = "Select CustomerFName, CustomerLName From customer WHERE CustomerID=" + CustID + ";";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		while(rset.next())
		{
			cname1 = rset.getString(1) + " " + rset.getString(2);
		}

		/*
		 * an example of the same query using a prepared statement...
		 *
		 */
//		String cname2 = "";
//		PreparedStatement os;
//		ResultSet rset2;
//		String query2;
//		query2 = "Select CustomerFName, CustomerLName From customer WHERE CustomerID=?;";
//		os = conn.prepareStatement(query2);
//		os.setInt(1, CustID);
//		rset2 = os.executeQuery();
//		while(rset2.next())
//		{
//			cname2 = rset2.getString("FName") + " " + rset2.getString("LName"); // note the use of field names in the getSting methods
//		}

		conn.close();
		return cname1; // OR cname2
	}

	/*
	 * The next 3 private methods help get the individual components of a SQL datetime object.
	 * You're welcome to keep them or remove them.
	 */
	private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(0,4));
	}
	private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(8, 10));
	}

	public static boolean checkDate(int year, int month, int day, String dateOfOrder)
	{
		if(getYear(dateOfOrder) > year)
			return true;
		else if(getYear(dateOfOrder) < year)
			return false;
		else
		{
			if(getMonth(dateOfOrder) > month)
				return true;
			else if(getMonth(dateOfOrder) < month)
				return false;
			else
			{
				if(getDay(dateOfOrder) >= day)
					return true;
				else
					return false;
			}
		}
	}


}