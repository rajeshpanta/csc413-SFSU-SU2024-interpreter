package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Return extends ByteCode {
    private String functionIdentifier;

    public Return(List<String> args) {
        super(args);
        if (args.size() > 1) {
            this.functionIdentifier = args.get(1); // Ensuring that there is at least one argument
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        // Pop the current frame
        vm.popFrame(); 
        // Set the program counter back to the address from the returnAddress stack
        vm.setProgramCounter(vm.popReturnAddress()); 
    }

    @Override
    public String toString() {
        // This method should be handled correctly elsewhere where VM context is accessible.
        // We cannot use vm here because it's not in scope. 
        // Therefore, return a placeholder or ensure that the toString is handled at the right place.
        return functionIdentifier != null ? "RETURN " + functionIdentifier : "RETURN";
    }
}
