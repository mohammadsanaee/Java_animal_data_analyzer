package space.harbour;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import space.harbour.domain.Animal;
import space.harbour.util.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Insert the file path");
        String filePath = keyboard.nextLine();
        while (true){
            System.out.println("Animal analyzer");
            try {
                List<Animal> animalData = FileUtils.loadAnimalData(filePath);
                System.out.println("0. Animals for a specific race");
                System.out.println("1. All races");
                System.out.println("2, Search for a specific animal by name");
                System.out.println("3. Total weight for a specific race");
                System.out.println("4. Add a new animal");
                System.out.println("5. Remove an animal by id");
                System.out.println("6. Remove all animals of a race");
                System.out.println("7. Average speed for a specific race");
                System.out.println("8. Total weight");
                System.out.println("9. Exit");
                String choice = keyboard.nextLine();

                switch (choice){
                    case "0" -> {
                        System.out.println("Which race?");
                        String inputRace = keyboard.nextLine();
                        List<Animal> animalList = animalData.stream()
                                .filter(Animal -> Animal.getRace().equals(inputRace))
                                .toList();
                        animalList.forEach(System.out::println);


                    }
                    case "1" -> {
                        List<String> animalList = animalData.stream()
                                .map(Animal::getRace)
                                .distinct()
                                .toList();
                        System.out.println();


                    }case "2" -> {
                        System.out.println("Input the animal name to search");
                        String inputName = keyboard.nextLine();
                        try {
                            List<Animal> animalList = animalData.stream()
                                    .filter(Animal -> Animal.getName().equals(inputName))
                                    .toList();
                            if(animalList.isEmpty()){
                                throw new NoSuchElementException("Such animal does not exist.");
                            }
                            animalList.forEach(System.out::println);
                        }

                        catch (NoSuchElementException e){
                            System.out.println(e.getMessage());
                        }

                    }case "3" -> {
                        System.out.println("which race?");
                        String inputRace = keyboard.nextLine();
                        int totalWeight = animalData.stream()
                                .filter(Animal -> Animal.getRace().equals(inputRace))
                                .mapToInt(Animal::getWeight).sum();
                        System.out.println(totalWeight);
                    }case "4" -> {
                        System.out.println("Animal's name?");
                        String name = keyboard.nextLine();
                        System.out.println("Animal's race?");
                        String race = keyboard.nextLine();
                        System.out.println("Animal's weight?");
                        int weight = Integer.parseInt(keyboard.nextLine());
                        System.out.println("Animal's maxSpeed?");
                        int maxSpeed = Integer.parseInt(keyboard.nextLine());
                        try {
                            if (name.isEmpty() || race.isEmpty() || weight <=0 || maxSpeed<=0 ){
                                throw new IllegalAccessException("All arguments are mandatory. weight and speed should be positive integers");
                        }
                        }catch (IllegalAccessException e){
                            System.out.println(e.getMessage());
                        }
//                        do {
//                            String id = UUID.randomUUID().toString();
//                        }while (animalData.stream().anyMatch(animal -> animal.getId().equals(id))){
//                            id = UUID.randomUUID().toString();
//                        }
                        String id = UUID.randomUUID().toString();


                        Animal animal = new Animal(id,name,race,weight,maxSpeed);
                        animalData.add(animal);
                        try (FileWriter writer = new FileWriter("animals.csv", true);
                             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                            printer.println();
                            printer.printRecord(animal.getId(), animal.getName(), animal.getRace(), animal.getWeight(), animal.getMaxSpeed());
                            System.out.println("Animal added successfully!");
                        } catch (IOException e) {
                            System.out.println("Error writing to file: " + e.getMessage());
                        }

                    }case "5" -> {
                        System.out.println("Enter the animal id to remove");
                        String idToRemove = keyboard.nextLine();
                        boolean removed = animalData.removeIf(animal -> animal.getId().equals(idToRemove));
                        if (removed) {
                            System.out.println("Animal removed successfully!");
                            try (Writer writer = new FileWriter("animals.csv");
                                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                                         .withHeader("id", "name", "race", "weight", "maxSpeed"))) {
                                for (Animal animal : animalData) {
                                    csvPrinter.printRecord(animal.getId(), animal.getName(),
                                            animal.getRace(), animal.getWeight(), animal.getMaxSpeed());
                                }
                                csvPrinter.flush();
                            } catch (IOException e) {
                                System.out.println("Failed to save");
                            }
                        } else {
                            System.out.println("id not found");
                        }

                    }case "6" -> {
                        System.out.println("Enter the animal race to remove");
                        String raceToRemove = keyboard.nextLine();
                        boolean removed = animalData.removeIf(animal -> animal.getRace().equals(raceToRemove));
                        if (removed) {
                            System.out.println("Animal removed successfully!");
                            try (Writer writer = new FileWriter("animals.csv");
                                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                                         .withHeader("id", "name", "race", "weight", "maxSpeed"))) {
                                for (Animal animal : animalData) {
                                    csvPrinter.printRecord(animal.getId(), animal.getName(),
                                            animal.getRace(), animal.getWeight(), animal.getMaxSpeed());
                                }
                                csvPrinter.flush();
                            } catch (IOException e) {
                                System.out.println("Failed to save");
                            }
                        } else {
                            System.out.println("id not found");
                        }

                    }case "7" -> {
                        System.out.println("enter the race to find the average speed");
                        String race = keyboard.nextLine();
                        double averageSpeed  = animalData.stream()
                                .filter(animal -> animal.getRace().equals(race))
                                .mapToDouble(Animal::getMaxSpeed).sum();
                        double numRace = animalData.stream()
                                .filter(animal -> animal.getRace().equals(race))
                                .count();
                        System.out.println(averageSpeed/numRace);

                    }case "8" -> {
                        int totalWeight = animalData.stream()
                                .mapToInt(Animal::getWeight).sum();
                        System.out.println(totalWeight);

                    }case "9" -> {
                        System.exit(0);

                    }

                }

            }
            catch (FileNotFoundException hi){
                System.out.println("File doesn't exist");
            } catch (IOException ioe){
                System.out.println("Error reading data");
            }


        }
    }
}