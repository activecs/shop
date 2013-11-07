package ua.epam.dereza.shop.db.dao;

/**
 * 
 * @author Eduard_Dereza
 *
 */
public interface DAOFactory {

	public UserDAO getUserDAO();

	public ProductDAO getProductDAO();

	public ManufacturerDAO getManufacturerDAO();

	public CategoryDAO getCategoryDAO();
}
