package app.repository;

import app.domain.Customer;
import app.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerRepository {


    //Файл базы данных
    private final File database;

    //mapper
    private final ObjectMapper mapper;

    //ID
    private int maxId;

    public CustomerRepository() throws IOException {
        database = new File("database/customer.txt");
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //определяем максимальный maxId
        List<Customer> customers = findAll();

        if (!customers.isEmpty()) {
            Customer lastCustomer = customers.get(customers.size() - 1);
            maxId = lastCustomer.getId();
        }
    }

    //сохранение
    public Customer save(Customer customer) throws IOException {
        customer.setId(++maxId);
        List<Customer> customers = findAll();
        customers.add(customer);
        mapper.writeValue(database, customers);
        return customer;
    }

    //читаем из БД
    public List<Customer> findAll() throws IOException {
        try {
            Customer[] customers = mapper.readValue(database, Customer[].class);

            return new ArrayList<>(Arrays.asList(customers));
        } catch (MismatchedInputException e) {
            return new ArrayList<>();
        }
    }

    //читаем по id
    public Customer findById(int id) throws IOException {
        return findAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //update
    public void update(Customer customer) throws IOException {
        int id = customer.getId();
        String newName = customer.getName();
        boolean active = customer.isActive();
        List<Product> products = customer.getProducts();

        List<Customer> customers = findAll();
        customers
                .stream()
                .filter(x -> x.getId() == id)
                .forEach(x -> {
                    x.setName(newName);
                    x.setActive(active);
                    if (!products.isEmpty()) {
                        x.setProducts(products);
                    }
                });
        mapper.writeValue(database, customers);
    }

    //delete
    public void deleteById(int id) throws IOException {
        List<Customer> customers = findAll();
        customers.removeIf(x -> x.getId() == id);
        mapper.writeValue(database, customers);
    }
}
