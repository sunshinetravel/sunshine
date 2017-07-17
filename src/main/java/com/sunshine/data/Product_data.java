package com.sunshine.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntityType;

import com.sunshine.dao.Product;
import com.sunshine.service.DemoEdmProvider;
import com.sunshine.util.Util;

public class Product_data {
	public static List<Entity> initProductData() {
		List<Entity> productList = new ArrayList<Entity>();
//		productList.clear();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		// add some sample product entities
		@SuppressWarnings("unchecked")
		List<Product> list = (List<Product>) em.createQuery("select p from Product p order by p.id").getResultList();
		em.getTransaction().commit();
		em.close();
		// add some sample product entities
		for (Product product : list) {
			Entity entity = new Entity();
			entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, product.getId()));
			entity.addProperty(new Property(null, "Name", ValueType.PRIMITIVE, product.getName()));
			entity.addProperty(new Property(null, "Description", ValueType.PRIMITIVE, product.getDescription()));
			entity.setType(DemoEdmProvider.ET_PRODUCT_FQN.getFullQualifiedNameAsString());
			entity.setId(Util.createId(entity, "ID"));
			productList.add(entity);
		}
		return productList;
	}
	
	public static Entity createProduct(EdmEntityType edmEntityType, Entity entity) {

		// the ID of the newly created product entity is generated automatically
//		int newId = 1;
////		while (productIdExists(newId)) {
////			newId++;
////		}
//
//		Property idProperty = entity.getProperty("ID");
//		if (idProperty != null) {
//			idProperty.setValue(ValueType.PRIMITIVE, Integer.valueOf(newId));
//		} else {
//			// as of OData v4 spec, the key property can be omitted from the
//			// POST request body
//			entity.getProperties().add(new Property(null, "ID", ValueType.PRIMITIVE, newId));
//		}
//		// entity.setId(createId("Products", newId));
//		entity.setId(Util.createId(entity, "ID"));
//		this.productList.add(entity);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		// UserInfo user = generateRadomEntity();

		Product product = new Product();
		// String key = atom.addAndGet(1) + "";
		product.setName(entity.getProperty("Name").getValue().toString());
		;
		product.setDescription(entity.getProperty("Description").getValue().toString());
		;
		em.persist(product);
		em.getTransaction().commit();
		em.close();
		return entity;

	}
	
//	private boolean productIdExists(int id) {
//
//		for (Entity entity : this.productList) {
//			Integer existingID = (Integer) entity.getProperty("ID").getValue();
//			if (existingID.intValue() == id) {
//				return true;
//			}
//		}
//
//		return false;
//	}

}
