import java.util.Scanner;

public class LineEditor {

    // main method
    public static void main(String[] args) {
        LineList document = new LineList(); // The linked list for the document
        Scanner scanner = new Scanner(System.in); // Scanner for user input

        // Load a file if passed as a command line argument
        if (args.length > 0) {
            String fileName = args[0];
            document.load(fileName, false); // Load file and create a new list
            System.out.println("Loaded file: " + fileName);
        }
        // show menu at the boot of the program
        LineEditor.menu();

        // Main loop for menu
        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim(); // Read and trim the input
            // Handle each command

            // show the menu
            if (command.equals("m")) {
                LineEditor.menu();

                // load file
            } else if (command.startsWith("load ")) {
                String[] parts = command.split(" ");
                // only allows you to load a file if there are three arguments
                if (parts.length == 3) {
                    String fileName = parts[1];
                    boolean append = parts[2].equals("1"); // 1 for append, 0 for new list
                    document.load(fileName, append);
                    System.out.println("Loaded file: " + fileName + (append ? " (append mode)" : " (new list mode)"));
                } else {
                    System.out.println("Invalid command format. Use: load fileName appendOption(1-append, 0-new list)");
                }

                // print all the lines
            } else if (command.equals("p")) {
                document.print();

                // print the number of lines
            } else if (command.equals("lines")) {
                System.out.println("Number of lines: " + document.lines()); // Display number of lines

                // needs two arguments
                // prints out the line number specified
            } else if (command.startsWith("line ")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    try {
                        int lineNumber = Integer.parseInt(parts[1]);
                        document.line(lineNumber); // Display specific line
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid line number format.");
                    }
                } else {
                    System.out.println("Invalid command format. Use: line lineNumber");
                }

                // print the amount of words
            } else if (command.equals("words")) {
                System.out.println("Word count: " + document.words()); // Count words

                // delete a specified line
            } else if (command.startsWith("del ")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    try {
                        int lineNumberToDelete = Integer.parseInt(parts[1]);
                        document.delete(lineNumberToDelete); // Delete specific line
                        System.out.println("Deleted line " + lineNumberToDelete);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid line number format.");
                    }
                } else {
                    System.out.println("Invalid command format. Use: del lineNumberToDelete");
                }

                // append a line typed up to the end of the singly linked list
            } else if (command.equals("a")) {
                System.out.print("Enter line to append: ");
                String lineToAppend = scanner.nextLine();
                document.addLine(lineToAppend); // Append line
                System.out.println("Line appended.");

                // append insert a line typed up in the specified line number and moves the
                // current line number and all next line numbers forward one node
            } else if (command.startsWith("i ")) {
                String[] parts = command.split(" ", 2);
                if (parts.length == 2) {
                    try {
                        int lineNumberToInsert = Integer.parseInt(parts[1]);
                        System.out.print("Enter line to insert: ");
                        String lineToInsert = scanner.nextLine();
                        document.addLine(lineToInsert, lineNumberToInsert); // Insert line
                        System.out.println("Line inserted at " + lineNumberToInsert);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid line number format.");
                    }
                } else {
                    System.out.println("Invalid command format. Use: i lineNumberToInsert");
                }

                // clear the list
            } else if (command.equals("cls")) {
                document.empty(); // Clear document
                System.out.println("Document cleared.");

                // replace <given string> for <new string>
            } else if (command.startsWith("rep ")) {
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    String originalWord = parts[1];
                    String newWord = parts[2];
                    document.replace(originalWord, newWord); // Replace words
                    System.out
                            .println("Replaced all occurrences of \"" + originalWord + "\" with \"" + newWord + "\".");
                } else {
                    System.out.println("Invalid command format. Use: rep originalWord newWord");
                }

                // save the file with a given file name
            } else if (command.startsWith("s ")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    String fileName = parts[1];
                    document.save(fileName); // Save to file
                    System.out.println("Document saved to " + fileName);
                } else {
                    System.out.println("Invalid command format. Use: s fileName");
                }

                // quit the program
            } else if (command.equals("quit")) {
                System.out.println("Exiting program...");
                break; // Exit the program
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close(); // Close the scanner when done
        System.out.println("Program ended."); // last statement. to ensure program has ended.
    }

    // menu method
    public static void menu() {
        // to make the menu not as confusing
        // ANSI escape code for green text
        String GREEN = "\033[32m";
        // ANSI escape code to reset color to default
        String RESET = "\033[0m";
        // display all commands
        System.out.println("\nMenu: " + GREEN + "m" + RESET);
        System.out.println("Load File: " + GREEN + "load fileName appendOption (1-append, 0-new list)" + RESET);
        System.out.println("Print: " + GREEN + "p" + RESET);
        System.out.println("Display number of lines: " + GREEN + "lines" + RESET);
        System.out.println("Display line: " + GREEN + "line lineNumber" + RESET);
        System.out.println("Count words: " + GREEN + "words" + RESET);
        System.out.println("Delete line: " + GREEN + "del lineNumberToDelete" + RESET);
        System.out.println("Append line: " + GREEN + "a" + RESET);
        System.out.println("Insert line: " + GREEN + "i lineNumberToInsert" + RESET);
        System.out.println("Clear document: " + GREEN + "cls" + RESET);
        System.out.println("Replace words: " + GREEN + "rep originalWord newWord" + RESET);
        System.out.println("Save to a file: " + GREEN + "s fileName" + RESET);
        System.out.println("Quit: " + GREEN + "quit" + RESET);
    }
}
