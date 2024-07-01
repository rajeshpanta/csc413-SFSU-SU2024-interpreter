package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * PopCode is a ByteCode that removes a specified number of values from the runtime stack.
 * It ensures that the removal does not cross frame boundaries.
 */
public class PopCode extends ByteCode {
    // Number of values to pop from the runtime stack
    private int numToPop;

    /**
     * Constructs a PopCode object. This constructor initializes the bytecode with the number
     * of values to pop from the stack, which is provided as an argument in the bytecode line.
     *
     * @param byteCodeLine the parsed line from the .cod file, expecting the second element
     *                     to be the number of values to pop.
     */
    public PopCode(List<String> byteCodeLine) {
        super(byteCodeLine);
        if (byteCodeLine.size() > 1) {
            try {
                this.numToPop = Integer.parseInt(byteCodeLine.get(1));
            } catch (NumberFormatException e) {
                // Handle potential parsing error, perhaps set to a default or throw an exception
                throw new IllegalArgumentException("Invalid number format for POP bytecode");
            }
        } else {
            throw new IllegalArgumentException("POP bytecode requires one argument");
        }
    }

    /**
     * Executes the Pop operation on the given VirtualMachine. This method removes the specified
     * number of values from the runtime stack without crossing frame boundaries.
     *
     * @param vm the VirtualMachine instance that is executing this bytecode.
     */
    @Override
    public void execute(VirtualMachine vm) {
        vm.popRunStack(numToPop);
    }

    /**
     * Provides a string representation of the Pop bytecode, suitable for VERBOSE mode display.
     * It returns the operation name "POP" followed by the number of elements it is set to pop.
     *
     * @return the verbose display string for this bytecode, e.g., "POP 3".
     */
    @Override
    public String toString() {
        return "POP " + numToPop;
    }
}
