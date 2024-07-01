package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;

import java.util.List;
import java.util.Scanner;

public class Read extends ByteCode {
    // Using a static scanner to avoid creating multiple instances if not necessary
    private static final Scanner scanner = new Scanner(System.in);

    public Read(List<String> args) {
        super(args);  // Assuming superclass requires this for some reason
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.print("Please enter an integer: "); // Prompt as per requirement
        while (!scanner.hasNextInt()) {
            scanner.nextLine(); // Clear the invalid input before re-prompting
            System.out.println("Invalid input. Please enter an integer.");
            System.out.print("Please enter an integer: ");
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline
        vm.pushRunStack(input); // Push the validated integer to the runtime stack
        
        if (vm.isVerboseMode()) {
            System.out.println(this); // Display this bytecode's action if VERBOSE mode is ON
        }
    }

    @Override
    public String toString() {
        return "READ"; // Ensures the display format is as specified
    }
}
