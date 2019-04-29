import java.rmi.Remote;

/**
 * Interfejs zdalnego systemu realizacji zadaĹ.
 */
public interface ExecutorServiceInterface extends Remote {
    /**
     * Liczba zadaĹ, ktĂłre serwis moĹźe jednoczeĹnie realizowaÄ.
     *
     * @return maksymalna liczba zadaĹ, ktĂłre jednoczeĹnie moĹźe realizowaÄ dany
     *         serwis.
     */
    public int numberOfTasksAllowed();

    /**
     * Metoda wykonujÄc obliczenia. MoĹźna jÄ wykonywaÄ wspĂłĹbieĹźnie. MaksymalnÄ
     * liczbÄ jednoczesnych uĹźyÄ moĹźna poznaÄ poprzez metodÄ numberOfTasksAllowed.
     * Metoda blokuje wywoĹujÄcy jÄ wÄtek na czas niezbÄdny do wykonania zadania.
     *
     * @param task zadanie do wykonania
     * @return rezultat pracy
     */
    public long execute(TaskInterface task);
}