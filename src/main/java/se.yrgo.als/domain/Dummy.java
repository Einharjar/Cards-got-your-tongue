package se.yrgo.als.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="DummyTable")
public class Dummy{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;	
	@Column(length=25)
	private String name;
	

	public Dummy() {
		this.name = "John Wick";
	}
	
	public Dummy(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}	
	
	
	
}