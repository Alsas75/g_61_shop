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

/*
Репозитории - это второй слой приложения.
Задача репозиториев - обеспечивать простейший доступ к данным
посредством реализации CRUD-операций.
CRUD - Create (создать), Read (прочитать), Update (обновить), Delete (удалить)
 */
public class ProductRepository {

    // Файл, который является базой данных
    private final File database;

    // Маппер для чтения и записи в файл объектов
    private final ObjectMapper mapper;

    // Поле, которое хранит максимальный идентификатор, сохранённый в базе данных
    private int maxId;

    // Этот конструктор отрабатывает когда создаётся объект репозитория.
    // В этом конструкторе мы инициализируем все поля объекта репозитория.
    public ProductRepository() throws IOException {
        database = new File("database/product.txt");
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Выясняем, какой идентификатор в БД на данный момент максимальный
        List<Product> products = findAll();

        if (!products.isEmpty()) {
            Product lastProduct = products.get(products.size() - 1);
            maxId = lastProduct.getId();
        }
    }

    // Сохраняем новый продукт в БД
    public Product save(Product product) throws IOException {
        product.setId(++maxId);
        List<Product> products = findAll();
        products.add(product);
        mapper.writeValue(database, products);
        return product;
    }

    // Читаем из БД все продукты
    public List<Product> findAll() throws IOException {
        try {
            Product[] products = mapper.readValue(database, Product[].class);
            return new ArrayList<>(Arrays.asList(products));
        } catch (MismatchedInputException e) {
            // Если произошла ошибка MismatchedInputException, это значит,
            // что Джексону не удалось прочитать информацию из файла, потому что он пустой.
            // Раз файл пустой - значит продуктов нет, а значит возвращаем пустой лист.
            return new ArrayList<>();
        }
    }

    // Читаем из БД один продукт по идентификатору
    public Product findById(int id) throws IOException {
        return findAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Обновление существующего продукта в БД
    // Этот метод будет менять только цену
    public void update(Product product) throws IOException {
        int id = product.getId();
        double newPrice = product.getPrice();
        boolean active = product.isActive();

        List<Product> products = findAll();
        products
                .stream()
                .filter(x -> x.getId() == id)
                .forEach(x -> {
                    x.setPrice(newPrice);
                    x.setActive(active);
                });
        mapper.writeValue(database, products);
    }

    // Удаляем продукт из БД
    public void deleteById(int id) throws IOException {
        List<Product> products = findAll();
        products.removeIf(x -> x.getId() == id);
        mapper.writeValue(database, products);
    }
}
