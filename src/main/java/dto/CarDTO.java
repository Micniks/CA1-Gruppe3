package dto;

import entities.Car;
import java.util.ArrayList;
import java.util.List;

public class CarDTO {
    
    private Long id;
    private int year;
    private String make;
    private int price;
    private String colour;
    private String owner;
    
    public CarDTO(Car car) {
        this.id = car.getId();
        this.year = car.getYear();
        this.make = car.getMake();
        this.price = car.getPrice();
        this.colour = car.getColour();
        this.owner = car.getOwner();
    }

    public Long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public int getPrice() {
        return price;
    }

    public String getColour() {
        return colour;
    }

    public String getOwner() {
        return owner;
    }
    
    public static List<CarDTO> convertList(List<Car> list){
        List<CarDTO> result = new ArrayList<CarDTO>();
        
        for (Car car : list) {
            result.add(new CarDTO(car));
        }
        return result;
    }
}