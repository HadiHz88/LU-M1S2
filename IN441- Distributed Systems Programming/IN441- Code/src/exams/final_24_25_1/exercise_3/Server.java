package exams.final_24_25_1.exercise_3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Step 1: start the RMI registry from this JVM if it is not already running.
            Registry registry = startRegistry();

            // Step 2: create the remote object.
            System.out.println("Server: Object creation");
            TextAnalysisServiceImpl _service = new TextAnalysisServiceImpl();

            // Step 3: bind the remote object in the registry.
            System.out.println("Registering with RMIregistry...");
            registry.rebind("analysis", _service);

            System.out.println("Waiting for client calls...");
        } catch (Exception e) {
            System.out.println("Error binding object: " + e.toString());
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
