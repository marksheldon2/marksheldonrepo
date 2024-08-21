use PizzasRUs;
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Pepperoni', 0.20, 1.25, 50, 100, 2, 2.75, 3.5, 4.5);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Sausage', 0.15, 1.25, 50, 100, 2.5, 3, 3.5, 4.25);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Ham', 0.15, 1.5, 25, 78, 2, 2.5, 3.25, 4);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Chicken', 0.25, 1.75, 25, 56, 1.5, 2, 2.25, 3);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Green Pepper', 0.02, 0.50, 25, 79, 1, 1.5, 2, 2.5);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Onion', 0.02, 0.50, 25, 85, 1, 1.5, 2, 2.75);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Roma Tomato', 0.03, 0.75, 10, 86, 2, 3, 3.5, 4.5);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Mushrooms', 0.10, 0.75, 50, 52 ,1.5, 2, 2.5, 3);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Black Olives', 0.10, 0.60, 25, 39, 0.75, 1, 1.5, 2);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Pineapple', 0.25, 1.00, 0, 15, 1, 1.25, 1.75, 2);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Jalapenos', 0.05, 0.50, 0, 64, 0.5, 0.75, 1.25, 1.75);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Banana Peppers', 0.05, 0.50, 0, 36, 0.6, 1, 1.3, 1.75);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Regular Cheese', 0.12, 0.50, 50, 250, 2, 3.5, 5, 7);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Four Cheese Blend', 0.15, 1.00, 25, 150, 2, 3.5, 5, 7);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Feta Cheese', 0.18, 1.50, 0, 75, 1.75, 3, 4, 5.5);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Goat Cheese', 0.2, 1.50, 0, 54, 1.6, 2.75, 4, 5.5);
Insert into topping (ToppingName, ToppingCostBusiness, ToppingPriceCustomer, ToppingMinInventory, ToppingCurrInventory,
	TopSmallAmt, TopMedAmt, TopLargeAmt, TopXLargeAmt)
Values ('Bacon', 0.25, 1.50, 0, 89, 1, 1.5, 2, 3);

Insert into discount (DiscountName, DiscountDollarAmount, DiscountPercent)
Values('Employee', null, 0.15);
Insert into discount (DiscountName, DiscountDollarAmount, DiscountPercent)
Values('Lunch Special Medium', 1.00, null);
Insert into discount (DiscountName, DiscountDollarAmount, DiscountPercent)
Values('Lunch Special Large', 2.00, null);
Insert into discount (DiscountName, DiscountDollarAmount, DiscountPercent)
Values('Specialty Pizza', 1.50, null);
Insert into discount (DiscountName, DiscountDollarAmount, DiscountPercent)
Values('Happy Hour', null, 0.10);
Insert into discount (DiscountName, DiscountDollarAmount, DiscountPercent)
Values('Gameday Special', null, 0.20);

Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Small', 'Thin', 3, 0.5);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Small', 'Original', 3, 0.75);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Small', 'Pan', 3.5, 1);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Small', 'Gluten-Free', 4, 2);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Medium', 'Thin', 5, 1);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Medium', 'Original', 5, 1.5);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Medium', 'Pan', 6, 2.25);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Medium', 'Gluten-Free', 6.25, 3);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Large', 'Thin', 8, 1.25);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Large', 'Original', 8, 2);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Large', 'Pan', 9, 3);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('Large', 'Gluten-Free', 9.5, 4);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('XLarge', 'Thin', 10, 2);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('XLarge', 'Original', 10, 3);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('XLarge', 'Pan', 11.5, 4.5);
Insert into base_price (baseSize, baseCrust, basePrice, baseCost)
values('XLarge', 'Gluten-Free', 12.5, 6);

-- order 1
Insert into ordered (OrderDate, OrderType, OrderCostBusiness, OrderPriceCustomer, OrderStatus)
values('2023-03-05 12:03:00', 'DineIn', 3.68, 18.75, 'Completed');
Insert into dine_in
values (1, 21);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (1, 'Thin', 'Large', 3.68, 18.75, 'Completed', 9);
insert into pizza_discount (PizzaDiscountDiscountCode, PizzaDiscountPizzaID)
value (3, 1);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(1, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(1, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(1, 1);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(1, 2);

-- order 2
Insert into ordered (OrderDate, OrderType, OrderPriceCustomer, OrderCostBusiness, OrderStatus)
values('2023-04-03 12:05:00', 'DineIn', 17.28, 4.63, 'Completed');
Insert into dine_in
values (2, 4);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (2, 'Pan', 'Medium', 3.23, 10.35,'Completed', 7);
insert into pizza_discount (PizzaDiscountDiscountCode, PizzaDiscountPizzaID)
value (2, 2);
insert into pizza_discount (PizzaDiscountDiscountCode, PizzaDiscountPizzaID)
value (4, 2);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(2, 15);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(2, 9);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(2, 7);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(2, 8);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(2, 12);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (2, 'Original', 'Small', 1.40, 6.93, 'Completed', 2);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(3, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(3, 4);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(3, 12);


-- order 3
insert into customer(CustomerFName, CustomerLName, CustomerPhone)
values('Andrew', 'Wilkes-Krie', '8642545861');
Insert into ordered (OrderDate, OrderType, OrderPriceCustomer, OrderCostBusiness, OrderStatus, OrderCustomerID)
values('2023-03-03 21:30:00', 'Takeout', 89.28, 19.8, 'Completed', 1);
Insert into takeout
values (3, 1);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (3, 'Original', 'Large', 3.30, 14.88, 'Completed', 10);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(4, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(4, 1);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (3, 'Original', 'Large', 3.30, 14.88, 'Completed', 10);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(5, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(5, 1);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (3, 'Original', 'Large', 3.30, 14.88, 'Completed', 10);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(6, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(6, 1);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (3, 'Original', 'Large', 3.30, 14.88, 'Completed', 10);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(7, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(7, 1);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (3, 'Original', 'Large', 3.30, 14.88, 'Completed', 10);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(8, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(8, 1);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (3, 'Original', 'Large', 3.30, 14.88, 'Completed', 10);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(9, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(9, 1);

-- order 4
update customer
set CustomerAddress = '115 Party Blvd', CustomerCity = 'Anderson', CustomerState = 'SC', CustomerZip = 29621
where CustomerID = 1;
Insert into ordered (OrderDate, OrderType, OrderPriceCustomer, OrderCostBusiness, OrderStatus, OrderCustomerID)
values('2023-04-20 19:11:00', 'Delivery', 67.75, 23.62, 'Completed', 1);
insert into order_discount (OrderDiscountDiscountCode, OrderDiscountOrderID)
value (6, 4);
Insert into delivery
values (4, 1);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (4, 'Original', 'XLarge', 9.19, 27.94, 'Completed', 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(10, 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(10, 1);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(10, 2);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (4, 'Original', 'XLarge', 6.25, 30.0, 'Completed', 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(11, 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(11, 3);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(11, 10);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(11, 3);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(11, 10);
insert into pizza_discount (PizzaDiscountDiscountCode, PizzaDiscountPizzaID)
value (4, 10);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (4, 'Original', 'XLarge', 8.18, 26.75, 'Completed', 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(12, 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(12, 4);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(12, 17);

-- order 5
insert into customer(CustomerFName, CustomerLName, CustomerPhone)
values('Matt', 'Engers', '8644749953');
Insert into ordered (OrderDate, OrderType, OrderPriceCustomer, OrderCostBusiness, OrderStatus, OrderCustomerID)
values('2023-03-02 17:30:00', 'Takeout', 25.95, 7.88, 'Completed', 2);
Insert into takeout
values (5, 2);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (5, 'Gluten-Free', 'XLarge', 7.88, 25.95, 'Completed', 16);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(13, 16);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(13, 5);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(13, 6);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(13, 7);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(13, 8);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(13, 9);
insert into pizza_discount (PizzaDiscountDiscountCode, PizzaDiscountPizzaID)
value (4, 13);


-- order 6
insert into customer(CustomerFName, CustomerLName, CustomerPhone, CustomerAddress, CustomerCity, CustomerState, CustomerZip)
values('Frank', 'Turner', '8642328944', '6745 Wessex St', 'Anderson', 'SC', '29621');
Insert into ordered (OrderDate, OrderType, OrderPriceCustomer, OrderCostBusiness, OrderStatus, OrderCustomerID)
values('2023-03-02 18:17:00', 'Delivery', 25.81, 4.24, 'Completed', 3);
Insert into delivery
values (6, 3);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (6, 'Thin', 'Large', 4.24, 25.81, 'Completed', 9);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(14, 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(14, 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(14, 4);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(14, 5);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(14, 6);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(14, 8);

-- order 7
insert into customer(CustomerFName, CustomerLName, CustomerPhone, CustomerAddress, CustomerCity, CustomerState, CustomerZip)
values('Milo', 'Auckerman', '8648785679', '8879 Suburban Home', 'Anderson', 'SC', '29621');
Insert into ordered (OrderDate, OrderType, OrderPriceCustomer, OrderCostBusiness, OrderStatus, OrderCustomerID)
values('2023-04-13 20:32:00', 'Delivery', 31.66, 6.00, 'Completed', 4);
insert into order_discount (OrderDiscountDiscountCode, OrderDiscountOrderID)
value (1, 7);
Insert into delivery
values (7, 3);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (7, 'Thin', 'Large', 2.75, 18, 'Completed', 9);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(15, 14);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(15, 14);
insert into pizza(PizzaOrderID, PizzaCrustType, PizzaSize, PizzaCostBusiness, PizzaPriceCustomer, PizzaState, PizzaBasePriceID)
values (7, 'Thin', 'Large', 3.25, 19.25, 'Completed', 9);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(16, 13);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(16, 1);
insert into pizza_topping (PizzaToppingPizzaID, PizzaToppingToppingID)
values(16, 1);



