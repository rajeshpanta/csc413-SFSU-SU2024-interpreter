package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Store extends ByteCode {
    private int offset;
    private String id;
    private int valuePopped;

    public Store(List<String> args) {
        super(args);
        this.offset = Integer.parseInt(args.get(1));  // Offset is mandatory
        if (args.size() > 2) {
            this.id = args.get(2);  // Identifier is optional
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.store(this.offset);  // Correctly using the VirtualMachine's method
    }

    @Override
    public String toString() {
        if (id != null) {
            return "STORE " + offset + " " + id + " " + id + "=" + valuePopped;
        } else {
            return "STORE " + offset;
        }
    }
}
