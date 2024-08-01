package org.car.carsearch.carsearch.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "car_details")
@XmlRootElement(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double length;
    private double weight;
    private double velocity;
    private String color;
    private double price;
    private String brand;
    private String type;

    // Getters and setters
}
