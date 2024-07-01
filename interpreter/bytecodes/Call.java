package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Call extends ByteCode {
    private String functionLabel;
    private int targetAddress;  // This address should be resolved before execution starts

    public Call(List<String> args) {
        super(args);
        if (args.size() > 1) {
            this.functionLabel = args.get(1); // Ensure that there is a label argument
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushReturnAddress(vm.getProgramCounter() + 1); // Correctly pushing the return address
        vm.setProgramCounter(targetAddress); // Setting the program counter to the target address
    }

    public void setTargetAddress(int address) {
        this.targetAddress = address; // Used during address resolution
    }
    public String getLabel() {
        return functionLabel;
    }

    @Override
    public String toString() {
        return "CALL " + functionLabel; // Simple string representation
    }
}
