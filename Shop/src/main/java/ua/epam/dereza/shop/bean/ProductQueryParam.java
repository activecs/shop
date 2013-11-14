package ua.epam.dereza.shop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Bean for product's filter form
 * 
 * @author Eduard_Dereza
 *
 */
public class ProductQueryParam {

	private String sortBy;
	private List<String> manufacturers;
	private String category;
	private BigDecimal priceFrom;
	private BigDecimal priceTo;
	private String keyword;
	private Integer itemPerPage;
	private Integer page;

	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public List<String> getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(List<String> manufacturers) {
		this.manufacturers = manufacturers;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BigDecimal getPriceFrom() {
		return priceFrom;
	}
	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}
	public BigDecimal getPriceTo() {
		return priceTo;
	}
	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getItemPerPage() {
		return itemPerPage;
	}
	public void setItemPerPage(Integer itemPerPage) {
		this.itemPerPage = itemPerPage;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "ProductFilter [sortBy=" + sortBy + ", manufacturers="
				+ manufacturers + ", category=" + category + ", priceFrom="
				+ priceFrom + ", priceTo=" + priceTo + ", keyword=" + keyword
				+ ", itemPerPage=" + itemPerPage + ", page=" + page + "]";
	}
}
