package nl.nina.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.nina.crm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	 @Query("from Customer where firstName like %?1%"
	 		+ "or lastName like %?1%"
			+ "or concat(firstName, ' ', lastName) like %?1%"
	 		+ "order by lastName, firstName")
	 List<Customer> findByFirstOrLastName(String name);	
	 
	 @Query("from Customer order by lastName, firstName") 
	 List<Customer> findAllSorted();
	 
}
