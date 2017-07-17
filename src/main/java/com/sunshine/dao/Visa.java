package com.sunshine.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VISA")
public class Visa {

    @Id 
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "TITLE", length = 50)
    private String title;
    
    @Column(name = "DESCRIPTION", length = 200)
    private String description;
    
    @Column(name = "IMAGES", length = 50)
    private String images;
    
    @Column(name = "PRO_DAYS", length = 10)
    private int pro_days;
    
    @Column(name = "EXPIRY_DATE", length = 10)
    private String expiry_date;
    
    @Column(name = "STAY_DAY", length = 10)
    private int stay_day;
    
    @Column(name = "NUMBER", length = 10)
    private int number;
    
    @Column(name = "PRICE", length = 10)
    private int price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setName(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public Visa(String title,String description) {
        super();
        this.title = title;
        this.description = description;
    }
	
	public Visa() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
    
    
}
