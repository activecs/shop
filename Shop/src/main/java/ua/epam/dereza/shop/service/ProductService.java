package ua.epam.dereza.shop.service;

import java.util.List;

import ua.epam.dereza.shop.bean.Category;
import ua.epam.dereza.shop.bean.Manufacturer;
import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.bean.ProductFilter;
import ua.epam.dereza.shop.db.dao.DAOException;

public interface ProductService {

	public int getAmount(Query query) throws DAOException;

	public List<Product> getProducts(Query query) throws DAOException;

	public Product getProduct(String id) throws DAOException;

	public List<Manufacturer> getManufacturers() throws DAOException;

	public List<Category> getCategories() throws DAOException;
}
