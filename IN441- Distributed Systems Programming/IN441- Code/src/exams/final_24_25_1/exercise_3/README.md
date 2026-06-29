# Exercise 3:

Write a Java program that uses RMI (Remote Method Invocation) to count vowels and consonants in a given string.

## Server Side:

1. Define a remote interface named TextAnalysisService with methods:
    - int `countVowels(String input)`
    - int `countConsonants(String input)`
2. Implement this interface in a class called TextAnalysisServicelmpl, where:
    - `countVowels()` returns the number of vowels (a, e, i, o, u)
    - `countConsonants()` returns the number of consonants
3. Host the remote object using RMI registry.

## Client Side:

1. Develop a client that connects to the remote TextAnalysisService.
2. Prompt the user to input a sentence.
3. Call the remote methods to get the number of vowels and consonants.
4. Display the results to the user.