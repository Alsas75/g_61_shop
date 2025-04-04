package app.domain;

import java.util.Objects;

public class Product {

    private int id;
    private String title;
    private double price;
    private boolean active;

    //пустой конструктор необходим фреймворку jackson
    public Product() {
    }

    public Product(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public Product(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public Product(int id, String title, double price, boolean active) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(price, product.price) == 0 && active == product.active && Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, active);
    }

    @Override
    public String toString() {
        return String.format("Продукт: ид - %d, наименование - %s, цена - %.2f, активен - %b.",
                id, title, price, active);
    }
}
