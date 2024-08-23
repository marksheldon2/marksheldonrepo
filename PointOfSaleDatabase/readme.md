# Pizzeria Point of Sale Database

## Project Overview
This Java project walks through the entire development process of a database from converting a business problem into requirements through to full implementation. Completed in collaboration with a partner, we evaluated the needs of a hypothetical pizzeria in need of a new database to track their inventory generating a list of possible tables. We then mapped the relationship among our tables, normalizing, adding bridge tables, and planning subtypes where necessary. To guide development, we documented our plan in a Crow's Foot diagram (`databaseDiagram.pdf`) to reference and update throughout development.

Once our design was complete, we generated an RDS instance in AWS, connecting it to MySQL Workbench. We then created utility sql files (ex: `sql/DropTables.sql`) in MySQL Workbench to quickly view/delete/create our database.

Only with all of the planning complete did we start developing the java program to implement each table class and the logic to tie them together. 

## Technical Overview
- Written in Java and SQL
- Hosted as an AWS RDS instance
- SQL managed with MySQL Workbench
- Contains multiple bridge tables and subtypes