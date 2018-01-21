package vn.edu.hcmut.phatdo.finalday;

/**
 * Created by peank on 20/01/2018.
 */

public class Item {
    //Attributes
    protected int _avatar;
    protected String _name;
    protected String _description;
    protected int _age;
    protected  String _email;
    //Constructor
    public Item(int avatar, String name, String description, int age,String email){
        setAvatar(avatar);
        setAge(age);
        setName(name);
        setDescription(description);
        setEmail(email);
    }
    //Get Methods
    public int getAvatar(){
        return this._avatar;
    }
    public int getAge(){
        return this._age;
    }

    public String getName(){
        return this._name;
    }

    public String getDescription(){
        return this._description;
    }
    //Set methods
    public void setName(String name){
        if(!name.equals(""))
            this._name = name;
    }

    public void setAvatar(int avatar){
        this._avatar = avatar;
    }

    public void setAge(int age){
        if(age>0&&age<110)
            this._age = age;
    }

    public void setDescription(String desc){
        if(!desc.equals(""))
            this._description = desc;
    }

    public String getEmail(){
        return this._email;
    }
    public void setEmail(String email){
        if(email!=""){
            this._email=email;
        }
    }

}
