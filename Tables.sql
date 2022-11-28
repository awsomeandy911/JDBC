CREATE TABLE Employee(
    empname VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    salary INTEGER,
    PRIMARY KEY (empname)
)


CREATE TABLE Technical_Staff(
    empname VARCHAR(255) NOT NULL,
    technical_positon VARCHAR(255),
    FOREIGN KEY (empname) REFERENCES Employee (empname)
)


CREATE TABLE Technical_Staff_Degree(
    empname VARCHAR(255) NOT NULL,
    degree VARCHAR(255) NOT NULL,
    PRIMARY KEY (degree),
    FOREIGN KEY (empname) REFERENCES Employee (empname)
)


CREATE TABLE Quality_Controller(
    empname VARCHAR(255) NOT NULL,
    product_type VARCHAR(255),
    FOREIGN KEY (empname) REFERENCES Employee (empname)
)


CREATE TABLE Worker(
    empname VARCHAR(255) NOT NULL,
    max_products_per_day VARCHAR(255),
    FOREIGN KEY (empname) REFERENCES Employee (empname)
)



CREATE TABLE Product(
    product_ID INTEGER NOT NULL UNIQUE,
    production_date DATE,
    production_time INTEGER,
    tech_name VARCHAR(255),
    qc_name VARCHAR(255) NOT NULL,
    w_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (product_ID),
    FOREIGN KEY (tech_name) REFERENCES Employee (empname),
    FOREIGN KEY (qc_name) REFERENCES Employee (empname),
    FOREIGN KEY (w_name) REFERENCES Employee (empname)
)


CREATE TABLE Product_1(
    product_ID INTEGER NOT NULL,
    size VARCHAR(255) CHECK (size = 'small' OR size = 'medium' OR size = 'large'),
    software_name VARCHAR (255),
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID)
)


CREATE TABLE Product_2(
    product_ID INTEGER NOT NULL,
    size VARCHAR(255) CHECK (size = 'small' OR size = 'medium' OR size = 'large'),
    color VARCHAR (255),
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID)
)


CREATE TABLE Product_3(
    product_ID INTEGER NOT NULL,
    size VARCHAR(255) CHECK (size = 'small' OR size = 'medium' OR size = 'large'),
    weight VARCHAR (255),
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID)
)


CREATE TABLE Account(
    accnum INTEGER NOT NULL UNIQUE,
    product_ID INTEGER NOT NULL,
    product_type VARCHAR (255),
    date_established DATE,
    cost INTEGER,
    PRIMARY KEY (accnum),
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID)
)


CREATE TABLE Customer(
    cname VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    PRIMARY KEY (cname)
)


CREATE TABLE Complaint(
    complaint_ID INTEGER NOT NULL UNIQUE,
    product_ID INTEGER NOT NULL,
    cname VARCHAR(255) NOT NULL,
    date DATE,
    description VARCHAR(255),
    treatment_expected VARCHAR(255),
    PRIMARY KEY (complaint_ID),
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID),
    FOREIGN KEY (cname) REFERENCES Customer (cname)
)


CREATE TABLE Accident(
    accident_number INTEGER NOT NULL UNIQUE,
    product_ID INTEGER NOT NULL,
    empname VARCHAR(255) NOT NULL,
    date DATE,
    days_lost INTEGER,
    PRIMARY KEY (accident_number),
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID),
    FOREIGN KEY (empname) REFERENCES Employee (empname)
)


CREATE TABLE Repair(
    product_ID INTEGER NOT NULL,
    date DATE,
    reasoning VARCHAR(50),
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID)
)


CREATE TABLE Purchase(
    product_ID INTEGER NOT NULL,
    cname VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_ID) REFERENCES Product (product_ID),
    FOREIGN KEY (cname) REFERENCES Customer (cname)
)
