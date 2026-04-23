package models;

public class Door extends Device{
    private boolean isLocked;

    public Door(int id,String name,String roomName){
        super(id,name,roomName);
        this.isLocked=true;
    }

    public void lock(){
        this.isLocked=true;
        System.out.println(name + " is now LOCKED.");
    }

    public void unlock(){
        this.isLocked=false;
        System.out.println(name + " is now UNLOCKED.");
    }

    public boolean isLocked(){
        return isLocked;
    }
}