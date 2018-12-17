package als.domain;
import javax.persistence.*;


@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private int id;
    
    @Column(name="username")
    private String username;
    
  @Column(name="banannasGained")
  private int bannanasGained;
  
    @Column(name="notes")
    private String notes;
    
    
    
    
    
    
    
	public Transactions() {
		super();
	}
    
    
	
	
	
	public Transactions(String username, int bannanasGained, String notes) {
		super();
		
		this.username = username;
		this.bannanasGained = bannanasGained;
		this.notes = notes;
	}





	public int getUserId() {
		return id;
	}
	public void setUserId(int userId) {
		this.id = userId;
	}
	public int getBannanasGained() {
		return bannanasGained;
	}
	public void setBannanasGained(int bannanasGained) {
		this.bannanasGained = bannanasGained;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}