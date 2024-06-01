package Pojos;

public class User {

    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}
