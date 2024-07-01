package interpreter.loaders;

import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.Call;
import interpreter.bytecodes.Goto;
import interpreter.bytecodes.FalseBranch;
import interpreter.bytecodes.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Program {
    private List<ByteCode> byteCodes;
    private HashMap<String, Integer> labelAddresses;  // For mapping label identifiers to bytecode indices

    public Program() {
        this.byteCodes = new ArrayList<>();
        this.labelAddresses = new HashMap<>();
    }

    public void addCode(ByteCode byteCode) {
        byteCodes.add(byteCode);
        if (byteCode instanceof Label) {
            Label label = (Label) byteCode;
            labelAddresses.put(label.getLabel(), byteCodes.size() - 1);
        }
    }

    public ByteCode getCode(int programCounter) {
        if (programCounter >= 0 && programCounter < byteCodes.size()) {
            return byteCodes.get(programCounter);
        }
        return null;  // Return null if the index is out of bounds
    }

    public int getSize() {
        return byteCodes.size();
    }

    public void resolveAddress() {
        // Iterate over all bytecodes to resolve addresses for goto, call, and false branch instructions
        for (ByteCode bytecode : byteCodes) {
            if (bytecode instanceof Goto) {
                Goto gotoCode = (Goto) bytecode;
                Integer targetIndex = labelAddresses.get(gotoCode.getLabel());
                if (targetIndex != null) {
                    gotoCode.setTargetAddress(targetIndex);
                }
            } else if (bytecode instanceof Call) {
                Call callCode = (Call) bytecode;
                Integer targetIndex = labelAddresses.get(callCode.getLabel());
                if (targetIndex != null) {
                    callCode.setTargetAddress(targetIndex);
                }
            } else if (bytecode instanceof FalseBranch) {
                FalseBranch falseBranchCode = (FalseBranch) bytecode;
                Integer targetIndex = labelAddresses.get(falseBranchCode.getLabel());
                if (targetIndex != null) {
                    falseBranchCode.setTargetAddress(targetIndex);
                }
            }
        }
    }
}
