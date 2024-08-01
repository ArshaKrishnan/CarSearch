package org.car.carsearch.carsearch.Service;

import lombok.extern.slf4j.Slf4j;
import org.car.carsearch.carsearch.Entity.Car;
import org.car.carsearch.carsearch.Entity.CarListWrapper;
import org.car.carsearch.carsearch.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> searchCarsByVelocityLengthWeight(double length, double weight, double velocity, String color) {
        return carRepository.findByLengthAndWeightAndVelocityAndColor(length, weight, velocity, color);
    }

    public List<Car> finAllCars() {
        return carRepository.findAll();
    }

    public List<Car> searchCarsByCriteria(double minPrice, double maxPrice, String brand, String type) {

            return carRepository.findByPriceBetweenAndBrandAndType(minPrice, maxPrice, brand, type);

    }

    public List<Car> searchCarsByColor(String color) {
        return carRepository.findByColor(color);
    }

    public List<Car> searchCarsByPrice(double price) {
        return carRepository.findByPrice(price);
    }

    public List<Car> searchCarsByPriceRange(Double minPrice, Double maxPrice) {
        return carRepository.findByPriceRange(minPrice, maxPrice);
    }

    /**
     Method used to convert the Entity details to XML format

     **/
    public ByteArrayResource convertCarsToXml(List<Car> cars) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CarListWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        CarListWrapper wrapper = new CarListWrapper(cars);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(wrapper, outputStream);
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public Car createCar(Car car) {
        // Ensure car is not null and has valid attributes
        Optional.ofNullable(car)
                .filter(c -> c.getColor() != null && c.getBrand() != null && c.getType() != null)
                .filter(c -> c.getPrice() > 0 && c.getLength() > 0 && c.getWeight() > 0 && c.getVelocity() > 0)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car details"));
        Optional<Car> existingCar = carRepository.findByBrandAndTypeAndColorAndLength(
                car.getBrand(), car.getType(), car.getColor(), car.getLength()
        );

        if (existingCar.isPresent()) {
            throw new IllegalArgumentException("Car with brand " + car.getBrand() +
                    ", type " + car.getType() +
                    ", color " + car.getColor() +
                    ", and length " + car.getLength() +
                    " already exists.");
        }

        return carRepository.save(car);
    }

    public ByteArrayResource convertCarToXml(Car car) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Car.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(car, outputStream);

        return new ByteArrayResource(outputStream.toByteArray());
    }
}