package models;

public class Admin extends User{
    private static final long serialVersionUID = 1L;
    
    private String permissions;
    private String role="Admin";

    public Admin(int userID, String name, String password, String permissions){
        super(userID, name, password);
        this.permissions = permissions;
    }

    public void removeUser(User u){
        System.out.println("User removed: " + u.getName());
    }

    public void addUser(User u){
        System.out.println("User added: " + u.getName());
    }

    public void addDevice(Device d){
        System.out.println("Device added: " + d.getName());
    }

    public void manageAI(){
    System.out.println("--- AI Assistant Management Panel ---");
    System.out.println("Admin " + getName() + " is configuring AI settings...");
    System.out.println("Status: Permission validation logic updated.");
    System.out.println("Status: Natural Language Processing (NLP) logs reviewed.");
    System.out.println("AI management system successfully accessed.");
}
}