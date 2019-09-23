package nl.nina.crm.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nl.nina.crm.model.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	private EntityManager entityManager;

	@Autowired
	public CustomerDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Customer> getCustomers() {
		Session session = entityManager.unwrap(Session.class);
		Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
		return query.getResultList();
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(Customer.class, id);
	}

	@Override
	public void deleteCustomer(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("delete from Customer where id=:customerId");
		query.setParameter("customerId", id);
		query.executeUpdate();
	}
	
    @Override
    public List<Customer> searchCustomers(String searchName) {

        Session currentSession = entityManager.unwrap(Session.class);
        Query<Customer> query = null;

        if (searchName != null && searchName.trim().length() > 0) {
        	query = currentSession.createQuery("from Customer where lower(firstName) like :searchName or lower(lastName) like :searchName", Customer.class);
        	query.setParameter("searchName", "%" + searchName.toLowerCase() + "%");
        }
        
        else {
        	query = currentSession.createQuery("from Customer", Customer.class);            
        }
        
        List<Customer> customers = query.getResultList();      
        return customers;
    }
}
