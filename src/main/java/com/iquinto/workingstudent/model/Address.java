package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iquinto.workingstudent.model.enums.Province;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "city", nullable = false, length = 75)
    private String city;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "province", nullable = false, length = 75)
    private Province province;

    @Column(name = "zipcode", nullable = false, length = 5)
    private String zipcode;

    @Column(name = "country", nullable = false, length = 50)
    private String country = "España";

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private User user;

    public Address(String street, String city, String zipcode, String province) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.province = Province.fromString(province);
    }

    public Address() {
    }

    public String getProvince() {
        return this.province.getText();
    }

    public void setProvince(String province) {
         this.province = Province.fromString(province);
    }

    @Override
    public String toString() {
        return " [" +
                "  province:" + this.getProvince() +
                "  country: " + this.getCountry() +
                "]" ;
    }
}
