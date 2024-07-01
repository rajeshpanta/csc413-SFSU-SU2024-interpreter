package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * HaltCode is a ByteCode class that signals the VirtualMachine to stop executing
 * the program. It does not terminate the interpreter but merely stops the program's execution.
 */
public class HaltCode extends ByteCode {

    /**
     * Constructs a HaltCode object. Halt takes no arguments, so the constructor does not
     * need to process any additional input other than the standard bytecode line handling.
     *
     * @param byteCodeLine the parsed line from the .cod file. Halt expects no arguments.
     */
    public HaltCode(List<String> byteCodeLine) {
        super(byteCodeLine);  // Pass to the base class constructor
    }

    /**
     * Executes the Halt operation on the given VirtualMachine. This method instructs
     * the VirtualMachine to cease program execution.
     *
     * @param vm the VirtualMachine instance that is executing this bytecode.
     */
    @Override
    public void execute(VirtualMachine vm) {
        vm.stopExecution();  // A method you need to implement in the VirtualMachine class
    }

    /**
     * Provides a string representation of the bytecode for debugging and logging purposes.
     * In VERBOSE mode, this bytecode does not need to display any additional information.
     *
     * @return the name of this bytecode, "HALT".
     */
    @Override
    public String toString() {
        return "HALT";
    }
}
