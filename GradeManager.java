import java.util.ArrayList;
import java.util.Scanner;

public class GradeManager {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Double> grades = new ArrayList<>();

        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("~ Student Grade Manager ~");
            
            while (true) {
                System.out.print("Enter student name (or type 'done' to finish): ");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("done")) break;

                while(true) {
                    System.out.print("Enter grade for " + name + " (or type '-1' to finish): ");

                    try {
                        double grade = Double.parseDouble(scanner.nextLine());
                        if (grade == -1) break;
                        names.add(name);
                        grades.add(grade);
                    } 

                    catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numerical grade.");
                    }
                }
                
            }
        } 

        if (names.isEmpty()) {
            System.out.println("No data entered.");
        } 
        else {
            displaySummary(names, grades);
        }
    }

    public static void displaySummary(ArrayList<String> names, ArrayList<Double> grades) {
        double sum = 0;
        double highest = grades.get(0);
        double lowest = grades.get(0);

        System.out.println("\n~ Summary Report ~");
        for (int i = 0; i < names.size(); i++) {
            double currentGrade = grades.get(i);
            sum += currentGrade;

            if (currentGrade > highest) highest = currentGrade;
            if (currentGrade < lowest) lowest = currentGrade;
            
            System.out.printf("%s: %.2f%n", names.get(i), currentGrade);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        System.out.printf("Average Score: %.2f%n", (sum / grades.size()));
        System.out.printf("Highest Score: %.2f%n", highest);
        System.out.printf("Lowest Score:  %.2f%n", lowest);
    }
}
