package com.sunshine.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntityType;

import com.sunshine.dao.Order;
import com.sunshine.service.DemoEdmProvider;
import com.sunshine.util.Util;

public class Order_data {
	public static List<Entity> initOrderData() {
		List<Entity> orderList = new ArrayList<Entity>();
//		productList.clear();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		// add some sample product entities
		@SuppressWarnings("unchecked")
		List<Order> list = (List<Order>) em.createQuery("select o from Order o order by o.id").getResultList();
		em.getTransaction().commit();
		em.close();
		// add some sample product entities
		for (Order order : list) {
			Entity entity = new Entity();
			entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, order.getId()));
			entity.addProperty(new Property(null, "Orderdate", ValueType.PRIMITIVE, order.getOrderdate()));
			entity.addProperty(new Property(null, "Remark", ValueType.PRIMITIVE, order.getRemark()));
			entity.setType(DemoEdmProvider.ET_ORDER_FQN.getFullQualifiedNameAsString());
			entity.setId(Util.createId(entity, "ID"));
			orderList.add(entity);
		}
		return orderList;
	}
	
	static Entity createOrder(EdmEntityType edmEntityType, Entity entity) {

//		// the ID of the newly created product entity is generated automatically
//		int newId = 1;
////		while (productIdExists(newId)) {
////			newId++;
////		}
//
//		Property idProperty = entity.getProperty("ID");
////		if (idProperty != null) {
////			idProperty.setValue(ValueType.PRIMITIVE, Integer.valueOf(newId));
////		} else {
////			// as of OData v4 spec, the key property can be omitted from the
////			// POST request body
////			entity.getProperties().add(new Property(null, "ID", ValueType.PRIMITIVE, newId));
////		}
//		// entity.setId(createId("Products", newId));
//		entity.setId(createId(entity, "ID"));
//		this.orderList.add(entity);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		// UserInfo user = generateRadomEntity();

		Order order = new Order();
		// String key = atom.addAndGet(1) + "";
		SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//		order.setId((Integer) entity.getProperty("Id").getValue());
		order.setCustomerid((Integer) entity.getProperty("Customerid").getValue());
		order.setNumber( (Integer) entity.getProperty("Number").getValue());
		try {
			order.setDate_from(format.parse(entity.getProperty("Date_from").getValue().toString()));
			order.setDate_to(format.parse(entity.getProperty("Date_to").getValue().toString()));
			order.setOrderdate(format.parse(entity.getProperty("Orderdate").getValue().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		order.setLocation(entity.getProperty("Location").getValue().toString());
		order.setRemark(entity.getProperty("Remark").getValue().toString());
		
		
		em.persist(order);
		em.getTransaction().commit();
		em.close();
		return entity;

	}
}
