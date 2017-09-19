package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Customer
{
    @Id 
    @GeneratedValue
    Long id;
    String firstName;
    String surname;
    
    public Customer(){
    	
    }
    
	public Customer(String firstName, String surname) {
		this.firstName = firstName;
		this.surname = surname;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", surname=" + surname + "]";
	}
    
    
}
