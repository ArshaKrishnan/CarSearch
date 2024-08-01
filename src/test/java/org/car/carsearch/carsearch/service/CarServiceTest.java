package org.car.carsearch.carsearch.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.xml.bind.JAXBException;
import org.car.carsearch.carsearch.Entity.Car;
import org.car.carsearch.carsearch.Repository.CarRepository;
import org.car.carsearch.carsearch.Service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchCarsByVelocityLengthWeight() {
        Car car = new Car();
        car.setId(1L);
        car.setLength(4.5);
        car.setWeight(1500.0);
        car.setVelocity(220.0);
        car.setColor("Red");
        car.setPrice(20000.0);
        car.setBrand("Toyota");
        car.setType("Sedan");
        List<Car> cars = Arrays.asList(car);
        when(carRepository.findByLengthAndWeightAndVelocityAndColor(4.5, 1500, 220, "Red")).thenReturn(cars);

        List<Car> result = carService.searchCarsByVelocityLengthWeight(4.5, 1500, 220, "Red");

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
    }

    @Test
    public void testFindAllCars() {
        Car car = setCarDetails();


        List<Car> cars = Arrays.asList(car);
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.finAllCars();

        assertEquals(1, result.size());
        assertTrue(result.contains(car));

    }
    @Test
    public void testSearchCarsByCriteria() {
        Car car = setCarDetails();
        List<Car> cars = Arrays.asList(car);
        when(carRepository.findByPriceBetweenAndBrandAndType(15000, 25000, "Toyota", "Sedan")).thenReturn(cars);

        List<Car> result = carService.searchCarsByCriteria(15000, 25000, "Toyota", "Sedan");

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
    }
    @Test
    public void testSearchCarsByColor() {
        Car car = setCarDetails();
        List<Car> cars = Arrays.asList(car);
        when(carRepository.findByColor("Red")).thenReturn(cars);

        List<Car> result = carService.searchCarsByColor("Red");

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
    }
    @Test
    public void testSearchCarsByPrice() {
        Car car = setCarDetails();
        List<Car> cars = Arrays.asList(car);
        when(carRepository.findByPrice(20000D)).thenReturn(cars);

        List<Car> result = carService.searchCarsByPrice(20000);

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
    }
    @Test
    public void testSearchCarsByPriceRange() {
        Car car = setCarDetails();
        List<Car> cars = Arrays.asList(car);
        when(carRepository.findByPriceRange(15000.0, 25000.0)).thenReturn(cars);

        List<Car> result = carService.searchCarsByPriceRange(15000.0, 25000.0);

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
    }
    @Test
    public void testConvertCarsToXml() throws JAXBException, javax.xml.bind.JAXBException {
        Car car = setCarDetails();
        List<Car> cars = Arrays.asList(car);

        ByteArrayResource xmlResource = carService.convertCarsToXml(cars);
        assertNotNull(xmlResource);
        assertTrue(xmlResource.contentLength() > 0);
        String xmlString = new String(xmlResource.getByteArray());
        assertTrue(xmlString.contains("<car>"));
        assertTrue(xmlString.contains("<brand>Toyota</brand>"));
    }

    @Test
    public void testCreateCar() {
        Car car = setCarDetails();
        when(carRepository.findByBrandAndTypeAndColorAndLength("Toyota", "Sedan", "Red", 4.5)).thenReturn(Optional.empty());
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.createCar(car);

        assertNotNull(result);
        assertEquals(car, result);
    }


    private Car setCarDetails(){
        Car car = new Car();
        car.setId(1L);
        car.setLength(4.5);
        car.setWeight(1500.0);
        car.setVelocity(220.0);
        car.setColor("Red");
        car.setPrice(20000.0);
        car.setBrand("Toyota");
        car.setType("Sedan");
        return car;
    }


}
