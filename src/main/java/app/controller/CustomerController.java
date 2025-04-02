package app.controller;

import app.domain.Customer;
import app.domain.Product;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.CustomerSaveException;
import app.exceptions.CustomerUpdateException;
import app.exceptions.ProductNotFoundException;
import app.service.CustomerService;

import java.io.IOException;
import java.util.List;

public class CustomerController {

    private final CustomerService service;

    public CustomerController() throws IOException {
        service = new CustomerService();
    }

    public Customer save(String name) throws CustomerSaveException, IOException {
       Customer customer = new Customer();
       customer.setName(name);
       return service.save(customer);
    }

    //  Вернуть всех покупателей из базы данных (активных).
    public List<Customer> getAllActiveCustomers() throws IOException {
        return service.getAllActiveCustomers();
    }

    //  Вернуть одного покупателя из базы данных по его идентификатору (если он активен).
    public Customer getActiveCustomerById(int id) throws IOException, CustomerNotFoundException {
        return service.getActiveCustomerById(id);
    }

    //  Изменить одного покупателя в базе данных по его идентификатору.
    public void update(int id, String name) throws CustomerUpdateException, IOException {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        service.update(customer);
    }

    //  Удалить покупателя из базы данных по его идентификатору.
    public void deleteById(int id) throws IOException, CustomerNotFoundException {
      service.deleteById(id);
    }

    //  Удалить покупателя из базы данных по его имени.
    public void deleteByName(String name) throws IOException, CustomerNotFoundException {
        service.deleteByName(name);
    }

    //  Восстановить удалённого покупателя в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, CustomerNotFoundException {
        service.restoreById(id);
    }

    //  Вернуть общее количество покупателей в базе данных (активных).
    public int getActiveCustomersCount() throws IOException {
        return service.getActiveCustomersCount();
    }

    //  Вернуть стоимость корзины покупателя по его идентификатору (если он активен).
    public double getCustomersCartTotalPrice(int id) throws IOException, CustomerNotFoundException {
        return service.getCustomersCartTotalPrice(id);
    }

    //  Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он активен)
    public double getCustomersCartAveragePrice(int id) throws IOException, CustomerNotFoundException {
        return service.getCustomersCartAveragePrice(id);
    }

    //  Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
    public void addProductToCustomersCart(int customerId, int productId) throws IOException, CustomerNotFoundException, ProductNotFoundException {
        service.addProductToCustomersCart(customerId, productId);
    }

    //  Удалить товар из корзины покупателя по их идентификаторам
    public void removeProductFromCustomersCart(int customerId, int productId) throws IOException, CustomerNotFoundException, ProductNotFoundException {
        service.removeProductFromCustomersCart(customerId, productId);
    }

    //  Полностью очистить корзину покупателя по его идентификатору (если он активен)
    public void clearCustomersCart(int id) throws IOException, CustomerNotFoundException {
        service.clearCustomersCart(id);
    }
}
