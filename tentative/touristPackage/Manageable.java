package touristPackage;

/**
 * Interface for entities that support CRUD-style management.
 * Implemented by Dining, TourGuide, TourPackage — demonstrates interface-based polymorphism.
 */
public interface Manageable {
    void add();
    void view();
    void delete();
    String getDetails();
}
