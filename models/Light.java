package models;

public class Light extends Device{
    private static final long serialVersionUID = 1L;
    
    private int brightness;

    public Light(int id,String name,String roomName){
        super(id,name,roomName);
        this.brightness=100;
    }

    @Override
    public void turnOn(){
        this.status = true;
        System.out.println(name + " in " + roomName + " is now ON.");
    }

    public void adjustBrightness(int value){
        this.brightness=value;
        System.out.println(name + " brightness adjusted to " + value + ".");
    }

    public int getBrightness(){
        return brightness;
    }
}