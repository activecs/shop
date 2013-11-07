package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;
import java.util.List;

import ua.epam.dereza.shop.bean.Category;

public interface CategoryDAO {
	
	 /**
     * Returns a category with the given id
     * 
     * @param category id
	 * @param connection
     *      
     * @return 
	 * @throws DAOException 
     */
    public Category findCategoryById(Connection conn, int id) throws DAOException;
    
    /**
     * Returns a list of categories
     * 
	 * @param connection
     * 
     * @return List<Category>
     * @throws DAOException 
     */
    public List<Category> findAllCategories(Connection conn) throws DAOException;
    
    /**
     * Returns a category with the given category name
     * 
     * @param connection
     * @param category name
     *            
     * @return Category
     * @throws DAOException 
     */
    public Category findCategoryByName(Connection conn, String name) throws DAOException;
}
