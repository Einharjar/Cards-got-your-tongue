package als.domain;
import java.util.List;

import javax.persistence.*;

//import org.hibernate.mapping.List;
//import org.hibernate.mapping.Set;


@Entity
@Table(name = "userDetails")
public class PersonDetails {



	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="personId")
    private int personId;
    
    @Column (name="firstName")
    private String firstName;
    
    @Column (name="lastName")
    private String lastName;
    
    @Column (name="userEmail")
    private String userEmail;
    
    @OneToMany(targetEntity=Users.class, mappedBy="details")
//    @JoinColumn(name = "userId")
//@ElementCollection()
//    public List<Users> getOrders() { return users; }
//    public void setOrders(List<Users> users) { this.users = users; }
    private List<Users> users;


  
    public PersonDetails(int person, String firstName, String lastName, String userEmail
    		) {
		super();
		this.personId = person;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
	}
    public PersonDetails() {
		super();
	}
    
    
    
	public int getPerson() {
		return personId;
	}
    
	public List<Users> getUser() {
		return users;
	}

	public void setUser(List<Users> user) {
		this.users = user;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
