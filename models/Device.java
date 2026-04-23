package models;

public abstract class Device{
    protected int id;
    protected String name;
    protected boolean status;
    protected String roomName;

    public Device(int id,String name,String roomName){
        this.id=id;
        this.name=name;
        this.roomName=roomName;
        this.status=false;
    }

    public void turnOn(){
        this.status=true;
        System.out.println(name + " in " + roomName + " is now ON.");
    }

    public void turnOff(){
        this.status=false;
        System.out.println(name + " in " + roomName + " is now OFF.");
    }

    public int getId(){ return id;}
    public String getName(){ return name;}
    public boolean getStatus(){ return status;}
    public String getRoomName(){ return roomName;}
}