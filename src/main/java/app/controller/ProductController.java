package app.controller;

import app.domain.Product;
import app.exceptions.ProductNotFoundException;
import app.exceptions.ProductSaveException;
import app.exceptions.ProductUpdateException;
import app.service.ProductService;

import java.io.IOException;
import java.util.List;

public class ProductController {

    private final ProductService service;

    public ProductController() throws IOException {
        service = new ProductService();
    }

    public Product save(String title, double price) throws ProductSaveException, IOException {
        Product product = new Product(title, price);
        return service.save(product);
    }

    //    Вернуть все продукты из базы данных (активные).
    public List<Product> getAllActiveProducts() throws IOException {
        return service.getAllActiveProducts();
    }

    //    Вернуть один продукт из базы данных по его идентификатору (если он активен).
    public Product getActiveProductById(int id) throws IOException, ProductNotFoundException {
        return service.getActiveProductById(id);
    }

    //    Изменить один продукт в базе данных по его идентификатору.
    public void update(int id, double price) throws ProductUpdateException, IOException {
        Product product = new Product(id, price);
        service.update(product);
    }

    //    Удалить продукт из базы данных по его идентификатору.
    public void deleteById(int id) throws IOException, ProductNotFoundException {
        service.deleteById(id);
    }

    //    Удалить продукт из базы данных по его наименованию.
    public void deleteByTitle(String title) throws IOException {
        service.deleteByTitle(title);
    }

    //    Восстановить удалённый продукт в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, ProductNotFoundException {
        service.restoreById();
    }

    //    Вернуть общее количество продуктов в базе данных (активных).
    public int getActiveProductsCount() throws IOException {
        return service.getActiveProductsCount();
    }

    //    Вернуть суммарную стоимость всех продуктов в базе данных (активных).
    public double getActiveProductsTotalCost() throws IOException {
        return service.getActiveProductsTotalCost();
    }

    //    Вернуть среднюю стоимость продукта в базе данных (из активных).
    public double getActiveProductsAveragePrice() throws IOException {
        return service.getActiveProductsAveragePrice();
    }
}
