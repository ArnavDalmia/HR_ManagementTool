/* class Employee 
        this class is used as a way to create a person or employee, each employee has accessible details
*/
public class Employee {
    /* Field Variables
    type : String variable either "Employee" or "Manager"
    name : String employee name
    age : int variable for employee age
    ID : int variable for employee ID
    salary : int variable for employee salary
	 */
    private String type; // initializing field variables as private so they can only be accessed and edited through setters/getters
    private String name;
    private int age;
    private int ID;
    private int salary;

    /* custructor Employee 
            creating a Employee object requires 5 parameters
	 * Parameters:
            type : String variable either "Employee" or "Manager"
            name : String employee name
            age : int variable for employee age
            ID : int variable for employee ID
            salary : int variable for employee salary
	 * Return Value
	 * 		none
	 * Local Variables:
	 * 		none
	 */
    public Employee(String type, String name,int age,int ID,int salary){
        this.type = type;//sets the field variables to the data from the parameters
        this.name = name;
        this.age = age;
        this.ID = ID;
        this.salary = salary;
    }
    
        /* method getName 
        Returns the name of the Employee object
	 * Parameters:
            none
	 * Return Value
            name : String employee name
	 * Local Variables:
            none
	 */
    public String getName(){
        return name; // returns name
    }
        /* method getAge 
        Returns the age of the Employee object
	 * Parameters:
            none
	 * Return Value
            age : int employee age
	 * Local Variables:
            none
	 */
    public int getAge(){
        return age; //returns age
    }
        /* method getID 
        Returns the ID of the Employee object
	 * Parameters:
            none
	 * Return Value
            ID : int employee ID
	 * Local Variables:
            none
	 */
    public int getID(){
        return ID; //returns ID
    }
        /* method getSalary 
        Returns the salary of the Employee object
	 * Parameters:
            none
	 * Return Value
            salary : int employee salary
	 * Local Variables:
            none
	 */
    public int getSalary(){
        return salary; //returns salary
    }
        /* method getType 
        Returns the type of the Employee object
	 * Parameters:
            none
	 * Return Value
            type : String employee type
	 * Local Variables:
            none
	 */
    public String getType(){
        return type; //returns type
    }
    
    /* method setSalary 
        sets the salary of the Employee object
	 * Parameters:
            salary : int employee salary
	 * Return Value
            none
	 * Local Variables:
            none
	 */
    public void setSalary(int salary){
        this.salary = salary;//sets salary to new value
    }
    /* method setType 
        sets the type of the Employee object
	 * Parameters:
            type : String employee type
	 * Return Value
            none
	 * Local Variables:
            none
	 */
    public void setType(String type){
        this.type = type;//sets type to new value
    }

    
    /* method printDetailsSimple 
            prints some details of the Employee object
	 * Parameters:
            none
	 * Return Value
	 * 		none
	 * Local Variables:
	 * 		type : String variable either "Employee" or "Manager"
            name : String employee name
            ID : int variable for employee ID
	 */
    public void printDetailsSimple(){
        System.out.print("Employee Status     :  " + type); //print line containing simple details
        System.out.print(",  Employee ID   :  " + ID);
        System.out.println(",  Employee Name   :  " + name);
    }

    /* method printDetails 
            prints all details of the Employee object
	 * Parameters:
            none
	 * Return Value
	 * 		none
	 * Local Variables:
	 * 		type : String variable either "Employee" or "Manager"
            name : String employee name
            age : int variable for employee age
            ID : int variable for employee ID
            salary : int variable for employee salary
	 */
    public void printDetails() {
        System.out.print("Employee Status     :  " + type); //print line containing all details
        System.out.print(",  Employee ID   :  " + ID);
        System.out.print(",  Employee Name   :  " + name);
        System.out.print(",  Employee Age    :  " + age);
        System.out.println(",  Employee Salary :  " + salary);
      }

}
