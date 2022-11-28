--Query 1
DROP PROCEDURE IF EXISTS New_Employee
 
GO
CREATE PROCEDURE New_Employee
    @type VARCHAR(255),
    @empname VARCHAR(255),
    @address VARCHAR(255),
    @salary INTEGER,
    @technical_position VARCHAR(255),
    @degree VARCHAR(255),
    @product_type VARCHAR(255),
    @max_num_products_per_day INTEGER
 
AS
BEGIN
    INSERT INTO Employee VALUES (@empname, @address, @salary)
 
    IF (@type = 'Technical_Staff')
    BEGIN
        INSERT INTO Technical_Staff VALUES (@empname, @technical_position)
        INSERT INTO Technical_Staff VALUES (@empname, @degree)
    END
 
    ELSE IF (@type = 'Quality_Controller')
    BEGIN
        INSERT INTO Quality_Controller VALUES (@empname, @product_type)
    END
 
    ELSE IF (@type = 'Worker')
    BEGIN
        INSERT INTO Worker VALUES (@empname, @max_num_products_per_day)
    END
 
    ELSE
    BEGIN
        PRINT 'Enter a Employee type (Technical_Staff/Quality_Controller/Worker)'
    END
END


--Query 2
DROP PROCEDURE IF EXISTS New_Product
 
GO
CREATE PROCEDURE New_Product
    @product_ID INTEGER,
    @production_date DATE,
    @production_time INTEGER,
    @tech_name VARCHAR(255),
    @qc_name VARCHAR(255),
    @w_name VARCHAR(255),
    @product_type VARCHAR(255),
    @size VARCHAR(255),
    @software_name VARCHAR(255),
    @color VARCHAR(255),
    @weight VARCHAR(255),
    @repair VARCHAR(255),
    @repair_date DATE,
    @reasoning VARCHAR(255)
 
AS
BEGIN
    INSERT INTO Product VALUES (@product_ID, @production_date, @tech_name, @qc_name, @w_name,@production_time)
 
    IF (@product_type = 'Product1')
    BEGIN
        INSERT INTO Product_1 VALUES (@product_ID, @size, @software_name)
    END
 
    ELSE IF (@product_type = 'Product2')
    BEGIN
        INSERT INTO Product_2 VALUES (@product_ID, @size, @color)
    END
 
    ELSE IF (@product_type = 'Product3')
    BEGIN
        INSERT INTO Product_3 VALUES (@product_ID, @size, @weight)
    END
 
    IF (@repair = 'YES')
    BEGIN
        INSERT INTO Repair VALUES (@product_ID, @repair_date, @reasoning)
    END
END

--Query 3
DROP PROCEDURE IF EXISTS New_Customer
 
GO
CREATE PROCEDURE New_Customer
    @cname VARCHAR(255),
    @address VARCHAR(255),
    @product_ID INTEGER
 
AS
BEGIN
    INSERT INTO Customer VALUES (@cname, @address)
    INSERT INTO Purchase VALUES (@product_ID, @cname)
END
 

--Query 4
DROP PROCEDURE IF EXISTS New_Account
 
GO
CREATE PROCEDURE New_Account
    @accnum INTEGER,
    @product_ID INTEGER,
    @product_type VARCHAR(255),
    @date_established DATE,
    @cost INTEGER
 
AS
BEGIN
    IF(@product_type = 'Product1')
    BEGIN
        INSERT INTO Account VALUES (@accnum, @product_ID, @product_type, @date_established, @cost)
    END
    IF(@product_type = 'Product2')
    BEGIN
        INSERT INTO Account VALUES (@accnum, @product_ID, @product_type, @date_established, @cost)
    END
    IF(@product_type = 'Product3')
    BEGIN
        INSERT INTO Account VALUES (@accnum, @product_ID, @product_type, @date_established, @cost)
    END
END
 
--Query 5
DROP PROCEDURE IF EXISTS New_Complaint
 
GO
CREATE PROCEDURE New_Complaint
    @complaint_ID INTEGER,
    @product_ID INTEGER,
    @cname VARCHAR(255),
    @date DATE,
    @description VARCHAR(255),
    @treatment_expected VARCHAR(255)
 
AS
BEGIN  
    INSERT INTO Complaint VALUES (@complaint_ID, @product_ID, @cname, @date, @description, @treatment_expected)  
END

--Query 6
DROP PROCEDURE IF EXISTS New_Accident
 
GO
CREATE PROCEDURE New_Accident
    @accident_number INTEGER,
    @product_ID INTEGER,
    @empname VARCHAR(255),
    @date DATE,
    @days_lost INTEGER
 
AS
BEGIN  
    INSERT INTO Accident VALUES (@accident_number, @product_ID, @empname, @date, @days_lost)  
END

--Query 7
DROP PROCEDURE IF EXISTS Get_Product_DateTime
 
GO
CREATE PROCEDURE Get_Product_DateTime
    @pid INTEGER
 
AS
BEGIN  
    SELECT * FROM Product WHERE product_ID = @pid 
END

--Query 8
DROP PROCEDURE IF EXISTS Get_Product_ByWorker
 
GO
CREATE PROCEDURE Get_Product_ByWorker
    @empname VARCHAR(255)
 
AS
BEGIN  
    SELECT * FROM Product WHERE Product.w_name IN (SELECT empname FROM Worker WHERE Product.w_name = @empname)
END

--Query 9
DROP PROCEDURE IF EXISTS Get_Error_QC
 
GO
CREATE PROCEDURE Get_Error_QC
    @empname VARCHAR(255)
 
AS
BEGIN  
    SELECT COUNT(*) 
    FROM Product 
    WHERE qc_name = @empname AND product_ID 
    IN (SELECT Complaint.product_ID FROM Complaint WHERE Product.product_ID = Complaint.product_ID)
END

--Query 10
DROP PROCEDURE IF EXISTS Get_Costs_Product3_RepairQC
 
GO
CREATE PROCEDURE Get_Costs_Product3_RepairQC
    @empname VARCHAR(255)
 
AS
BEGIN  
    SELECT SUM(cost) 
    FROM Account
    WHERE Account.product_ID
    IN (SELECT Product.product_ID FROM Product
    WHERE Product.qc_name = @empname AND product_ID
    IN (SELECT product_ID FROM Product_3 WHERE Product_3.product_ID = Product.product_ID
    AND product_ID IN (SELECT product_ID FROM Repair WHERE Product_3.product_ID = Repair.product_ID)))
END

--Query 11
DROP PROCEDURE IF EXISTS Get_Cname_ByColor
 
GO
CREATE PROCEDURE Get_Cname_ByColor
    @color VARCHAR(255)
 
AS
BEGIN  
    SELECT * FROM Customer
   WHERE Customer.cname IN (SELECT Purchase.cname FROM Purchase
   WHERE Purchase.product_id IN (SELECT Product_2.product_id FROM Product_2 WHERE Product_2.color = @color))
END

--Query 12
DROP PROCEDURE IF EXISTS Get_Salary
 
GO
CREATE PROCEDURE Get_Salary
    @particular_salary VARCHAR(255)
 
AS
BEGIN  
   SELECT * FROM EMPLOYEE WHERE Employee.salary > @particular_salary
END

--Query 13
DROP PROCEDURE IF EXISTS Get_WorkDaysLost_Accident_WComplaint
 
GO
CREATE PROCEDURE Get_WorkDaysLost_Accident_WComplaint
 
AS
BEGIN  
   SELECT SUM(days_lost) FROM Accident WHERE Accident.product_id IN (SELECT Complaint.product_id FROM Complaint);
END

--Query 14
DROP PROCEDURE IF EXISTS Get_YearAvgCost
 
GO
CREATE PROCEDURE Get_YearAvgCost
    @year INTEGER
 
AS
BEGIN  
   SELECT AVG(cost) FROM Account WHERE Account.product_id 
   IN (SELECT Product.product_id FROM Product WHERE YEAR(Product.production_date) = @year);
END

--Query 15
DROP PROCEDURE IF EXISTS Delete_All_RangeAccidents
 
GO
CREATE PROCEDURE Delete_All_RangeAccidents
    @start DATE,
    @end DATE
 
AS
BEGIN  
   DELETE FROM Accident
   WHERE Accident.[date] <= @end AND Accident.[date] >= @start
END
