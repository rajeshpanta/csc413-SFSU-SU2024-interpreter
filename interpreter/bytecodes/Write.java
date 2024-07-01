package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;

import java.util.List;

/**
 * The Write ByteCode is used to display information to the console. 
 * The only thing Write is allowed to display is the top value of the runtime stack.
 */
public class Write extends ByteCode {

    public Write(List<String> byteCodeLine) {
        super(byteCodeLine);  // Ensure compatibility with the ByteCode superclass constructor
    }

    @Override
    public void execute(VirtualMachine vm) {
        int topValue = vm.peekRunStack();  // Assume a method exists to peek the top of the stack
        System.out.println(topValue);      // Print the top of the runtime stack
        if (vm.isVerboseMode()) {          // Check VERBOSE mode for conditional display
            System.out.println(this);      // Print bytecode information if VERBOSE mode is on
        }
    }

    @Override
    public String toString() {
        return "WRITE";
    }
}
