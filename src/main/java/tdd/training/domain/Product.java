package tdd.training.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class Product {

    public static final Product NO_PRODUCT = new Product("No product", Price.fromCents(0));

    private final String name;
    private final Price price;

    public Product(String name, Price price) {
        this.name = name;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }
}
