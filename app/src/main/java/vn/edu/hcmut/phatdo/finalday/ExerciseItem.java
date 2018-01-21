package vn.edu.hcmut.phatdo.finalday;

import java.io.Serializable;

/**
 * Created by peank on 20/01/2018.
 */

public class ExerciseItem implements Serializable{
    //Attributes
    protected int _avatar;
    protected int _video;
    protected String _gender;
    protected String _name;
    protected String _description;
    protected int _age;
    protected int score;
    //Constructor
    public ExerciseItem(int avatar,int video,String gender, String name, String description, int age,int score){
        setAvatar(avatar);
        setAge(age);
        setName(name);
        setDescription(description);
        setVideo(video);
        setGender(gender);
        setScore(score);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setVideo(int video){
        this._video = video;
    }
    public int getVideo(){
        return this._video;
    }
    public void setGender(String gender){
        if(!gender.equals("")){
            this._gender = gender;
        }
    }
    public String getGender(){
        return this._gender;
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

}
