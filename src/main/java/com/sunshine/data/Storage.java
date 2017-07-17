/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.sunshine.data;

import java.util.ArrayList;
import java.util.List;

import com.sunshine.service.DemoEdmProvider;
import com.sunshine.util.Util;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.server.api.uri.UriParameter;

public class Storage {

	// represent our database
	private List<Entity> productList;
	private List<Entity> productListBeforeTransaction;
	private List<Entity> categoryList;
	private List<Entity> orderList;
	// private EntityManagerFactory emf = null;
	// private Product product;
	// private Order order;

	public Storage() {

		productList = new ArrayList<Entity>();
		categoryList = new ArrayList<Entity>();
		orderList = new ArrayList<Entity>();

		// creating some sample data
		productList = Product_data.initProductData();
		categoryList = Category_data.initCategoryData();
		orderList = Order_data.initOrderData();
	}

	/* PUBLIC FACADE */

	// Read
	public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) {
		EntityCollection entitySet = null;

		if (edmEntitySet.getName().equals(DemoEdmProvider.ES_PRODUCTS_NAME)) {
			entitySet = getProducts();
		} else if (edmEntitySet.getName().equals(DemoEdmProvider.ES_CATEGORIES_NAME)) {
			entitySet = getCategories();
		} else if (edmEntitySet.getName().equals(DemoEdmProvider.ES_ORDERS_NAME)) {
			entitySet = getOrders();
		}

		return entitySet;
	}

	public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) {
		Entity entity = null;

		EdmEntityType edmEntityType = edmEntitySet.getEntityType();

		if (edmEntityType.getName().equals(DemoEdmProvider.ET_PRODUCT_NAME)) {
			entity = getProduct(edmEntityType, keyParams);
		} else if (edmEntityType.getName().equals(DemoEdmProvider.ET_CATEGORY_NAME)) {
			entity = getCategory(edmEntityType, keyParams);
		} else if (edmEntityType.getName().equals(DemoEdmProvider.ET_ORDER_NAME)) {
			entity = getOrder(edmEntityType, keyParams);
		}

		return entity;
	}

	// Create
	public Entity createEntityData(EdmEntitySet edmEntitySet, Entity entityToCreate) {

		EdmEntityType edmEntityType = edmEntitySet.getEntityType();

		// actually, this is only required if we have more than one Entity Type
		if (edmEntityType.getName().equals(DemoEdmProvider.ET_PRODUCT_NAME)) {
			return Product_data.createProduct(edmEntityType, entityToCreate);
		} else if (edmEntityType.getName().equals(DemoEdmProvider.ET_ORDER_NAME)) {
			return Order_data.createOrder(edmEntityType, entityToCreate);
		} else if (edmEntityType.getName().equals(DemoEdmProvider.ET_CATEGORY_NAME)) {
			return Category_data.createCategory(edmEntityType, entityToCreate);
		}

		return null;
	}

	public void beginTransaction() {
		if (productListBeforeTransaction == null) {
			productListBeforeTransaction = cloneEntityCollection(productList);
		}
	}

	public void commitTransaction() {
		if (productListBeforeTransaction != null) {
			productListBeforeTransaction = null;
		}
	}

	public void rollbackTranscation() {
		if (productListBeforeTransaction != null) {
			productList = productListBeforeTransaction;
			productListBeforeTransaction = null;
		}
	}

	// Navigation
	public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType) {
		EntityCollection collection = getRelatedEntityCollection(entity, relatedEntityType);
		if (collection.getEntities().isEmpty()) {
			return null;
		}
		return collection.getEntities().get(0);
	}

	public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType, List<UriParameter> keyPredicates) {

		EntityCollection relatedEntities = getRelatedEntityCollection(entity, relatedEntityType);
		return Util.findEntity(relatedEntityType, relatedEntities, keyPredicates);
	}

	public EntityCollection getRelatedEntityCollection(Entity sourceEntity, EdmEntityType targetEntityType) {
		EntityCollection navigationTargetEntityCollection = new EntityCollection();

		FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
		String sourceEntityFqn = sourceEntity.getType();

		if (sourceEntityFqn.equals(DemoEdmProvider.ET_PRODUCT_FQN.getFullQualifiedNameAsString())
				&& relatedEntityFqn.equals(DemoEdmProvider.ET_CATEGORY_FQN)) {
			// relation Products->Category (result all categories)
			int productID = (Integer) sourceEntity.getProperty("ID").getValue();
			if (productID == 1 || productID == 2) {
				navigationTargetEntityCollection.getEntities().add(categoryList.get(0));
			} else if (productID == 3 || productID == 4) {
				navigationTargetEntityCollection.getEntities().add(categoryList.get(1));
			} else if (productID == 5 || productID == 6) {
				navigationTargetEntityCollection.getEntities().add(categoryList.get(2));
			}
		} else if (sourceEntityFqn.equals(DemoEdmProvider.ET_CATEGORY_FQN.getFullQualifiedNameAsString())
				&& relatedEntityFqn.equals(DemoEdmProvider.ET_PRODUCT_FQN)) {
			// relation Category->Products (result all products)
			int categoryID = (Integer) sourceEntity.getProperty("ID").getValue();
			if (categoryID == 1) {
				// the first 2 products are notebooks
				navigationTargetEntityCollection.getEntities().addAll(productList.subList(0, 2));
			} else if (categoryID == 2) {
				// the next 2 products are organizers
				navigationTargetEntityCollection.getEntities().addAll(productList.subList(2, 4));
			} else if (categoryID == 3) {
				// the first 2 products are monitors
				navigationTargetEntityCollection.getEntities().addAll(productList.subList(4, 6));
			}
		}

		if (navigationTargetEntityCollection.getEntities().isEmpty()) {
			return null;
		}

		return navigationTargetEntityCollection;
	}

	/* INTERNAL */
	private List<Entity> cloneEntityCollection(final List<Entity> entities) {
		final List<Entity> clonedEntities = new ArrayList<Entity>();

		for (final Entity entity : entities) {
			final Entity clonedEntity = new Entity();

			clonedEntity.setId(entity.getId());
			for (final Property property : entity.getProperties()) {
				clonedEntity.addProperty(new Property(property.getType(), property.getName(), property.getValueType(),
						property.getValue()));
			}

			clonedEntities.add(clonedEntity);
		}

		return clonedEntities;
	}

	private EntityCollection getProducts() {
		EntityCollection retEntitySet = new EntityCollection();
		productList = Product_data.initProductData();
		for (Entity productEntity : this.productList) {
			retEntitySet.getEntities().add(productEntity);
		}

		return retEntitySet;
	}

	private Entity getProduct(EdmEntityType edmEntityType, List<UriParameter> keyParams) {

		// the list of entities at runtime
		EntityCollection entityCollection = getProducts();

		/* generic approach to find the requested entity */
		return Util.findEntity(edmEntityType, entityCollection, keyParams);
	}

	private EntityCollection getCategories() {
		EntityCollection entitySet = new EntityCollection();
		orderList = Category_data.initCategoryData();
		for (Entity categoryEntity : this.categoryList) {
			entitySet.getEntities().add(categoryEntity);
		}

		return entitySet;
	}

	private Entity getCategory(EdmEntityType edmEntityType, List<UriParameter> keyParams) {

		// the list of entities at runtime
		EntityCollection entitySet = getCategories();

		/* generic approach to find the requested entity */
		return Util.findEntity(edmEntityType, entitySet, keyParams);
	}

	private EntityCollection getOrders() {
		EntityCollection retEntitySet = new EntityCollection();
		orderList = Order_data.initOrderData();
		for (Entity orderEntity : this.orderList) {
			retEntitySet.getEntities().add(orderEntity);
		}

		return retEntitySet;
	}

	private Entity getOrder(EdmEntityType edmEntityType, List<UriParameter> keyParams) {

		// the list of entities at runtime
		EntityCollection entityCollection = getOrders();

		/* generic approach to find the requested entity */
		return Util.findEntity(edmEntityType, entityCollection, keyParams);
	}
}
