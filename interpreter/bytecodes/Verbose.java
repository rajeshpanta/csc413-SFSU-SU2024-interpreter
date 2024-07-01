package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Verbose extends ByteCode {
    private String mode; // Holds the "ON" or "OFF" state.

    public Verbose(List<String> byteCodeLine) {
        super(byteCodeLine);
        if (byteCodeLine.size() > 1) {
            this.mode = byteCodeLine.get(1).toUpperCase();
            if (!"ON".equals(mode) && !"OFF".equals(mode)) {
                throw new IllegalArgumentException("Invalid mode for VERBOSE bytecode. Only 'ON' or 'OFF' expected.");
            }
        } else {
            throw new IllegalArgumentException("VERBOSE bytecode requires one argument ('ON' or 'OFF').");
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setVerboseMode("ON".equals(mode));
    }

    @Override
    public String toString() {
        return "VERBOSE " + mode;
    }
}
