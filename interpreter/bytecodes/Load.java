package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Load extends ByteCode {
    private int offset;
    private String id;

    public Load(List<String> args) {
        super(args);
        this.offset = Integer.parseInt(args.get(1)); // Ensure this value is correctly parsed
        if (args.size() > 2) {
            this.id = args.get(2); // Optional identifier
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.loadFromRunStack(offset);
    }

    @Override
    public String toString() {
        if (id != null) {
            // Ensures the description is left-aligned at a fixed column width
            return String.format("LOAD %d %-17s <load %s>", offset, id, id);
        } else {
            // Simple format when there is no id involved
            return String.format("LOAD %d", offset);
        }
    }
}