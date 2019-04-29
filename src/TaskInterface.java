import java.io.Serializable;

/**
 * Interfejs reprezentujÄcy zadanie obliczeniowe.
 */
public interface TaskInterface extends Serializable {
    /**
     * Metoda zwraca unikalny identyfikator zadania.
     *
     * @return unikalny identyfikator zadania.
     */
    public long taskID();
}