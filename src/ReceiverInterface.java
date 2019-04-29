import java.rmi.Remote;

/**
 * Interfejs systemu odbioru wynikĂłw obliczeĹ.
 */
public interface ReceiverInterface extends Remote {
    /**
     * Metoda, do ktĂłrej nadsyĹany jest wynik obliczeĹ. Wyniki obliczeĹ mogÄ byÄ
     * nadsyĹane wyĹÄcznie sekwencyjnie. WspĂłĹbieĹźne uĹźycie metody spowoduje bĹÄd.
     *
     * @param taskID numer zadania - moĹźna go poznaÄ za pomocÄ metody
     *               TaskInterface.taskID()
     * @param result wynik obliczeĹ wykonanych za pomocÄ metody
     *               ExecutorServiceInterface.execute()
     */
    public void result(long taskID, long result);
}