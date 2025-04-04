package app.service;

import app.domain.Product;
import app.exceptions.ProductNotFoundException;
import app.exceptions.ProductSaveException;
import app.exceptions.ProductUpdateException;
import app.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

/*
Сервисы - это третий слой приложения.
Сервисы содержат всю бизнес-логику, то есть
тот код, который и решает бизнес-задачи, ради которых
приложение и создавалось.
Сервис не должен обращаться в базу данных напрямую, вместо
этого он должен вызывать соответствующие методы репозитория.
 */
public class ProductService {

    /*
    Это поле с объектом репозитория нужно для того, чтобы код сервиса
    мог обращаться к объекту репозитория и вызывать у него методы
    для взаимодействия с базой данных.
     */
    private final ProductRepository repository;

    public ProductService() throws IOException {
        repository = new ProductRepository();
    }

    //    Сохранить продукт в базе данных (при сохранении продукт автоматически считается активным).
    public Product save(Product product) throws ProductSaveException, IOException {
        if (product == null) {
            throw new ProductSaveException("Продукт не может быть null");
        }

        String title = product.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new ProductSaveException("Наименование продукта не может быть пустым");
        }

        if (product.getPrice() <= 0) {
            throw new ProductSaveException("Цена продукта должна быть положительной");
        }

        product.setActive(true);
        return repository.save(product);
    }

    //    Вернуть все продукты из базы данных (активные).
    public List<Product> getAllActiveProducts() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
//                .filter(x -> x.isActive())
                .toList();
    }

    //    Вернуть один продукт из базы данных по его идентификатору (если он активен).
    public Product getActiveProductById(int id) throws IOException, ProductNotFoundException {
        Product product = repository.findById(id);

        if (product == null || !product.isActive()) {
            throw new ProductNotFoundException(id);
        }

        return product;
    }

    //    Изменить один продукт в базе данных по его идентификатору.
    public void update(Product product) throws ProductUpdateException, IOException {
        if (product == null) {
            throw new ProductUpdateException("Продукт не может быть null");
        }

        if (product.getPrice() <= 0) {
            throw new ProductUpdateException("Цена продукта должна быть положительной");
        }

        product.setActive(true);
        repository.update(product);
    }

    //    Удалить продукт из базы данных по его идентификатору.
    public void deleteById(int id) throws IOException, ProductNotFoundException {
        Product product = getActiveProductById(id);
        product.setActive(false);
        repository.update(product);
    }

    //    Удалить продукт из базы данных по его наименованию.
    public void deleteByTitle(String title) throws IOException, ProductNotFoundException {
        Product product = getAllActiveProducts()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .peek(x -> x.setActive(false))
                .findFirst()
                .orElseThrow(
                        () -> new ProductNotFoundException(title)
                );
        repository.update(product);
    }

    //    Восстановить удалённый продукт в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, ProductNotFoundException {
        Product product = repository.findById(id);
        if (product != null) {
            product.setActive(true);
            repository.update(product);
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    //    Вернуть общее количество продуктов в базе данных (активных).
    public int getActiveProductsCount() throws IOException {
        return getAllActiveProducts().size();
    }

    //    Вернуть суммарную стоимость всех продуктов в базе данных (активных).
    public double getActiveProductsTotalCost() throws IOException {
        return getAllActiveProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    //    Вернуть среднюю стоимость продукта в базе данных (из активных).
    public double getActiveProductsAveragePrice() throws IOException {
        // 1 способ
//        return getAllActiveProducts()
//                .stream()
//                .mapToDouble(Product::getPrice)
//                .average()
//                .orElse(0.0);

        // 2 способ
        int productCount = getActiveProductsCount();

        if (productCount == 0) {
            return 0.0;
        }

        return getActiveProductsTotalCost() / productCount;
    }
}
