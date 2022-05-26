package table;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create json data.
 * @param <T>
 */
public class Repository<T> {

    /**
     * The type of the given element.
     */
    public Class<T> typeOfElement;
    /**
     * The given list of the elements.
     */
    public List<T> listOfElements;

    /**
     * Constructor of the Repository class.
     * @param elementType the type of the element
     */
    public Repository(Class<T> elementType) {
        this.typeOfElement = elementType;
        listOfElements = new ArrayList<>();
    }

    /**
     * Add element to the repository.
     * @param element the selected element
     */
    public void add(T element) {
        listOfElements.add(element);
    }

}
