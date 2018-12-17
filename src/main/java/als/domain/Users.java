package als.domain;
import javax.persistence.*;


@Entity
@Table(name = "users")
public class Users {
    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    
    @Column(name="userId")
    private int userId;
    
//    @Column(name="Details")

    @ManyToOne
    @JoinColumn(name = "personId")
    private PersonDetails details;
    
    @Column(name="userName"//,  nullable = false, columnDefinition = "varchar default 35"
    		)
    private String username;
    
    @Column(name="password"//,  nullable = false, columnDefinition = "varchar default 35"
    		)
    private String password;

    @Column(name="bananas"//,  nullable = false, columnDefinition = "varchar default 35"
    		)
    private int bananas;


    public int getBananas() {
		return bananas;
	}

	public void setBananas(int bananas) {
		this.bananas = bananas;
	}

	public void addBananas(int bananas) {
		this.bananas += bananas;
	}
	public void removeBananas(int bananas) {
		this.bananas -= bananas;
	}

	public Users() {
	}

    public Users(int userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
    
    public Users(PersonDetails details, String username, String password) {
		super();
		this.userId = details.getPerson();
		this.details = details;
		this.username = username;
		this.password = password;
	}



	public PersonDetails getDetails() {
		return details;
	}



	public void setDetails(PersonDetails details) {
		this.details = details;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }



    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + username + '\'' +
                '}';
    }
}
