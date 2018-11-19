package se.yrgo.als.domain;
import javax.persistence.*;


@Entity
@Table(name = "users", schema = "chat-got-your-tongue")
public class Users {

    public Users(){}

    //TODO
    //Make another constructor
    public Users(String userName, String firstName, String lastName, String userEmail) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="userId")
    private int userId;

    @Column(name="userName")
    private String userName;

    @Column (name="firstName")
    private String firstName;

    @Column (name="lastName")
    private String lastName;

    @Column (name="userEmail")
    private String userEmail;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
