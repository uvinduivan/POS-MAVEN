
package dao.custom.impl;

import dao.custom.CustomerDao;
import dao.util.CrudUtil;
import dto.CustomerDto;
import entity.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDaoImpl implements CustomerDao {


    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }

    @Override

    public boolean save(customer entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        /*PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,entity.getId());
        pstm.setString(2,entity.getName());
        pstm.setString(3,entity.getAddress());
        pstm.setDouble(4,entity.getSalary());

        return pstm.executeUpdate()>0;*/
        return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override

    public boolean update(customer entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name=?,addrress=?,salary=? WHERE id=?";
       /* PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(4,entity.getId());
        pstm.setString(1,entity.getName());
        pstm.setString(2,entity.getAddress());
        pstm.setDouble(3,entity.getSalary());

        return pstm.executeUpdate()>0;*/
        return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());
    }

    @Override

    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE from customer WHERE id=?";
        /*PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,value);

        return pstm.executeUpdate()>0;*/
        return CrudUtil.execute(sql,value);
    }

    @Override

    public List<customer> getAll() throws SQLException, ClassNotFoundException {
        List<customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";

      //  PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){

            list.add(new customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
                    ));
        }

        return list;

    }



}