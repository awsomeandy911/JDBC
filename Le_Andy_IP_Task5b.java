/**
 *
 * @author Andy Le
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Le_Andy_IP_Task5b 
{
    //Database credentials
    final static String HOSTNAME = "lee076-sql-server.database.windows.net";
    final static String DBNAME = "cs-dsa-4513-sql-db";
    final static String USERNAME = "user";
    final static String PASSWORD = "password";
    
    //Database connection
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;"
            + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", HOSTNAME, DBNAME, USERNAME, PASSWORD);
    
    //User input prompt
    final static String PROMPT = "\nWELCOME TO THE DATABASE SYSTEM OF MyProducts, Inc.:\n" 
            + "1)New Employee;\n" 
            + "2)New Product;\n"
            + "3)New Customer;\n" 
            + "4)New Account;\n" 
            + "5)New Complaint;\n" 
            + "6)New Accident;\n" 
            + "7)Get Date Produced & Time Spent of Product;\n" 
            + "8)Get All Products Produced by Worker;\n" 
            + "9)Get Total Number of Errors by Quality Controller;\n" 
            + "10)Get Total Costs of Repaired Product 3;\n" 
            + "11)Get All Customers Names of Products by Color;\n" 
            + "12)Get All Employees Salaries;\n" 
            + "13)Get Total Workdays Lost by Accidents w/ Complaints;\n" 
            + "14)Get Average Costs of All Products by Year;\n"
            + "15)Delete All Accounts;\n" 
            + "16)Import;\n" 
            + "17)Export;\n" 
            + "18)Exit;";

    public static void main(String[] args) throws SQLException
    {
        //Welcome message
        System.out.println("HELLO!");
        
        //Scanner for user input
        final Scanner scan = new Scanner (System.in);
        //Initialize user input choice as null
        String choice = "";
        
        //Keep asking user to select choices until choice 18, 'Exit', is selected
        while(!choice.equals("18"))
        {
            //Print out available choices
            System.out.println(PROMPT);
            //Read user selected choice
            choice = scan.next();
            scan.nextLine();
            
            //Switch between different choices
            switch(choice)
            {
                //Case 1: New Employee
                case "1":
                    
                    //User input data from Employee
                    System.out.println("Enter a Employee type (Technical_Staff/Quality_Controller/Worker): ");
                    String type = scan.nextLine();
                    
                    System.out.println("Enter Employee's name: ");
                    String empname = scan.nextLine();
                    
                    System.out.println("Enter Employee's address: ");
                    String address = scan.nextLine();
                    
                    System.out.println("Enter Employee's salary: ");
                    int salary = scan.nextInt();
                        
                        scan.nextLine();
                        //User input data for specific Employee types
                        //Technical_Staff type
                        System.out.println("Enter Techincal Staff's Position: (if N/A, press Enter)");
                        String technical_position = scan.nextLine();
                      
                        System.out.println("Enter Technical Staff's Degree(BS/MS/PhD): (if N/A, press Enter)");
                        String degree = scan.nextLine();
                        
                        //Quality_Controller type
                        System.out.println("Enter Quality Controller's Product type (Product1/Product2/Product3): (if N/A, press Enter)");
                        String product_type = scan.nextLine();
                        
                        //Worker type
                        System.out.println("Enter Worker's Max Number of Products per Day: (if N/A, Enter 0)");
                        int max_num_products_per_day = scan.nextInt();
                        
                    //Connect to database and prepare Query statement
                    System.out.println("Connecting to the database...");
                    try(final Connection connection = DriverManager.getConnection(URL))
                    {
                        try(final PreparedStatement statement = connection.prepareStatement("EXEC New_Employee @type = ?, @empname = ?, @address = ?, @salary = ?, "
                                + "@technical_position = ?, @degree = ?, @product_type = ?, @max_num_products_per_day = ?"))
                        {
                            //Populate query template with user data
                            statement.setString(1,type);
                            statement.setString(2,empname);
                            statement.setString(3,address);
                            statement.setInt(4,salary);
                            statement.setString(5,technical_position);
                            statement.setString(6,degree);
                            statement.setString(7, product_type);
                            statement.setInt(8, max_num_products_per_day);
                            
                            //Execute populated Query 
                            System.out.println("Dispatching the query...");
                            final int inserted_rows = statement.executeUpdate();
                            System.out.println(String.format("Done. %d Rows Inserted", inserted_rows));
                        }
                        //Catch SQL exception
                        catch(SQLException e)
                        {
                            System.out.println("Aww Try Again");
                            System.out.println(e);
                        }
                    }
                    //End Case 1: New Employee
                    break;
                    
                //Case 2: New Product 
                case "2":
                	
                	//User input data from Product
                    System.out.println("Enter a Product type (Product1/Product2/Product3): ");
                    String producttype = scan.nextLine();
                    
                    System.out.println("Enter Product ID: ");
                    int product_ID = scan.nextInt();
                    scan.nextLine();
                    
                    System.out.println("Enter Production Date of Product (yyyy-mm-dd): ");
                    Date production_date = Date.valueOf(scan.nextLine());
                    
                    System.out.println("Enter Production Time of Product (minutes): ");
                    int production_time = scan.nextInt();
                    
                    	scan.nextLine();
                    	//User input data for specific Employee/Product types
                    	//Technical Staff type
                        System.out.println("(YES/NO) Has Product been repaired? ");
                        String repair = scan.nextLine();                        
                        
                        String tech_name = "";
                        Date repair_date;
                        String reasoning = "";
                        
                        //User inputs repair date and reasoning if "YES" is inputed by user
                        if(repair.equals("YES"))
                        {
                        	System.out.println("Enter Technical Staff's Name who repaired the Product: ");
                            tech_name = scan.nextLine();
                            
                            System.out.println("Enter Repair Date of Product (yyyy-mm-dd): ");
                            repair_date = Date.valueOf(scan.nextLine());
                            
                            System.out.println("Enter Reasoning for the repair of Product: ");
                            reasoning = scan.nextLine();
                        }
                        //If User inputs "NO"
                        else
                        {
                        	tech_name = null;
                        	repair_date = Date.valueOf("1000-01-01");
                        	reasoning = null;
                        }
                        
                        //Quality Controller type
                        System.out.println("Enter Quality Controller's Name who checked the Product: ");
                        String qc_name = scan.nextLine();
                        
                    	//Worker type
                    	System.out.println("Enter Worker's Name who produced the Product: ");
                        String w_name = scan.nextLine();
                        
                        System.out.println("Enter size of Product (small/medium/large): ");
                        String size = scan.nextLine();
                        
                        System.out.println("Enter Software Name of Product (Only for Product1): (if N/A, press Enter)");
                        String software_name = scan.nextLine();
                        
                        System.out.println("Enter color of Product (Only for Product2): (if N/A, press Enter)");
                        String color = scan.nextLine();
                        
                        System.out.println("Enter weight of Product (in LBs; Only for Product3): (if N/A, Enter 0)");
                        int weight = scan.nextInt();
                        
                        //Connect to database and prepare Query statement
                        System.out.println("Connecting to the database...");
                        try(final Connection connection = DriverManager.getConnection(URL))
                        {
                            try(final PreparedStatement statement = connection.prepareStatement("EXEC New_Product @product_ID = ?, @production_date = ?, @tech_name = ?, @qc_name = ?, @w_name = ?,"
                                    + "@production_time = ?, @product_type = ?, @size = ?, @software_name = ?, @color = ?, @weight = ?, @repair = ?, @repair_date = ?, @reasoning = ?"))
                            {
                                //Populate query template with user data
                            	statement.setInt(1, product_ID);                          
                                statement.setDate(2,production_date);
                                statement.setString(3,tech_name);
                                statement.setString(4,qc_name);
                                statement.setString(5,w_name);
                                statement.setInt(6,production_time);
                                statement.setString(7, producttype);
                                statement.setString(8, size);
                                statement.setString(9, software_name);                          
                                statement.setString(10, color);
                                statement.setInt(11, weight);
                                statement.setString(12, repair);
                                statement.setDate(13, repair_date);
                                statement.setString(14, reasoning);
                                
                                //Execute populated Query 
                                System.out.println("Dispatching the query...");
                                final int inserted_rows = statement.executeUpdate();
                                System.out.println(String.format("Done. %d Rows Inserted", inserted_rows));
                            }
                            //Catch SQL exception
                            catch(SQLException e)
                            {
                                System.out.println("Aww Try Again");
                                System.out.println(e);
                            }
                        }
                        //End Case 2: New Product
                        break;
                        
                //Case 3: New Customer        
                case "3": 
                
                //User input data from Customer
                System.out.println("Enter Customer's name who purchased Product: ");
                String cname = scan.nextLine();
                
                System.out.println("Enter Customer's Address: ");
                String cAddress = scan.nextLine();
                
                System.out.println("Enter Product ID of Customer's purchase: ");
                int cproduct_ID = scan.nextInt();
                
                //Connect to database and prepare Query statement
                System.out.println("Connecting to the database...");
                try(final Connection connection = DriverManager.getConnection(URL))
                {
                    try(final PreparedStatement statement = connection.prepareStatement("EXEC New_Customer @cname = ?, @address = ?, @product_ID = ?"))
                    {
                        //Populate query template with user data
                        statement.setString(1,cname);
                        statement.setString(2,cAddress);
                        statement.setInt(3,cproduct_ID);
                                              
                        //Execute populated Query
                        System.out.println("Dispatching the query...");
                        final int inserted_rows = statement.executeUpdate();
                        System.out.println(String.format("Done. %d Rows Inserted", inserted_rows));
                    }
                    //Catch SQL exception
                    catch(SQLException e)
                    {
                        System.out.println("Aww Try Again");
                        System.out.println(e);
                    }
                }
                	//End Case 3: New Customer
                	break;
                
                //Case 4: New Account        
                case "4": 
                
                //User input data from Account
                System.out.println("Enter Account number: ");
                int accnum = scan.nextInt();
                
                scan.nextLine();
                System.out.println("Enter a Product type (Product1/Product2/Product3): ");
                String acctype = scan.nextLine();
                
                System.out.println("Enter Product ID for Account according to Product type: ");
                int Aproduct_ID = scan.nextInt();
                
                scan.nextLine();
                System.out.println("Enter Account Establishment Date(yyyy-mm-dd): ");
                Date date_established = Date.valueOf(scan.nextLine());
                
                System.out.println("Enter cost of product: ");
                int cost = scan.nextInt();
                
                //Connect to database and prepare Query statement
                System.out.println("Connecting to the database...");
                try(final Connection connection = DriverManager.getConnection(URL))
                {
                    try(final PreparedStatement statement = connection.prepareStatement("EXEC New_Account @accnum = ?, @product_ID = ?, @product_type = ?, @date_established = ?, @cost = ?"))
                    {
                        //Populate query template with user data
                        statement.setInt(1,accnum);
                        statement.setInt(2, Aproduct_ID);
                        statement.setString(3,acctype);
                        statement.setDate(4, date_established);
                        statement.setInt(5, cost);
                                           
                        //Execute populated Query
                        System.out.println("Dispatching the query...");
                        final int inserted_rows = statement.executeUpdate();
                        System.out.println(String.format("Done. %d Rows Inserted", inserted_rows));
                    }
                    //Catch SQL exception
                    catch(SQLException e)
                    {
                        System.out.println("Aww Try Again");
                        System.out.println(e);
                    }
                }
                	//End Case 4: New Account
                	break;
                /**
                *case "5":
                *	break;
                *case "6":
                *	break;
                *case "7":
                *	break;
                *case "8":
                *	break;
                *case "9":
                *	break;
                *case "10":
                *	break;
                *case "11":
                *	break;
                *case "12":   not enough time ;(
                *	break;
                *case "13":
                *	break;
                *case "14":
                *	break;
                *case "15":
                *	break;
                *case "16":
                *	break;
                *case "17":
                *	break;
                */
                //Case 18: Exit Application
                case "18":
                    System.out.println("Exiting Database System, Bye Bye!");
                    //End Case 18: Exit
                    break;
                    
                //In case user enters invalid option, re-prompt user again
                default:
                    System.out.println(String.format("Invalid option: %s\n" + "Try Again!", choice));
                    break;
            }
        }
        
        //Close scanner before exiting application
        scan.close();
    }
}
