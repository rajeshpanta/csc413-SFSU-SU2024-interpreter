package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;

import java.util.List;

/**
 * The Label ByteCode marks positions in the program for jumps.
 * It does not affect the runtime behavior directly but is used in address resolution.
 */
public class Label extends ByteCode {
    private String label;

    /**
     * Constructs a Label bytecode with its label.
     *
     * @param byteCodeLine The list of strings parsed from a line in the .cod file,
     *                     where the first element is the bytecode name, and the second
     *                     is the label.
     */
    public Label(List<String> byteCodeLine) {
        super(byteCodeLine);  // Pass the line to the superclass constructor
        if (byteCodeLine.size() > 1) {
            this.label = byteCodeLine.get(1);  // The label is the second element in the list
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        // No operational execution needed; labels are for marking positions only.
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "LABEL " + label;
    }
}
