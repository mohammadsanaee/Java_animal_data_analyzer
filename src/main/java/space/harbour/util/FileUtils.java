package space.harbour.util;

import com.sun.source.tree.BreakTree;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import space.harbour.domain.Animal;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtils {
    public static List<Animal> loadAnimalData(String filePath) throws FileNotFoundException,
            IOException{
        Reader fileReader  = new FileReader(filePath);
        final String[] Headers = {"id", "name", "race", "weight", "maxSpeed"};

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(Headers)
                .setSkipHeaderRecord(true)
                .build();
        List<Animal> animalList = new ArrayList<>();
        Iterable<CSVRecord> records = csvFormat.parse(fileReader);
        for (CSVRecord record:records){
            String id = record.get("id");
            String name = record.get("name");
            String race = record.get("race");
            int weight = Integer.parseInt(record.get("weight"));
            int maxSpeed = Integer.parseInt(record.get("maxSpeed"));
            Animal animal = Animal.builder()
                    .id(id).name(name).race(race).weight(weight).maxSpeed(maxSpeed).build();
            animalList.add(animal);


        }
        return animalList;
    }


}
