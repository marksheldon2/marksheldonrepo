use PizzasRUs;
drop view if exists ToppingPopularity;
Create view ToppingPopularity as
	Select ToppingName as Topping, count(PizzaToppingToppingID) as ToppingCount
	From pizza_topping right join topping on pizza_topping.PizzaToppingToppingID = topping.ToppingID
	Group by PizzaToppingToppingID
	Order by count(PizzaToppingToppingID) desc;
    
drop view  if exists ProfitByPizza;
Create view ProfitByPizza as
	Select PizzaSize as Size, PizzaCrustType as Crust, sum(PizzaPriceCustomer - PizzaCostBusiness) as Profit, DATE_FORMAT(OrderDate, '%c/%Y') as 'Order Month'
	From ordered
    join pizza on pizza.PizzaOrderID = ordered.OrderID
    group by PizzaSize, PizzaCrustType
	Order by (sum(PizzaPriceCustomer - PizzaCostBusiness) ) desc;

drop view  if exists ProfitByOrderType;
create view ProfitByOrderType as 
	select OrderType, DATE_FORMAT(OrderDate, '%c/%Y') as 'Order Month', sum(OrderPriceCustomer) as TotalOrderPrice, 
    sum(OrderCostBusiness) as TotalOrderCost, sum(OrderPriceCustomer - OrderCostBusiness) as Profit
    from ordered
    group by OrderType, DATE_FORMAT(OrderDate, '%c/%Y')
union
	select '', 'Grand Total', sum(OrderPriceCustomer) as TotalOrderPrice, 
    sum(OrderCostBusiness) as TotalOrderCost, sum(OrderPriceCustomer - OrderCostBusiness) as Profit
    from ordered;