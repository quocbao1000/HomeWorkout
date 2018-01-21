package vn.edu.hcmut.phatdo.finalday;

import java.io.Serializable;

/**
 * Created by peank on 20/01/2018.
 */

public class NotificationItem implements Serializable {

    String friendname;
    String course;

    NotificationItem(String friendname,String course){
        this.friendname = friendname;
        this.course = course;
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
