package logic;

public class VoiceInteractionModule implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    private String apiID;
    private String roomName;
    private boolean isActive;

    public VoiceInteractionModule(String apiID, String roomName){
        this.apiID=apiID;
        this.roomName=roomName;
        this.isActive=true;
    }

    public void receiveVoiceInput(String text){
        System.out.println("Voice input received: " + text);
        sendToAssistant(text);
    }

    public void sendToAssistant(String text){
        System.out.println("Sending command to AI via API: " + apiID);
    }
}