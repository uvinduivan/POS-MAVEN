
package dao.custom.impl;

import dao.custom.CustomerDao;
import dao.util.hibernateUtil;
import dto.CustomerDto;
import entity.customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }

    @Override

    public boolean save(customer entity) throws SQLException, ClassNotFoundException {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
        //String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        //return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override

    public boolean update(customer entity) throws SQLException, ClassNotFoundException {

        Session session = hibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        customer customer = session.find(entity.customer.class,entity.getId());
        customer.setName(entity.getName());
        customer.setAddress(entity.getAddress());
        customer.setSalary(entity.getSalary());
        session.save(customer);
        transaction.commit();
        return true;


    }

    @Override

    public boolean delete(String value) throws SQLException, ClassNotFoundException {

        Session session = hibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        session.delete(session.find(customer.class,value));
        transaction.commit();
        return true;
    }

    @Override

    public List<customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = hibernateUtil.getSession();
        Query query = session.createQuery("FROM customer");
        List<customer> list = query.list();

      /* List<customer> list = new ArrayList<>();
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
        }*/


        return list;

    }



}