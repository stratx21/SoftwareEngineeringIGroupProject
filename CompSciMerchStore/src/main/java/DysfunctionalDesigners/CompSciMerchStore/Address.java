package DysfunctionalDesigners.CompSciMerchStore;

public class Address {
    private String street;
    private int zipCode;
    private  String city;
    private String state;
    
    
    public String getStreet() { return street; }
    public int getZipCode() { return zipCode; }
    public String getCity() { return city; }
    public String getState() { return state; }
    
    public void setStreet(String street) { this.street = street; }
    public void setZipCode(int zipCode) { this.zipCode = zipCode; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }

    /**
	 * Jackson Json parser requires this
	 */
    public Address() {}

    /**
     * Constructor to make and address
     * @param street Street name
     * @param zipCode Zip code
     * @param city City for address
     * @param state State
     */
    public Address(String street, int zipCode, String city, String state) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", zipCode=" + zipCode +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    //TODO: not sure if we need these but I generated them just incase.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (zipCode != address.zipCode) return false;
        if (!street.equals(address.street)) return false;
        if (!city.equals(address.city)) return false;
        return state.equals(address.state);
    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + zipCode;
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }
}
