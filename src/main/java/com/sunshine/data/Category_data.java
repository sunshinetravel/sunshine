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

import com.sunshine.dao.Category;
import com.sunshine.service.DemoEdmProvider;
import com.sunshine.util.Util;

public class Category_data {
	public static List<Entity> initCategoryData() {
		List<Entity> categoryList = new ArrayList<Entity>();
//		productList.clear();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		// add some sample product entities
		@SuppressWarnings("unchecked")
		List<Category> list = (List<Category>) em.createQuery("select c from Category c order by c.id").getResultList();
		em.getTransaction().commit();
		em.close();
		// add some sample product entities
		for (Category category : list) {
			Entity entity = new Entity();
			entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, category.getId()));
			entity.addProperty(new Property(null, "Name", ValueType.PRIMITIVE, category.getName()));
			entity.setType(DemoEdmProvider.ET_CATEGORY_FQN.getFullQualifiedNameAsString());
			entity.setId(Util.createId(entity, "ID"));
			categoryList.add(entity);
		}
		return categoryList;
	}
	
	static Entity createCategory(EdmEntityType edmEntityType, Entity entity) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Category category = new Category();

		category.setName(entity.getProperty("Name").getValue().toString());
		category.setDescrption(entity.getProperty("Descrption").getValue().toString());
				
		em.persist(category);
		em.getTransaction().commit();
		em.close();
		return entity;

	}
}
