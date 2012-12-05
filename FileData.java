import java.util.ArrayList;
import java.util.List;

public class FileData {
    // A list of data attributes from the file.
    private List<Object> data;

    /**
     * Initialises a new FileData object.
     *
     * Preconditions: None.
     * Postconditions: A default-valued FileData object will be created.
     */
    public FileData() {
        data = new ArrayList<Object>();
    }

    /**
     * Adds an object to the list of data items.
     *
     * Preconditions: The object passed in cannot be null.
     * Postconditions: The object will be added to the list of data items.
     */
    public void addData(Object obj) {
        if (obj != null) {
            data.add(obj);
        }

        return;
    }

    /**
     * Returns the entire list of objects.
     *
     * Preconditions: None.
     * Postconditions: The list of objects will be returned.
     */
    public List<Object> getAllData() {
        return data;
    }

    /**
     * Returns the object at the specified index in the list.
     *
     * Preconditions: The index must be in the bounds of the list.
     * Postconditions: The object at the specified index will be returned.
     */
    public Object getData(int index) {
        // Make sure the index passed in is reasonable.
        if (index >= 0 && index < data.size()) { 
            return (data.get(index));
        }

        return null;
    }

    /**
     * Returns the index of the specified object in the list, or if the object
     * is null or not in the list, -1 is returned.
     *
     * Preconditions: The object specified cannot be null, and must be in the list.
     * Postconditions: The index of the object in the list will be returned.
     */
    public int indexOf(Object obj) {
        // Make sure the object passed in is not null.
        if (obj != null) {
            return (data.indexOf(obj));
        }

        return -1;
    }

    /**
     * Returns the size of the list of data items.
     *
     * Preconditions: None.
     * Postconditions: The number of elements in the data list will be returned.
     */
    public int numItems() {
        return (data.size());
    }
}