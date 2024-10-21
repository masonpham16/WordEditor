
//imports here
import java.io.*;
//imports here

/*
 * 
 * Student name: Hai (Mason) Pham
 * Completion date: October 20, 2024
 *
 * LineList.txt: the template file of LineList.java
 * Student tasks: implement tasks as specified in this file
 *
 * LineList class is a linked-base list that represents the contents of a
 * document
 *
 */

public class LineList {

	private Node head;

	public LineList() { // constructor
		head = null;
	}
	// Don't modify anything before this line.
	// Do not add any other instance variables.

	// *** Student tasks: implement the following methods ***

	public void empty() {
		// delete all lines in the editor
		// same as LineList module: make head null to delete all lines
		head = null;
	}

	// there's a print module required for some tests
	public void print() {
		Node current = head; // start with head node
		int linecount = 1;
		while (current != null) { // if list is not empty
			System.out.println(linecount + ". " + current.getLine()); // print line
			current = current.getNext(); // go to next node
			linecount++;
		}
	}

	public void load(String fileName, boolean append) {
		// append is true, read lines and add to existing list,
		// otherwise, create new list.
		// Each line is stored in a Node.
		// You may need to handle exception within this method
		// or throw runtime exception only.
		if (!append) {
			// If not appending, clear the current list
			head = null; // Reset the head to start a new list
		}
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Append each line to the linked list
				addLine(line); // Assuming you have an append method
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

	public void save(String fileName) {
		// Save all lines represented with Nodes to a file.
		// Each line (Node) occupies a line in the saved file.
		// You may need to handle exception within this method
		// or throw runtime exception only.
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			Node current = head; // Assuming there's a head pointer to the first node
			while (current != null) {
				bw.write(current.getLine());
				bw.newLine(); // Write each line in a new line
				current = current.getNext(); // Move to the next node
			}
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

	public void addLine(String line) {
		// append the line to the end of the list
		// Create a new node with the provided line
		Node newNode = new Node(line); // Create a new node
		// If the list is empty, set the new node as the head
		if (head == null) {
			head = newNode;
			return;
		}
		// Traverse to the last node
		Node current = head;
		while (current.getNext() != null) {
			current = current.getNext();
		}
		// Link the last node to the new node
		current.setNext(newNode);
	}

	public void addLine(String line, int n) {
		// insert new line to nth line, if n > total number of line,
		// append to the end of the list.
		Node newNode = new Node(line); // Create a new node
		// If the list is empty or n is 0, insert the node as the head
		if (head == null || n == 1) {
			newNode.setNext(head); // Set new node's next to current head
			head = newNode;
			return;
		}
		// Traverse to the n-th node (or as close as possible)
		Node current = head;
		for (int i = 1; i < n - 1 && current.getNext() != null; i++) {
			current = current.getNext();
		}
		// Insert the new node after the n-th node
		newNode.setNext(current.getNext());
		current.setNext(newNode);
	}

	public int words() {
		// count number of words, word may be separated with
		// \t,;.?!-@spaces ' * and "
		// instead of typing in all the symbols, I used non-word characters as
		// delimiters
		int totalWords = 0;
		Node current = head;
		// Traverse the list
		while (current != null) {
			String line = current.getLine();
			// Use regex to split the line into words, ignoring delimiters and symbols
			String[] words = line.split("\\W+"); // \W+ matches non-word characters as delimiters
			// Count the number of words (non-empty strings)
			for (String word : words) {
				if (!word.isEmpty()) {
					totalWords++;
				}
			}
			// Move to the next node
			current = current.getNext();
		}
		return totalWords; // Return the total word count
	}

	public int lines() {
		// count number of lines, which is the same as the number of
		// nodes in the list.
		int nodeCount = 0;
		Node current = head;

		// Traverse the list and count each node
		while (current != null) {
			nodeCount++; // Increment the node counter
			current = current.getNext(); // Move to the next node
		}

		return nodeCount; // Return the total number of nodes
	}

	public void delete(int n) {
		// If the list is empty, do nothing
		if (head == null) {
			return;
		}
		// Deleting the head node (n 0)
		if (n == 1) {
			head = head.getNext(); // Move head to the next node
			return;
		}
		// Traverse the list to find the node before the one to be deleted
		Node current = head;
		for (int i = 1; current != null && i < n - 1; i++) {
			current = current.getNext(); // Move to the next node
		}
		// If n is beyond the end of the list, do nothing
		if (current == null || current.getNext() == null) {
			return;
		}
		// Node current.getNext() is the one to be deleted
		Node nodeToDelete = current.getNext();
		// Change the next of the current node to skip the deleted node
		current.setNext(nodeToDelete.getNext());
	}

	public String toString() {
		// Return all lines represented by Nodes in the list. All lines
		// are in the same order as their corresponding nodes in the list.
		// All lines are separated with \n. No newline character should be
		// added after the last line.
		StringBuilder result = new StringBuilder();
		Node current = head;
		while (current != null) {
			result.append(current.getLine()); // Add the node's line to the result
			if (current.getNext() != null) {
				result.append("\n"); // Add a newline between nodes
			}
			current = current.getNext(); // Move to the next node
		}
		return result.toString(); // Convert StringBuilder to a string
	}

	public void replace(String s1, String s2) {
		// Replace all occurrences of s1 with s2.
		Node current = head;
		// Traverse the list and call replace method on each node
		while (current != null) {
			current.replace(s1, s2); // Replace s1 with s2 in the current node's line
			current = current.getNext(); // Move to the next node
		}
	}

	public void line(int n) {
		// Print nth line in the document. (The nth node in the list)
		// If nth line does not exist, print Line n does not exist.
		if (n < 1) {
			System.out.println("Invalid line number. Line numbers start from 1.");
			return; // Exit if the line number is invalid
		}

		Node current = head; // Start from the head of the list
		int count = 1; // Initialize a counter to track the position

		while (current != null) {
			if (count == n) {
				System.out.println(current.getLine()); // Print the line of the nth node
				return; // Exit after printing
			}
			current = current.getNext(); // Move to the next node
			count++; // Increment the counter
		}

		// If the loop completes without finding the nth node
		System.out.println("Line " + n + " does not exist.");
	}
}
