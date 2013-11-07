package ua.epam.dereza.shop.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Query with internal query builder
 * 
 * @author Eduard_Dereza
 *
 */
public class Query {

	private Query(String productQuery, String countQuery){
		this.productQuery = productQuery;
		this.countQuery = countQuery;
	}

	private String productQuery;
	private String countQuery;

	public String getProductQuery() {
		return productQuery;
	}

	public String getCountQuery() {
		return countQuery;
	}

	@Override
	public String toString() {
		return "Query [query=" + productQuery + ", queryCount=" + countQuery + "]";
	}

	public static class Builder{
		private String base = "SELECT product.id,product.name,price,photo,manufacturer.name as manufacturer,category.name as category,description FROM product INNER JOIN manufacturer ON manufacturer_id=manufacturer.id INNER JOIN category ON category_id=category.id";
		private String count = "SELECT count(*) FROM product INNER JOIN manufacturer ON manufacturer_id=manufacturer.id INNER JOIN category ON category_id=category.id";
		private String orderBy;
		private String manufacturers;
		private String category;
		private String priceFrom;
		private String priceTo;
		private String keyword;
		private String limit;
		private String offset;

		private static enum SortBy{
			priceHigest(" price DESC "), priceLowest(" price ASC "), name(" manufacturer.name ASC ");

			private String value;

			private SortBy(String value) {
				this.value = value;
			}
			@Override
			public String toString() {
				return value;
			}
		}

		public Builder sortBy(String val){
			if(val != null){
				try{
					orderBy = " ORDER BY " + SortBy.valueOf(val).toString();
				}catch(Exception e){
					orderBy = null;
				}
			}
			return this;
		}
		public Builder manufacturers(List<String> val){
			StringBuilder values = new StringBuilder();
			if(val != null){
				for(String tempVal : val){
					if(values.length() != 0)
						values.append(", ");
					values.append("'" + tempVal + "'");
				}
				if(values.length() != 0)
					manufacturers = " manufacturer.name IN (" + values.toString() + ")";
			}
			return this;
		}
		public Builder category(String val){
			if(val != null){
				category = " category.name='" + val + "'";
			}
			return this;
		}
		public Builder priceFrom(BigDecimal val){
			if(val != null){
				priceFrom = " price>=" + val;
			}
			return this;
		}
		public Builder priceTo(BigDecimal val){
			if(val != null){
				priceTo = " price<=" + val;
			}
			return this;
		}
		public Builder keyword(String val){
			if(val != null){
				keyword = " CONCAT(manufacturer.name,' ',product.name) LIKE '%" + val + "%'";
			}
			return this;
		}
		public Builder limit(Integer val){
			if(val != null){
				limit = " LIMIT " + val;
			}
			return this;
		}
		public Builder offset(Integer val){
			if(val != null){
				offset = " OFFSET " + val;
			}
			return this;
		}

		public Query build(){
			StringBuilder parameters = new StringBuilder();
			if(manufacturers != null)
				parameters.append(manufacturers + " AND ");
			if(category != null)
				parameters.append(category + " AND ");
			if(priceFrom != null)
				parameters.append(priceFrom + " AND ");
			if(priceTo != null)
				parameters.append(priceTo + " AND ");
			if(keyword != null)
				parameters.append(keyword + " AND ");

			// removes last 'AND' occurence
			int lastIndex = parameters.lastIndexOf("AND");
			if(lastIndex != -1)
				parameters = new StringBuilder(parameters.substring(0, lastIndex - 1));

			// append WHERE
			if(parameters.length() != 0)
				parameters.insert(0, " WHERE ");

			if(orderBy != null)
				parameters.append(orderBy + " ");
			if(limit != null)
				parameters.append(limit + " ");
			if(offset != null)
				parameters.append(offset + " ");



			String productQuery = base + parameters + ";";
			String countQuery = count + parameters + ";";

			return new Query(productQuery, countQuery);
		}
	}
}
