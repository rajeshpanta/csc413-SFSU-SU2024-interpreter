package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Lit extends ByteCode {
    private int value;
    private String id;

    public Lit(List<String> args) {
        super(args);
        this.value = Integer.parseInt(args.get(1));  // The value is mandatory
        if (args.size() > 2) {
            this.id = args.get(2);  // The identifier is optional
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.push(this.value);  // Use VirtualMachine's method to push the value
    }

    @Override
    public String toString() {
        if (id != null) {
            return "LIT " + value + " " + id + " int " + id;
        } else {
            return "LIT " + value;
        }
    }
}
