package exams.final_24_25_2.exercise_3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Step 1: start the RMI registry from this JVM if it is not already running.
            Registry registry = startRegistry();

            // Step 2: create and register the library service.
            LibraryServiceImpl service = new LibraryServiceImpl();
            registry.rebind("LibraryServer", service);

            System.out.println("Library RMI server is running...");
        } catch (Exception e) {
            System.out.println("Error starting library server: " + e.toString());
        }
    }

    private static Registry startRegistry() throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on port 1099");
            return registry;
        } catch (RemoteException e) {
            System.out.println("RMI registry already running on port 1099");
            return LocateRegistry.getRegistry(1099);
        }
    }
}
