package exams.final_24_25_1.exercise_3;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            TextAnalysisService _service =
                    (TextAnalysisService) Naming.lookup(
                            "rmi://localhost:1099/analysis");

            int countVowels = _service.countVowels("test with vowels");

            int countConsonants = _service.countConsonants("test with constants");

            System.out.println(
                    "Count of Vowels is " + countVowels + "\n" +
                            "Count of Constants is " + countConsonants + "\n"
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
