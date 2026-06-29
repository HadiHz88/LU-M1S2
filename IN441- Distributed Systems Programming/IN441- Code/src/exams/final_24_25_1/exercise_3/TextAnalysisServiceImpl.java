package exams.final_24_25_1.exercise_3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TextAnalysisServiceImpl extends UnicastRemoteObject implements TextAnalysisService {

    protected TextAnalysisServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int countVowels(String input) throws RemoteException {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        int count = 0;
        String lowerInput = input.toLowerCase();
        for (int i = 0; i < lowerInput.length(); i++) {
            if (VOWELS.contains(lowerInput.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int countConsonants(String input) throws RemoteException {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        int count = 0;
        String lowerInput = input.toLowerCase();
        for (int i = 0; i < lowerInput.length(); i++) {
            if (CONSONANTS.contains(lowerInput.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    private static final List<Character> VOWELS = List.of('a', 'e', 'i', 'o', 'u');

    private static final List<Character> CONSONANTS = List.of(
            'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'
    );
}