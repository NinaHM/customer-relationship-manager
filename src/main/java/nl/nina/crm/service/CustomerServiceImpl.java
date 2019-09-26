package nl.nina.crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.nina.crm.model.Customer;
import nl.nina.crm.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		Optional<Customer> result = customerRepository.findById(id);
		
		Customer customer = null;
		
		if(result.isPresent()) {
			customer = result.get();
		} else {
			customer = new Customer();
		}
		
		return customer;
	}
	
	@Override
	public void deleteCustomer(int id) {
		customerRepository.deleteById(id);
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {

		if (searchName != null) {
			return customerRepository.findByFirstOrLastName(searchName);
		} else {
			return customerRepository.findAll();
		}
	}

}
