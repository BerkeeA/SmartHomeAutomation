package models;

public class Thermostat extends Device{
    private static final long serialVersionUID = 1L;
    
    private int temperature;

    public Thermostat(int id, String name, String roomName){
        super(id,name,roomName);
        this.temperature=22;
    }

    public void setTemperature(int value){
        this.temperature=value;
        System.out.println(name + " temperature set to " + value + " degrees.");
    }

    public int getTemperature(){
        return temperature;
    }
}