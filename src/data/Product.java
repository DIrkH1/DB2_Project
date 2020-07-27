package data;

import javax.xml.namespace.QName;

public class Product {

    private Integer id;
    private String name;
    private String material;
    private String description;
    private Double price;

    public Product(Integer id, String name, String material, String description, Double netPrice){
        this.id =id;
        this.name = name;
        this.material = material;
        this.description = description;
        this.price = netPrice;
    }

    public Product(Product product){
        this.id =id;
        this.name = name;
        this.material = material;
        this.description = description;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }
}
