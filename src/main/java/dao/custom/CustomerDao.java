package dao.custom;

import dao.CrudDao;
import dto.CustomerDto;
import entity.customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<customer> {
    //    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
//    boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
//    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
//    List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException;
    CustomerDto searchCustomer(String id);
}