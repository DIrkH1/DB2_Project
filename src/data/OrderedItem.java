package data;

public class OrderedItem extends Product {

    private Integer orderAmount;
    public OrderedItem(Product product){
        super(product);
        this.orderAmount = 1;
    }

    public boolean equals(Product product){
        return this.getId().equals(product.getId());
    }

    public void incOrder(){
        this.orderAmount = this.orderAmount + 1;
    }

    public void decOrder(){
        this.orderAmount = this.orderAmount - 1;
    }

    public Integer getOrderAmount(){
        return this.orderAmount;
    }

    public String getSQL(){
        return getId() + ", '" + getName() + " '" + orderAmount;
    }

}
