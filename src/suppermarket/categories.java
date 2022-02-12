
package suppermarket;

/**
 *
 * @author lukogo
 */
public class categories {
    
    int id;
    String category, status;

    public categories(int id, String categories, String status) {
        this.id = id;
        this.category = categories;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategories(String categories) {
        this.category = categories;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategories() {
        return category;
    }

    public String getStatus() {
        return status;
    }
    
}
