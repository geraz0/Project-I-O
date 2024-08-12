import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();
        int choice = 10;
        boolean continueRunning = true;

        while (continueRunning) {
            System.out.println("\nChoose an option:");
            System.out.println("1: List directory contents");
            System.out.println("2: Copy file");
            System.out.println("3: Move file");
            System.out.println("4: Delete file");
            System.out.println("5: Create directory");
            System.out.println("6: Delete directory");
            System.out.println("7: Search for files");
            System.out.println("8: Create file");
            System.out.println("9: Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 9) {
                    continueRunning = false;
                    System.out.println("Exiting the program...");
                } else {
                    fileManager.handleUserChoice(choice, scanner);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
}

