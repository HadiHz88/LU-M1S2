package exams.final_24_25_1.exercise_3;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Server: Object creation");
            TextAnalysisServiceImpl _service = new TextAnalysisServiceImpl();

            System.out.println("Registering with RMIregistry...");
            Naming.rebind("rmi://localhost:1099/analysis", _service);

            System.out.println("Waiting for client calls...");
        } catch (Exception e) {
            System.out.println("Error binding object: " + e.toString());
        }
    }
}
