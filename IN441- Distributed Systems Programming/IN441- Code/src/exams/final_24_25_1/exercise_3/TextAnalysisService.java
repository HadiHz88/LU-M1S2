package exams.final_24_25_1.exercise_3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TextAnalysisService extends Remote {
    public int countVowels(String input) throws RemoteException;
    public int countConsonants(String input) throws RemoteException;
}
