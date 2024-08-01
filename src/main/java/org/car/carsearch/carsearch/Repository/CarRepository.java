package org.car.carsearch.carsearch.Repository;

import org.car.carsearch.carsearch.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByLengthAndWeightAndVelocityAndColor(double length, double weight, double velocity, String color);

    List<Car> findByPriceBetweenAndBrandAndType(double minPrice, double maxPrice, String brand, String type);

    @Query("SELECT c FROM Car c WHERE (:color IS NULL OR c.color = :color)")
    List<Car> findByColor(@Param("color") String color);

    @Query("SELECT c FROM Car c WHERE (:price IS NULL OR c.price = :price)")
    List<Car> findByPrice(@Param("price") Double price);

    @Query("SELECT c FROM Car c WHERE (:minPrice IS NULL OR c.price >= :minPrice) AND (:maxPrice IS NULL OR c.price <= :maxPrice)")
    List<Car> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    Optional<Car> findByBrandAndTypeAndColorAndLength(String brand, String type, String color, double length);
}
