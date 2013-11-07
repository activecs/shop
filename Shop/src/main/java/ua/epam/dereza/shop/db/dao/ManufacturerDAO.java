package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;
import java.util.List;

import ua.epam.dereza.shop.bean.Manufacturer;

public interface ManufacturerDAO {
	
	 /**
     * Returns a manufacturer with the given id
     * 
	 * @param connection 
     * @param manufacturer id
     *      
     * @return 
	 * @throws DAOException 
     */
    public Manufacturer findManufacturerById(Connection conn, int id) throws DAOException;
    
    /**
     * Returns a list of manufacturers
     * 
	 * @param connection
     * 
     * @return List<Manufacturer>
     * @throws DAOException 
     */
    public List<Manufacturer> findAllManufacturers(Connection conn) throws DAOException;
    
    /**
     * Returns a manufacturer with the given manufacturer name
     * 
	 * @param connection
     * @param manufacturer name
     *            
     * @return Manufacturer
     * @throws DAOException 
     */
    public Manufacturer findManufacturerByName(Connection conn, String name) throws DAOException;
}
