package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * Goto ByteCode for unconditional jumps within the program.
 */
public class Goto extends ByteCode {
    private String label;
    private int targetAddress;  // This will be resolved before the program begins executing.

    public Goto(List<String> args) {
        super(args);
        if (!args.isEmpty()) {
            this.label = args.get(1);  // Assuming the label is the second token.
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setProgramCounter(targetAddress);  // Unconditionally jump to the resolved label address.
    }

    public void setTargetAddress(int address) {
        this.targetAddress = address;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "GOTO " + label;
    }
}
