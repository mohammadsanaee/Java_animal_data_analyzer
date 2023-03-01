package space.harbour.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Animal {
    private String id;
    private String name;
    private String race;
    private int weight;
    private int maxSpeed;
    public Animal(String id, String name, String race, int weight, int maxSpeed) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
    }
}

