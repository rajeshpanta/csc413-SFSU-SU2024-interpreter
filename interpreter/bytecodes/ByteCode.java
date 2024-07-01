package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * Abstract class ByteCode serves as the base for all bytecode classes in the language X.
 * Each bytecode class will extend this class and implement the execute method to define
 * its specific behavior.
 */
public abstract class ByteCode {
    // The name of the bytecode, typically the first token in the line from the .cod file
    private String code;

    /**
     * Constructs a ByteCode object using the first token of a line from a .cod file
     * which typically represents the bytecode name.
     *
     * @param byteCodeLine the list of strings parsed from a line in the .cod file,
     *                     where the first element is the bytecode name.
     */
    public ByteCode(List<String> byteCodeLine) {
        if (byteCodeLine != null && !byteCodeLine.isEmpty()) {
            this.code = byteCodeLine.get(0);
        }
    }

    /**
     * Retrieves the bytecode name.
     *
     * @return the name of the bytecode.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns a string representation of the bytecode,
     * which is primarily used for debugging and logging purposes.
     *
     * @return the name of the bytecode as a string.
     */
    @Override
    public String toString() {
        return this.code;
    }

    /**
     * Abstract method that each specific bytecode class will implement.
     * This method defines how the bytecode interacts with the VirtualMachine.
     *
     * @param vm the VirtualMachine instance that is executing this bytecode.
     */
    public abstract void execute(VirtualMachine vm);
}
