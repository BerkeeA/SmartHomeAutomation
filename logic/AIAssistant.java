package logic;

import models.User;
import models.Device;

public class AIAssistant implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    private int assistantID;
    private String name;

    public AIAssistant(int assistantID,String name){
        this.assistantID=assistantID;
        this.name=name;
    }

    public boolean authenticateVoice(User u){
        System.out.println("Authenticating voice for: " + u.getName());
        return true; 
    }

    public void processVoiceCommand(String cmd){
        System.out.println("Processing command: " + cmd);
    }

    public void executeAction(Device d){
        System.out.println("Executing action on device: " + d.getName());
        d.turnOn();
    }

    public void updateDatabase(Device d){
        System.out.println("Database updated for: " + d.getName());
    }
}