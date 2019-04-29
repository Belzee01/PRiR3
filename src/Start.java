import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Start {

    public static void main(String[] args) throws RemoteException {
        String name = "rmi:TaskDispatcher";
        TaskDispatcherInterface engine = new Server();
        TaskDispatcherInterface stub =
                (TaskDispatcherInterface) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind(name, stub);
    }

    public static class Server implements TaskDispatcherInterface {
        private ReceiverInterface receiver;

        private Map<String, Worker> consumers;

        public Server() {
            this.consumers = new HashMap<>();
        }

        @Override
        public void setReceiverServiceName(String name) {
            receiver = (ReceiverInterface) Helper.connect("rmi://localhost/" + name);
        }

        @Override
        public void addTask(TaskInterface task, String executorServiceName, boolean priority) {
            synchronized (this) {
                if (!consumers.containsKey(executorServiceName)) {
                    Worker thread = new Worker(executorServiceName, receiver);
                    thread.setDaemon(true);
                    consumers.put(executorServiceName, thread);
                    thread.start();
                } else {
                    consumers.get(executorServiceName).add(new TaskPriority(task, priority));
                }
            }
        }

        public static class Worker extends Thread {
            private ExecutorServiceInterface es;
            private int currentCount;
            private ReceiverInterface receiver;
            private PriorityQueue<TaskPriority> queue;

            public Worker(String serviceName, ReceiverInterface receiver) {
                this.receiver = receiver;
                this.currentCount = 0;
                this.es = (ExecutorServiceInterface) Helper.connect("rmi://localhost/" + serviceName);
                this.queue = new PriorityQueue<>(TaskPriority.priorityComparator);
            }

            public void add(TaskPriority task) {
                synchronized (this) {
                    queue.add(task);
                    currentCount++;
                }
            }

            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (currentCount <= es.numberOfTasksAllowed()) {
                            TaskPriority task = queue.poll();
                            long result = es.execute(task.getTaskInterface());
                            receiver.result(task.getTaskInterface().taskID(), result);
                            currentCount--;
                        }
                    }
                }
            }
        }

        public static class TaskPriority {
            private TaskInterface taskInterface;
            private boolean priority;

            public TaskPriority(TaskInterface taskInterface, boolean priority) {
                this.taskInterface = taskInterface;
                this.priority = priority;
            }

            public TaskInterface getTaskInterface() {
                return taskInterface;
            }

            public boolean isPriority() {
                return priority;
            }

            public static Comparator<TaskPriority> priorityComparator = (p1, p2) -> {
                boolean priority0 = p1.isPriority();
                boolean priority1 = p2.isPriority();

                return Boolean.compare(priority0, priority1);
            };
        }
    }
}
