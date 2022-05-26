package table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Represents the gSon repository.
 * @param <T>
 */
public class gSonRepository<T> extends Repository<T> {

    /**
     * Create the GSON builder.
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Constructor of the gSonRepository.
     * @param elementType the type of the given element
     */
    public gSonRepository(Class<T> elementType) {
        super(elementType);
    }

    /**
     * Function to load json data from a json file.
     * @param file the file where the data is stored
     * @throws IOException if something goes wrong
     */
    public void loadFromFile(File file) throws IOException {
        try (var reader = new FileReader(file)) {
            var listType = TypeToken.getParameterized(List.class, typeOfElement).getType();
            listOfElements = GSON.fromJson(reader, listType);
        }
    }

    /**
     * Function to save to json file.
     * @param file the file where the data will be stored
     * @throws IOException if something goes wrong
     */
    public void saveToFile(File file) throws IOException {
        try (var writer = new FileWriter(file)) {
            GSON.toJson(listOfElements, writer);
        }
    }
}
