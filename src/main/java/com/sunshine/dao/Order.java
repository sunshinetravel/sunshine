package com.sunshine.dao;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id 
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;	 

	@Column(name = "CUSTOMERID")
    private Integer customerid;
    
    @Column(name = "ORDERDATE")
    private Date orderdate;
    
    @Column(name = "NUMBER")
    private Integer number;
    
    @Column(name = "LOCATION", length = 200)
    private String location;
    
    @Column(name = "DATE_FROM")
    private Date date_from;
    
    @Column(name = "DATE_TO")
    private Date date_to;
    
    @Column(name = "REMARK", length = 200)
    private String remark;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}  

    public Integer getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}  	
	
	public Order(Integer id, Integer customerid, Date orderdate, Integer number, String location, Date date_from,
			Date date_to, String remark) {
		super();
		this.id = id;
		this.customerid = customerid;
		this.orderdate = orderdate;
		this.number = number;
		this.location = location;
		this.date_from = date_from;
		this.date_to = date_to;
		this.remark = remark;
	}
	
//	public Order(String remark) {
//		super();
//		this.remark = remark;
//	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
    
    
}
