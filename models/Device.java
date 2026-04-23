package models;

public abstract class Device{
    protected int id;
    protected String name;
    protected String status;
    protected String roomName;

    public Device(int id, String name, String roomName){
        this.id=id;
        this.name=name;
        this.roomName=roomName;
        this.status="OFF";
    }

    public void turnOn(){
        this.status="ON";
        System.out.println(name + " in " + roomName + " is now ON.");
    }

    public void turnOff(){
        this.status="OFF";
        System.out.println(name + " in " + roomName + " is now OFF.");
    }

    public int getId(){ return id;}
    public String getName(){ return name;}
    public String getStatus(){ return status;}
    public String getRoomName(){ return roomName;}
}