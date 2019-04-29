import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfejs systemu kolejkowania i rozdziaĹu zadaĹ.
 */
public interface TaskDispatcherInterface extends Remote {

    /**
     * Metoda pozwala na przekazanie do systemu nazwy serwisu, do ktĂłrego naleĹźy
     * odsyĹaÄ wyniki przeprowadzonych obliczeĹ. Serwis, do ktĂłrego wysyĹane sÄ
     * wyniki obliczeĹ jest takĹźe serwisem RMI - stÄd do jego lokalizacji wystarczy
     * znajomoĹÄ nazwy, pod ktĂłrÄ zostaĹ on zarejestrowany.
     *
     * @param name nazwa zdalnego serwisu
     */
    public void setReceiverServiceName(String name) throws RemoteException;

    /**
     * Metoda sĹuĹźy do wprowadzania zadaĹ do systemu. Metoda musi zawsze i
     * wspĂłĹbieĹźnie wprowadzaÄ zadania do systemu. System musi pozwalaÄ na dodawanie
     * nowych zadaĹ nawet w przypadku, gdy inne zadania sÄ wĹaĹnie realizowane.
     * Metoda nie moĹźe blokowaÄ pracy uĹźytkownika na czas znaczÄco dĹuĹźszy od
     * potrzebnego do umieszczenia zadania w kolejce.
     *
     * @param task                przekazywane zadanie
     * @param executorServiceName nazwa serwisu RMI, do ktĂłrego naleĹźy dostarczyÄ
     *                            zadanie celem jego wykonania.
     * @param priority            true oznacza, Ĺźe zadanie jest priorytetowe i
     *                            powinno byÄ wykonane moĹźliwie szybko. <br>
     *                            <br>
     *                            Niech serwis executorServiceName bÄdzie obciÄĹźony
     *                            pracÄ i wykonuje maksymalnÄ, dozwolonÄ liczbÄ
     *                            zadaĹ i niech wszystkie zadania dla tego serwisu
     *                            sÄ niepriorytetowe. JeĹli metoda addTask, ktĂłra
     *                            wprowadza do serwisuÂ nowe zadanie dla tego samego
     *                            executorServiceName zakoĹczy siÄ przed koĹcem
     *                            pracy metod ExecutorServiceInterface.execute to
     *                            jeĹli nowe zadanie jest priorytetowe, to musi
     *                            zostaÄ uruchomione jako zadanie nastÄpne. JeĹli
     *                            jest to zadanie zwykĹe to moĹźe zostaÄ uruchomione
     *                            w dowolnej kolejnoĹci. JeĹli w serwise sÄ inne
     *                            zadanie priorytetowe to nowe zadanie priorytetowe
     *                            moĹźe zostaÄ wykonane w dowolnej kolejnoĹci ale
     *                            przed zadaniami zwykĹymi. <br>
     *                            false oznacza zadanie zwykĹe
     */
    public void addTask(TaskInterface task, String executorServiceName, boolean priority) throws RemoteException;

}