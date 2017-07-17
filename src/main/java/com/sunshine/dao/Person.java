package com.sunshine.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

 @Entity
 @Table(name = "PERSON")
 public class Person {
 
     @Id 
     @GeneratedValue
     @Column(name = "ID")
     private Integer id;
     
     @Column(name = "NAME", length = 100)
     private String name;
     
     
     public Integer getId() {
         return id;
     }
     public void setId(Integer id) {
         this.id = id;
     }
     public String getName() {
         return name;
     }
     public void setName(String name) {
         this.name = name;
     }
     public Person(String name) {
         super();
         this.name = name;
     }
     public Person() {
         super();
         // TODO Auto-generated constructor stub
     }
     @Override
     public String toString() {
        return "Person [id=" + id + ", name=" + name + "]";
     }
    
     
 }