package app.repository;

import app.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {

    //Файл базы данных
    private final File database;

    //mapper
    private final ObjectMapper mapper;

    //ID
    private int maxId;

    public ProductRepository() throws IOException {
        database = new File("database/product.txt");
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //определяем максимальный maxId
        List<Product> products = findAll();

        if (!products.isEmpty()) {
            Product lastProduct = products.get(products.size() - 1);
            maxId = lastProduct.getId();
        }
    }

    //сохранение
    public Product save(Product product) throws IOException {
        product.setId(++maxId);
        List<Product> products = findAll();
        products.add(product);
        mapper.writeValue(database, products);
        return product;
    }

    //читаем из БД
    public List<Product> findAll() throws IOException {
        try {
            Product[] products = mapper.readValue(database, Product[].class);

            return new ArrayList<>(Arrays.asList(products));
        } catch (MismatchedInputException e) {
            return new ArrayList<>();
        }
    }

    //читаем по id
    public Product findById(int id) throws IOException {
        return findAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //update
    public void update(Product product) throws IOException {
        int id = product.getId();
        double newPrice = product.getPrice();

        List<Product> products = findAll();
        products
                .stream()
                .filter(x -> x.getId() == id)
                .forEach(x -> x.setPrice(newPrice));
        mapper.writeValue(database, products);
    }

    //delete
    public void deleteById(int id) throws IOException {
        List<Product> products = findAll();
        products.removeIf(x -> x.getId() == id);
        mapper.writeValue(database,products);
    }
}
