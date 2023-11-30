import java.io.BufferedReader;//simplifies reading  text from a file by buffering characters.
import java.io.FileReader;//Supports the reading from a file
import java.io.IOException;//this class allows the handling of input/output errors.
import java.util.HashMap;//stores elements in key/value pairs
import java.util.Map;//this imports the map interface
import java.util.Scanner;

public class EmailAndGradesSystem {

    // a) Method to validate an email address
    /* A method that takes a string as input and tells you
    whether that string is a valid email address.A valid email address
    should contain the character @, and not have any spaces with at
     least one period(.) with minimum of 2 characters after the period(.) */
    public static boolean isValidEmailAddress(String email) {
        // Using a simple check for the presence of '@' and at least one '.' after it
        //The contains() method checks whether a string contains a sequence of characters and indexof() checks for the first occurence.
        //we have to ensure that the dot comes before the @ to be able to match the requirement.
        return email.contains("@") && email.indexOf('@') < email.lastIndexOf('.')
                && email.substring(email.lastIndexOf('.')).length() > 2
                && !email.contains(" "); //the return value will be a boolean implying either the true or false based on what is submitted by the user.
    }

    // b) Method to analyze grades from the file
    public static void analyzeGradesFromFile(String fileName) {
        //BufferedReader is a class which simplifies reading text from a character input stream.
        // It buffers the characters in order to enable efficient reading of text
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int totalGrades = 0; //initializing the counter for totalgrades
            int sum = 0;//initializing the sum  before using the while loop to run through the entries.
            //since Map is an interface, we always need a class that extends this map in order to create an object.
            //hence the use of the HashMap
            Map<Integer, Integer> gradeFrequency = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                int grade = Integer.parseInt(line.trim());
                totalGrades++;
                sum += grade;

                // Update frequency of each grade
                gradeFrequency.put(grade, gradeFrequency.getOrDefault(grade, 0) + 1);
            }

            // Calculate average
            double average = (double) sum / totalGrades;

            // Find the modal grade(s)
            // Keyset() method creates a set out of the key elements contained in the hash map
            int maxFrequency = 0;
            for (int grade : gradeFrequency.keySet()) {
                int frequency = gradeFrequency.get(grade);
                if (frequency > maxFrequency) {
                    maxFrequency = frequency;
                }
            }

            System.out.println("Average Grade: " + average);
            System.out.println("Number of Grades above Average: " + countGradesAboveAverage(average, gradeFrequency));
            System.out.println("Modal Grade(s): " + findModalGrades(maxFrequency, gradeFrequency));

        } catch (IOException | NumberFormatException e) {
            //e.printStackTrace();
            System.err.println("Error reading grades from the file.");
        }
    }

    // Helper method to count the number of grades above the average
    private static int countGradesAboveAverage(double average, Map<Integer, Integer> gradeFrequency) {
        int count = 0;
        for (int grade : gradeFrequency.keySet()) {
            if (grade > average) {
                count += gradeFrequency.get(grade);
            }
        }
        return count;
    }

    // Helper method to find the modal grade(s)
    private static String findModalGrades(int maxFrequency, Map<Integer, Integer> gradeFrequency) {
        StringBuilder modalGrades = new StringBuilder();
        for (int grade : gradeFrequency.keySet()) {
            if (gradeFrequency.get(grade) == maxFrequency) {
                modalGrades.append(grade).append(" ");
            }
        }
        return modalGrades.toString().trim();
    }

    // c) Main method marks the start of the program and calls all methods to be loaded at runtime.
    public static void main(String[] args) {
        // Test the email validation method

        Scanner EmailObject = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter valid email address");
        String testEmail = EmailObject.nextLine();
        //calling the method for email validation on user input
        System.out.println("Is '" + testEmail + "' a valid email address? " + isValidEmailAddress(testEmail));

        // Test the grade analysis method
        String fileName = "final.txt";//file containing grades
        System.out.println("\nGrade Analysis:");
        analyzeGradesFromFile(fileName);
    }
}

