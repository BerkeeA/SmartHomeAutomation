import models.*;
import logic.*;

public class Main{
    public static void main(String[] args){
        System.out.println("==========================================");
        System.out.println("   SMART HOME SYSTEM ACTIVATED          ");
        System.out.println("==========================================");

        Admin admin = new Admin(1, "Berke", "pass123", "Full_Access");
        System.out.println("> Logged in as Admin: " + admin.getName());

        Light livingRoomLight=new Light(101, "Living Room Light", "Living Room");
        Thermostat ac=new Thermostat(102, "Central AC", "Hallway");
        Door mainDoor=new Door(103, "Main Entrance", "Entrance");

        admin.addDevice(livingRoomLight);
        admin.addDevice(ac);
        admin.addDevice(mainDoor);

        AIAssistant jarvis = new AIAssistant(1, "Jarvis");
        VoiceInteractionModule voiceModule = new VoiceInteractionModule("API-999", "Living Room");

        System.out.println("\n--- STARTING VOICE COMMAND TEST ---");

        String command="Turn on Living Room Light";
        voiceModule.receiveVoiceInput(command);
        jarvis.processVoiceCommand(command);
        jarvis.authenticateVoice(admin);
        
        jarvis.executeAction(livingRoomLight);
        jarvis.updateDatabase(livingRoomLight);

        System.out.println("\n--- CURRENT SYSTEM STATUS ---");
        admin.monitorStatus(livingRoomLight);
        admin.monitorStatus(ac);
        admin.monitorStatus(mainDoor);

        System.out.println("\n--- ADMIN MANAGEMENT TEST ---");
        admin.manageAI();

        System.out.println("\n==========================================");
        System.out.println("   SYSTEM TEST COMPLETED SUCCESSFULLY   ");
        System.out.println("==========================================");
    }
}