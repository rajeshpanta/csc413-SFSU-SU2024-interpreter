package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class FalseBranch extends ByteCode {
    private String label;
    private int targetAddress;

    public FalseBranch(List<String> args) {
        super(args);
        this.label = args.size() > 1 ? args.get(1) : null;
    }

    @Override
    public void execute(VirtualMachine vm) {
        int value = vm.pop();  // Using the correct method name
        if (value == 0) {
            vm.setProgramCounter(targetAddress);
        }
    }

    public void setTargetAddress(int address) {
        this.targetAddress = address;
    }
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "FALSEBRANCH " + label;
    }
}
