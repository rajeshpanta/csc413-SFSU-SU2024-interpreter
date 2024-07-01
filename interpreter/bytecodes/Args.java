package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Args extends ByteCode {
    private int numArgs;

    public Args(List<String> args) {
        super(args);
        if (args.size() > 1) {
            this.numArgs = Integer.parseInt(args.get(1));  // Ensure this value is correctly parsed
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.newFrameAt(numArgs);  // Set up a new frame with the number of arguments specified
        // // Debug output for verbose mode
        // if (vm.isVerboseMode()) {
        //     System.out.println(toString());
        // }
    }

    @Override
    public String toString() {
        return "ARGS " + numArgs;  // Display syntax for ARGS in verbose mode
    }
}
