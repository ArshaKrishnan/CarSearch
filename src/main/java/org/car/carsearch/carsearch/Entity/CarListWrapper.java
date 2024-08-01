package org.car.carsearch.carsearch.Entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
public class CarListWrapper {
    private List<Car> cars;

    // Default no-arg constructor
    public CarListWrapper() {}

    public CarListWrapper(List<Car> cars) {
        this.cars = cars;
    }

    @XmlElement(name = "car")
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}

