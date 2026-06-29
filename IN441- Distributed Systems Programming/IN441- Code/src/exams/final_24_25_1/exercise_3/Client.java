package exams.final_24_25_1.exercise_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            // Step 1: find the remote object from the RMI registry.
            TextAnalysisService _service =
                    (TextAnalysisService) Naming.lookup(
                            "rmi://localhost:1099/analysis");

            // Step 2: read the sentence to analyze.
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a sentence: ");
            String sentence = input.readLine();

            // Step 3: call the remote methods.
            int countVowels = _service.countVowels(sentence);
            int countConsonants = _service.countConsonants(sentence);

            // Step 4: display the results.
            System.out.println(
                    "Count of Vowels is " + countVowels + "\n" +
                            "Count of Consonants is " + countConsonants + "\n"
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
