package com.astrocode.api.shared.vo;

import com.astrocode.api.shared.exception.VOException;

import java.util.Objects;

public class Location {

    private final String street;
    private final String number;
    private final String district;
    private final String city;
    private final String state;

    private Location(String street, String number, String district, String city, String state) {
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.state = state;
    }

    public static Location of(String street, String number, String district, String city, String state){
        if (street == null || street.isBlank()){
            throw VOException.required("Location", "street");
        }

        if (number == null || number.isBlank()) {
            throw VOException.required("Location", "number");
        }

        if (district == null || district.isBlank()) {
            throw VOException.required("Location", "district");
        }

        if (city == null || city.isBlank()) {
            throw VOException.required("Location", "city");
        }

        if (state == null || state.isBlank()) {
            throw VOException.length("Location", "state", 2);
        }

        return new Location(
                street.trim(),
                number.trim(),
                district.trim(),
                city.trim(),
                state.trim().toUpperCase()
        );
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(street, location.street) && Objects.equals(number, location.number) && Objects.equals(district, location.district) && Objects.equals(city, location.city) && Objects.equals(state, location.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, district, city, state);
    }

    @Override
    public String toString() {
        return "Location{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
