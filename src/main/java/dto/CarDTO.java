package dto;

import entities.Car;
import java.util.ArrayList;
import java.util.List;

public class CarDTO {
    
    private final Long id;
    private final int produced;
    private final String make;
    private final String model;
    private final int price;
    private final String colour;
    
    public CarDTO(Car car) {
        this.id = car.getId();
        this.produced = car.getProduced();
        this.make = car.getMake();
        this.model = car.getModel();
        this.price = car.getPrice();
        this.colour = car.getColour();
    }

    public Long getId() {
        return id;
    }

    public int getProduced() {
        return produced;
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

    public String getModel() {
        return model;
    }
    
    public static List<CarDTO> convertList(List<Car> list){
        List<CarDTO> result = new ArrayList<CarDTO>();
        for (Car car : list) {
            result.add(new CarDTO(car));
        }
        return result;
    }
}