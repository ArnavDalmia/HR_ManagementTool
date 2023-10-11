// This program is called the HRManagement Tool. Created by Arnav Dalmia
// Overview, calls for connection to secure sql server, and then interprets data in a more friendly way for anyone such as HR to operate, and then after operations are completed then all updates are updates directly to the sql server

/*public class Main
 * This class is the super class Main
 * It operates the entire program
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.text.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class App {
	/* method login 
        Ensures that the user has been logged in before accessing the program
	 * Parameters:
            username : String variable representhing the login credentials
			password : String variable representhing the login credentials
	 * Return Value
            none
	 * Local Variables:
            valid : Boolean variable to loop through userinputs to ensure that login credentials are correct
			userInput1 : String variable representhing the userinputted login credentials
			userInput2 : String variable representhing the userinputted login credentials
	 */
	public static void login(String username, String password){
		boolean valid;
		valid = false; //initializes valid to false, for looping

		while(!valid){
			String userInput1 = Utils.obtainInput("Enter Login Username: "); //initializes userInput1 and userInput2 and assigns them to inputs
			String userInput2 = Utils.obtainInput("Enter Password: ");

			if (userInput1.equals(username) && userInput2.equals(password)) // checks if userinputs match the username and password
				valid = true; // if true, breaks out of loop
			else	
				System.out.println("Login Failed. Username OR Password are incorrect. Try Again. "); //error message for invalid input of credentials
		}
		System.out.println("Login COmpleted. Welcome Mr Monster. "); // after verefied, welcomes user
	}
	/* method readFile 
        Reads the entries within the sql database table, and then iterates through to add to the local ArrayList people
	 */

	
	public static void readFile(ArrayList<Employee> people) { 	    			
    	String jdbcUrl = "jdbc:mysql://localhost:3306/employees";
    	String username = "root";
    	String password = "arnav123";

    		        try {
    		            // connecting to sql database
    		            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

    		            Statement statement = connection.createStatement();

    		            String sqlQuery = "SELECT * FROM EmployeeManagmentSoftware";

    		            ResultSet resultSet = statement.executeQuery(sqlQuery);

    		            // iterates through the result set which is each line or row of data. then getting the variables and adding to people
    		            while (resultSet.next()) {
    		                int id = resultSet.getInt("ID");
    		                String fullname = resultSet.getString("Fullname");
    		                String empType = resultSet.getString("EmpType");
    		                int age = resultSet.getInt("Age");
    		                int sal = resultSet.getInt("Sal");
    		            	people.add(new Employee(empType,fullname, age, id, sal)); //adds to parameter  people
    		                
    		            }
    		            // after doin updates, closes resources
    		            resultSet.close();
    		            statement.close();
    		            connection.close();
    		            
    		        } catch (SQLException e) {
    		            e.printStackTrace();
    		        }
    		    }
    		

		/* method newEmp 
        creates new employee object using user input, and returns said object
	 * Parameters:
            people : Employee ArrayList containing all employees of COOKIE CORP
	 * Return Value
            temp : Employee object that is newly created
	 * Local Variables:
            invalid : boolean variable to loop through userinputs to ensure that all parameters are accurate before returning object
			type : String variable either "Employee" or "Manager"
            name : String employee name
            age : int variable for employee age
            ID : int variable for employee ID
            salary : int variable for employee salary
			verify : boolean variable used to verify if a user inputted  new object already exists within people
	 */
    public static Employee newEmp(ArrayList<Employee> people){
		boolean invalid = false;
		Employee temp = new Employee(null, null, 0, 0, 0);//initializing invalid and temp, so that loop can commence
		while (!invalid){
			String name = Utils.obtainInput("Enter Full Name: "); // asks user for information regarding name, ID, age, salary, and type
			int ID = Utils.obtainIntegerInputLowHigh("Enter ID(unique 7 digit): ", 1000000, 10000000);
			int age = Utils.obtainIntegerInputLow("Enter age: ",0);
			int salary = Utils.obtainIntegerInputLow("Enter salary: ",0);
			String type = Utils.obtainInputSpecificAnswers("Enter Employee Status(Employee or Manager): ", "Employee", "Manager");

			boolean verify = true; // init verify for loop
			for (Employee b : people){ 
				if (b.getID() == ID){ // if any object within people has a duplicate of user inputted data
					verify = false; // sets verify to false, means that there is a problem
				} 
			}
			if (verify){ // if the duplucate doesnt exist, unique entries
					temp = new Employee(type, name, age, ID, salary);
					invalid = true;
			}
			else //error message if employee exists, re-entries loop
				System.out.println("Employee ID exists in system. Try Again!\n");
		}
        return temp; //after validating new employee, returns object

    }

		/* method writeToFile 
        writes from people into sql table connected through mysql database
        uses statement and prepared statement
	 */
    public static void writeToFile(ArrayList<Employee> people) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/employees";
        String username = "root";
        String password = "arnav123";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            
            // created a sql string statement wth ? marks to use prepared statement
            String sql = "INSERT INTO EmployeeManagmentSoftware (ID, Fullname, EmpType, Age, Sal) VALUES (?, ?, ?, ?, ?)";
            
            // created a preparedstatement
            preparedStatement = connection.prepareStatement(sql);
            
            // removes data in table, safer than fully deleting table and dealing with duplicate data
            String truncateSql = "TRUNCATE TABLE EmployeeManagmentSoftware";
            Statement truncateStatement = connection.createStatement();
            truncateStatement.executeUpdate(truncateSql);
            
            // for loop through people and using preparedStatement querie updates
            for (Employee employee : people) {
                preparedStatement.setInt(1, employee.getID());
                preparedStatement.setString(2, employee.getName());
                preparedStatement.setString(3, employee.getType());
                preparedStatement.setInt(4, employee.getAge());
                preparedStatement.setInt(5, employee.getSalary());
                preparedStatement.executeUpdate();
            }

            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

		/* method inList 
        obtains userinputted employee ID and returns the position wihtin the list where that employee exists, if it doesn't exist, stuck in loop until valid entry
	 * Parameters:
            people : Employee ArrayList containing all employees of COOKIE CORP
	 * Return Value
            index : int variable representing the index of employee in people list
	 * Local Variables:
            found : boolean variable to loop through userinputs to ensure that employee ID exists
            ID : int variable for employee ID
			e : Employee variable for easy refrencing each employee in people

	 */
	public static int inList(ArrayList<Employee> people){
		int index = -1; // initializes index to -1 so that if is still -1 then we know that employee doenst exist

		boolean found = false; // init found for loop
		while (!found){
				int ID; //init ID and obtains userinput for ID value
				ID = Utils.obtainIntegerInput("Enter ID of employee: ");

				for(int i = 0; i < people.size(); i++){ //loops through people
					Employee e = people.get(i); //assigns e to each Employee in people, for easy refrencing
					if (e.getID() == (ID)) // if the userinputted ID matches any ID within the system
						index = people.indexOf(e); // sets index to matched ID
							}
			if (index > -1) //after loop, if index hasnt changed there is no employee with that ID
					found = true; // breaks loop
			else //error message if ID doesn't exist
				System.out.println("Employee doesn't exist. Please check spelling as it is case sensistive.");
			}
		return index; //after validating ID, returns the position of Employee  within people
	}
	/* method displayAllEmployees 
		displays all employees using simple method of each Employee
	* Parameters:
    	people : Employee ArrayList containing all employees of COOKIE CORP
	* Return Value
    	none
	* Local Variables:
		e : Employee variable for easy refrencing each employee in people
	 */
	public static void displayAllEmployees(ArrayList<Employee> people){
		for(Employee e : people){ //loop through people and reference  each Emmployee object as e
			e.printDetailsSimple(); //calls printDetailSimple method of each Employee object
		}
	}
	/* method displaySpecificEmp
		displays employee detailed message using user input ID
	* Parameters:
    	people : Employee ArrayList containing all employees of COOKIE CORP
	* Return Value
    	none
	* Local Variables:
		emp : Employee variable for the userInputted Employee object
	 */
	public static void displaySpecificEmp(ArrayList<Employee> people){
		Employee emp = people.get(inList(people)); //obtains Employee object by calling inList method which returns the index of the user Inputted ID
		emp.printDetails(); //calls printDetails method of Employee, outputs detailed message containing all employee details
	}
	/* method changeSalary
		asks user for what employee they want to change salary of, and after verifying Employee, then cahnges salary to new value
	* Parameters:
    	people : Employee ArrayList containing all employees of COOKIE CORP
	* Return Value
    	none
	* Local Variables:
		index : int variable for the location of userInputted Employee object within people
		newSalary : int variable containing new user inputted salary
	 */
	public static void changeSalary(ArrayList<Employee> people){
		int index = inList(people); //obtains index of employee within people
		int newSalary; // initializes newSalary
		newSalary = Utils.obtainIntegerInputLow("Enter New Salary",0); //sets newSalary to user input
		people.get(index).setSalary(newSalary); // sets employees salaary to newSalary using setter
		System.out.println("Salary has been adjusted."); // message outputted to show confirmation
	}
	/* method promote
		asks user for what employee they want, and after verifying, promotes them
	* Parameters:
    	people : Employee ArrayList containing all employees of COOKIE CORP
	* Return Value
    	none
	* Local Variables:
		index : int variable for the location of userInputted Employee object within people
	 */
	public static void promote(ArrayList<Employee> people){
		int index = inList(people);//obtains index of employee within people
		if (people.get(index).getType().equals("Manager")) //checks if the type of employee is "Manager", and if so error message outputted
			System.out.println("Sorry this is a Manager, requires CEO permission to promote. NOT POSSIBLE TODAY");
		else{ //if they are an "Employee" type
			people.get(index).setType("Manager"); //changes type to "Manager"
			System.out.println(people.get(index).getName() + " has been changed to a Manager."); //Success message
		}
	}
	/* method demote
		asks user for what manager they want, and after verifying, demotes them
	* Parameters:
    	people : Employee ArrayList containing all employees of COOKIE CORP
	* Return Value
    	none
	* Local Variables:
		index : int variable for the location of userInputted Employee object within people
	 */
	public static void demote(ArrayList<Employee> people){
		int index = inList(people);//obtains index of employee within people
		if (people.get(index).getType().equals("Employee"))//checks if the type of employee is "Employee", and if so error message outputted
			System.out.println("Sorry this is a Employee, requires CEO permission and CEO software to fire.");
		else{//if they are an "Manager" type
			people.get(index).setType("Employee");//changes type to "Employee"
			System.out.println(people.get(index).getName() + " has been changed to a Employee.");//Success message
		}
	}
	/* method remove
		asks user for what employee they want, and after verifying, removes them from people
	* Parameters:
    	people : Employee ArrayList containing all employees of COOKIE CORP
	* Return Value
    	none
	* Local Variables:
		index : int variable for the location of userInputted Employee object within people
	 */
	public static void remove(ArrayList<Employee> people){
		int index = inList(people); //obtains index of employee within people
		people.remove(index); //removes Employee object based on their index, using index variable
		System.out.println("Successfully removed Employee"); // success message outputted
	}
	
	/* method menu
	 * This method optains the user input to the main menu
	 * Parameters:
	 * 		none
	 * Return Value
	 * 		int variable containing the user inputted menu option
	 * Local Variables:
	 * 		none
	 */
	public static int menu(){
		System.out.println("1.	Display All Employees(Managers INCLUDED)");		//displays menu options 
		System.out.println("2.	Display Specific Employee");	
		System.out.println("3.	Add Employee(Managers INCLUDED)");			//need to add verify for managers or employees				
		System.out.println("4.	Remove Employee(Managers INCLUDED)");		
		System.out.println("5.	Promote Employee(NOT MANAGERS)");								
		System.out.println("6.	Demote Manager(NOT EMPLOYEES)");		
		System.out.println("7.	Change Salary of Employees");
		System.out.println("8.	Quit");
		return Utils.obtainIntegerInputLowHigh("",1,9);				 //returns option number, and verefies it using the Utils class    
	}

	/* method main
	 * This method operates the entire program
	 * Parameters:
	 * 		none
	 * Return Value
	 * 		none
	 * Local Variables:
	 * 		username : String final variable that represents the username to login
	 * 		password : String final variable that represents the password to login
	 * 		people : Employee ArrayList that contains all Employee's from the txt database.txt
	 * 		loopQuit : boolean variable to loop through the options
	 * 		option : int variable that represents the user's option choice from the main menu
	 */
	public static void main(String[] args) { // remember to add functionality that there is always at least one employee in list
		final String username = "Cookie"; //sets credentials to login
		final String password = "Monster";
		login(username, password); //calls login method to ensure credentials to operate program

		System.out.println("Welcome to the COOKIE CORP Employee Management Software\n");
		ArrayList<Employee> people = new ArrayList<>(); //initializes people, empty
		readFile(people); //calls readFile, which reads the current entries within people and adds to people

		boolean loopQuit = false; // init loopQuit for loop
		while (!loopQuit){
			if(people.size() < 2){ // checks if there are less than 2 Employees currently in the database
				System.out.println("Database requires at least two entries. Please create new employee below:"); //tells user to make new Employees until there are at least 2
				people.add(newEmp(people)); //calls newEmp to create new Employees
			}
			else { //if there are more than 2 employees in people and database
				int option; // init option and assigns to menu return, which is the user choice
				option = menu();
				if (option == 1){ // option 1, "1.	Display All Employees(Managers INCLUDED)"
					displayAllEmployees(people); //calls displayAllEmployees method
				}
				else if (option == 2){ // option 2, "2.	Display Specific Employee"
					displaySpecificEmp(people); //calls displaySpecificEmp method
				}
				else if (option == 3){//option 3, "3.	Add Employee(Managers INCLUDED)"
					people.add(newEmp(people)); //calls newEmp method to create new Employee object, then adds to people
				}
				else if (option == 4){//option 4, "4.	Remove Employee(Managers INCLUDED)"
					remove(people); //calls remove method
				}
				else if (option == 5){//option 5, "5.   Promote Employee(NOT MANAGERS)"
					promote(people); //calls promote method
				}
				else if (option == 6){ //option 6, "6.	Demote Manager(NOT EMPLOYEES)"
					demote(people); //calls demote method
				}
				else if (option == 7){//option 7, "7.	Change Salary of Employees"
					changeSalary(people);//calls changeSalary method
				}
				else if (option == 8){//option 8, "8.   Quit"
					loopQuit = true;//changes loopQuit to true breaking the loop after full execution of commands is finished
				}
				writeToFile(people);	//after all options writeToFile is called, which takes the changes made to people, and rewrites the database on database.txt

			}
	}
		System.out.println("Thanks for using program. OPERATIONS COMPLETED"); // after program is finished message is outputted
}
}