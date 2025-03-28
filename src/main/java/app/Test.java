package app;

import app.domain.Customer;
import app.domain.Product;
import app.repository.CustomerRepository;
import app.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {
    //тренировка с фреймверком jackson
//    public static void main(String[] args) {
//
//        Product product = new Product();
//        product.setId(7);
//        product.setTitle("Банан");
//        product.setPrice(130);
//        product.setActive(true);
//
//        System.out.println(product);
//
//        //mapper
//        ObjectMapper mapper = new ObjectMapper();
//
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        File file = new File("database/product.txt");
//        try {
//            //записывает в
//            mapper.writeValue(file, product);
//
//            //читаем JSON
//            Product productFromFile = mapper.readValue(file, Product.class);
//            System.out.println("Прочитанный из файла продукт");
//            System.out.println(productFromFile);
//
//            //
//            List<Product> products = List.of(
//                new Product(1,"Яблоко", 75, true),
//                new Product(2,"Банан", 120, true),
//                new Product(3,"Апельсин", 230, true)
//            );
//
//            mapper.writeValue(file, products);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static void main(String[] args) throws IOException {
//        ProductRepository repository = new ProductRepository();
//

    //        Product product = new Product(77, "Персик", 290, true);
    //       repository.save(product);
//        System.out.println("Все продукты из БД:");
//        repository.findAll().forEach(System.out::println);
//
//        Product product = repository.findById(2);
//        System.out.println("Полученный продукт с ИД 2");
//        System.out.println(product);
//
//        product = repository.findById(10);
//        System.out.println("Полученный продукт с ИД 10");
//        System.out.println(product);
//
//        product = new Product();
//        product.setId(3);
//        product.setPrice(240);
//
//        repository.update(product);
//
//        System.out.println(repository.findById(3));
//
//        repository.deleteById(3);


    //    }
    public static void main(String[] args) throws IOException {

        CustomerRepository repository = new CustomerRepository();

        Customer customer1 = new Customer();
        customer1.setName("Вася");
        customer1.setActive(true);

        Customer customer2 = new Customer();
        customer2.setName("Петя");
        customer2.setActive(true);

        Customer customer3 = new Customer();
        customer3.setName("Дима");
        customer3.setActive(true);

        repository.save(customer1);
        repository.save(customer2);
        repository.save(customer3);

    }
}