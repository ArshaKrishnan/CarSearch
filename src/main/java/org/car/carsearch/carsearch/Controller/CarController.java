package org.car.carsearch.carsearch.Controller;

import com.sun.net.httpserver.Headers;
import lombok.extern.slf4j.Slf4j;
import org.car.carsearch.carsearch.Entity.Car;
import org.car.carsearch.carsearch.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping("/cars")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;


    /**
    function used to search cars by
    @params
    length
    velocity
    weight
    & color
     **/
    @GetMapping(path = "/searchCarsByVelocityLengthWeight")
    public ResponseEntity<?> searchCarByVelocityLengthWeight(
            @RequestParam double length,
            @RequestParam double weight,
            @RequestParam double velocity,
            @RequestParam(defaultValue = "") String color
    ) throws JAXBException {

        try {
            List<Car> cars = carService.searchCarsByVelocityLengthWeight(length, weight, velocity, color);
            if (cars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found matching the criteria.");
            }
            ByteArrayResource xmlResource = carService.convertCarsToXml(cars);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(xmlResource);


        } catch (JAXBException e) {
            log.error("Error generating XML: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating XML");
        } catch (Exception e) {
            log.error("Error occurred while fetching car details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching car details");
        }
    }
    /**
     function will return all cars available in db

     **/
    @GetMapping(path = "/searchCars")
    public ResponseEntity<?> getAllCars() throws JAXBException {

        try {
            List<Car> cars = carService.finAllCars();
            if (cars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found matching the criteria.");
            }
            ByteArrayResource xmlResource = carService.convertCarsToXml(cars);

            HttpHeaders headers = setHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(xmlResource);


        } catch (JAXBException e) {
            log.error("Error generating XML: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating XML");
        } catch (Exception e) {
            log.error("Error occurred while fetching car details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching car details");
        }
    }

    /**
     function used to search cars by price range and other details
     @params
     minprice
     maxprice
     brand
     & type
     **/

    @GetMapping("/searchByPriceBrandType")
    public ResponseEntity<?> searchCarsByPriceAndBrandAndType(
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam(defaultValue = "") String brand,
            @RequestParam(defaultValue = "") String type) throws JAXBException {
        try {
            List<Car> cars = carService.searchCarsByCriteria(minPrice, maxPrice, brand, type);
            if (cars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found matching the criteria.");
            }
            ByteArrayResource xmlResource = carService.convertCarsToXml(cars);
            HttpHeaders headers = setHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(xmlResource);

        } catch (JAXBException e) {
            log.error("Error generating XML: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating XML");
        } catch (Exception e) {
            log.error("Error occurred while fetching car details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching car details");
        }
    }
    /**
     function used to search cars by
     @params
    color
     **/

    @GetMapping("/searchByColor")
    public ResponseEntity<?> searchCarsByColor(@RequestParam(required = false) String color) {
        try {
            List<Car> cars = carService.searchCarsByColor(color);
            if (cars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found matching the criteria.");
            }
            ByteArrayResource xmlResource = carService.convertCarsToXml(cars);

            HttpHeaders headers = setHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(xmlResource);


        } catch (JAXBException e) {
            log.error("Error generating XML: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating XML");
        } catch (Exception e) {
            log.error("Error occurred while fetching car details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching car details");
        }
    }
    /**
     function used to search cars by price range
     @params
     minprice
     maxprice

     **/
    @GetMapping("/searchByPriceRange")
    public ResponseEntity<?> searchCarsByPriceRange(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        try {
            List<Car> cars = carService.searchCarsByPriceRange(minPrice, maxPrice);
            if (cars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found within the specified price range.");
            }
            ByteArrayResource xmlResource = carService.convertCarsToXml(cars);

            HttpHeaders headers = setHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(xmlResource);


        } catch (JAXBException e) {
            log.error("Error generating XML: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating XML");
        } catch (Exception e) {
            log.error("Error occurred while fetching car details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching car details");
        }

    }
    /**
     function used to search cars by price
     @params
     price
     **/
    @GetMapping("/searchByPrice")
    public ResponseEntity<?> searchCarsByPrice(@RequestParam(required = false) Double price) {
        try {
            List<Car> cars = carService.searchCarsByPrice(price);
            if (cars.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found with the specified price.");
            }
            ByteArrayResource xmlResource = carService.convertCarsToXml(cars);

            HttpHeaders headers =setHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(xmlResource);


        } catch (JAXBException e) {
            log.error("Error generating XML: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating XML");
        } catch (Exception e) {
            log.error("Error occurred while fetching car details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching car details");
        }
    }

    /**
     function used to save new  car details

     **/
    @PostMapping("/saveCar")
    public ResponseEntity<?> createCar(@RequestBody Car car) {

        if (car == null ||
                car.getColor() == null ||
                car.getBrand() == null ||
                car.getType() == null ||
                car.getPrice() <= 0 ||
                car.getLength() <= 0 ||
                car.getWeight() <= 0 ||
                car.getVelocity() <= 0) {

            return ResponseEntity.badRequest().body("Null values..!");
        }

            Car savedCar = carService.createCar(car);
        log.info(savedCar.toString());
            try {
                ByteArrayResource xmlResource = carService.convertCarToXml(savedCar);
                HttpHeaders headers =setHeaders();

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(xmlResource);

            } catch (JAXBException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating XML");
            }
        }
    private HttpHeaders setHeaders() {
        HttpHeaders headers= new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");
        return headers;
    }
    }







