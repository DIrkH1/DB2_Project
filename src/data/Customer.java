package data;

public class Customer {
    private Integer id;
    private String surname;
    private String name;
    private String address;
    private String city;
    private String country;
    private String company;

    public Customer(Integer id, String surname, String name, String address, String city, String country, String company){
        this.id =id;
        this.surname = surname;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.company = company;
    }

    public Customer(Customer customer){
        this.id =id;
        this.surname = surname;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
