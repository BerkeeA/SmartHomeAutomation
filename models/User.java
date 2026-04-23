package models;

public class User implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    private int userID;
    private String name;
    private String password;
    private byte[] voiceSignature; // Represents the 'Blob' type from the UML diagram in Java

    public User(int userID,String name,String password){
        this.userID=userID;
        this.name=name;
        this.password=password;
        this.voiceSignature=null;
    }

    public boolean login(String u,String p){
        if (this.name.equals(u) && this.password.equals(p)){
            System.out.println("Login successful.");
            return true;
        }
        return false;
    }

    public void monitorStatus(Device device){
        String state=device.getStatus() ? "ON" : "OFF";
        System.out.println("Device: " + device.getName() + " | Status: " + state);
    }

    public void registerVoice(){
        System.out.println("Registering voice signature for " + name);
        this.voiceSignature=new byte[0]; 
    }

    public int getUserID(){
        return userID;
    }

    public String getName(){
        return name;
    }
}