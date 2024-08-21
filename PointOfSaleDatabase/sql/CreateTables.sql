create schema PizzasRUs;
use PizzasRUs;
CREATE TABLE customer (
	CustomerID		INTEGER PRIMARY KEY AUTO_INCREMENT,
	CustomerFName	VARCHAR(30) NOT NULL,
	CustomerLName	VARCHAR(30) NOT NULL,
	CustomerAddress	VARCHAR(50),
	CustomerZip		CHAR(9),
	CustomerCity	VARCHAR(50),
	CustomerState	CHAR(2),
	CustomerPhone	CHAR(10)
);
CREATE TABLE ordered (
	OrderID		INTEGER PRIMARY KEY AUTO_INCREMENT,
	OrderCustomerID		INTEGER,
	OrderType		ENUM('DineIn', 'Takeout', 'Delivery'),
	OrderCostBusiness	DECIMAL(5, 2) NOT NULL,
	OrderPriceCustomer	DECIMAL(5,2) NOT NULL,
	OrderStatus		ENUM('Processing', 'Completed'),
	OrderDate		DATETIME,
	FOREIGN KEY (OrderCustomerID) REFERENCES customer(CustomerID)
);

CREATE TABLE dine_in (
	DineInOrderID		INTEGER PRIMARY KEY,
	DineInTableNum		INTEGER NOT NULL,
	FOREIGN KEY (DineInOrderID) references ordered (OrderID)
);

CREATE TABLE takeout (
TakeoutOrderID		INTEGER PRIMARY KEY,
TakeoutCustomerID		INTEGER NOT NULL,
FOREIGN KEY (TakeoutOrderID) references ordered(OrderID),
FOREIGN KEY (TakeoutCustomerID) references customer(CustomerID)
);

CREATE TABLE delivery (
DeliveryOrderID		INTEGER PRIMARY KEY,
DeliveryCustomerID		INTEGER NOT NULL,
FOREIGN KEY (DeliveryOrderID) references ordered(OrderID),
FOREIGN KEY (DeliveryCustomerID) references customer(CustomerID)
);

CREATE TABLE base_price (
	BasePriceID		integer primary key auto_increment,
	BaseSize		enum('Small', 'Medium', 'Large', 'XLarge') not null,
	BaseCrust		enum('Thin', 'Original', 'Pan', 'Gluten-Free') not null,
	BaseCost		decimal(4,2) not null,
	BasePrice		decimal(4,2) not null
);

CREATE TABLE pizza (
	PizzaID		integer primary key auto_increment,
	PizzaOrderID		integer not null,
	PizzaCrustType	enum('Thin', 'Original', 'Pan', 'Gluten-Free') not null,
	PizzaSize		enum('Small', 'Medium', 'Large', 'XLarge') not null,
	PizzaState		enum('Processing', 'Completed'),
	PizzaBasePriceID	integer,
    PizzaPriceCustomer		decimal(4,2),
    PizzaCostBusiness		decimal(4,2),
	FOREIGN KEY (PizzaBasePriceID) REFERENCES base_price(BasePriceID),
    FOREIGN KEY (PizzaOrderID) REFERENCES ordered(OrderID)
);

CREATE TABLE discount (
	DiscountCode		INTEGER PRIMARY KEY auto_increment,
	DiscountName	VARCHAR(30) NOT NULL,
	DiscountDollarAmount	DECIMAL(4, 2) UNSIGNED,
	DiscountPercent		DECIMAL(4, 2) UNSIGNED
);

CREATE TABLE pizza_discount (
	PizzaDiscountDiscountCode		INTEGER,
	PizzaDiscountPizzaID		INTEGER,
	PRIMARY KEY (PizzaDiscountDiscountCode, PizzaDiscountPizzaID),
	FOREIGN KEY (PizzaDiscountDiscountCode) REFERENCES discount(DiscountCode),
	FOREIGN KEY (PizzaDiscountPizzaID) REFERENCES pizza (PizzaID)
);

CREATE TABLE order_discount (
	OrderDiscountDiscountCode		INTEGER,
	OrderDiscountOrderID		INTEGER,
	PRIMARY KEY (OrderDiscountDiscountCode, OrderDiscountOrderID),
	FOREIGN KEY (OrderDiscountDiscountCode) REFERENCES discount(DiscountCode),
	FOREIGN KEY (OrderDiscountOrderID) REFERENCES ordered (OrderID)
);

Create table topping (
	ToppingID			integer primary key auto_increment,
	ToppingName			varchar(30) not null,
	ToppingCostBusiness		decimal(4,2) not null,
	ToppingPriceCustomer	decimal(4,2) not null,
	ToppingMinInventory		integer unsigned not null,
	ToppingCurrInventory		integer unsigned not null,
	TopSmallAmt			integer unsigned not null,
	TopMedAmt			integer unsigned not null,
	TopLargeAmt			integer unsigned not null,
	TopXLargeAmt		integer unsigned not null
);

Create table pizza_topping (
	PizzaToppingToppingID		integer,
	PizzaToppingPizzaID		integer,
	foreign key (PizzaToppingToppingID) references topping(ToppingID),
	foreign key (PizzaToppingPizzaID) references pizza(PizzaID)
);



