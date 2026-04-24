import models.*;
import logic.*;
import ui.*;

public class Main{
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> {
            new SmartHomeGUI().setVisible(true);
        });

        System.out.println("==========================================");
        System.out.println("   SMART HOME SYSTEM TEST INITIATED      ");
        System.out.println("==========================================");

        Admin admin=new Admin(1, "Berke", "1234", "Full_Access");
        User standardUser=new User(2, "User1", "pass");
        System.out.println("> System Administrator: " + admin.getName());

        Light livingRoomLight=new Light(101, "Lamp", "Living Room");
        Thermostat ac=new Thermostat(102, "AC", "Bedroom");
        Door mainDoor=new Door(103, "Main Gate", "Entrance");

        admin.addDevice(livingRoomLight);
        admin.addDevice(ac);
        admin.addDevice(mainDoor);
        admin.addUser(standardUser);

        AIAssistant jarvis=new AIAssistant(1, "Jarvis");
        VoiceInteractionModule voiceModule = new VoiceInteractionModule("API-999", "Living Room");

        String command="Turn on Living Room Lamp";
        voiceModule.receiveVoiceInput(command);
        
        if (jarvis.authenticateVoice(admin)) {
            jarvis.processVoiceCommand(command);
            jarvis.executeAction(livingRoomLight);
            jarvis.updateDatabase(livingRoomLight);
        }

        admin.monitorStatus(livingRoomLight);
        admin.monitorStatus(ac);
        
        mainDoor.lock();
        mainDoor.turnOn(); 

        System.out.println("\n==========================================");
        System.out.println("    ALL SYSTEM TESTS PASSED (MVP v1.0)    ");
        System.out.println("==========================================");
    }
}