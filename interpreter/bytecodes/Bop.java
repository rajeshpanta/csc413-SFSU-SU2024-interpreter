package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Bop extends ByteCode {
    private String operator;

    public Bop(List<String> args) {
        super(args);
        this.operator = args.get(1);
    }

    @Override
    public void execute(VirtualMachine vm) {
        int right = vm.pop();
        int left = vm.pop();
        int result = 0;
        switch (operator) {
            case "+": result = left + right; break;
            case "-": result = left - right; break;
            case "/": result = left / right; break;
            case "*": result = left * right; break;
            case "==": result = (left == right) ? 1 : 0; break;
            case "!=": result = (left != right) ? 1 : 0; break;
            case "<=": result = (left <= right) ? 1 : 0; break;
            case "<": result = (left < right) ? 1 : 0; break;
            case ">=": result = (left >= right) ? 1 : 0; break;
            case ">": result = (left > right) ? 1 : 0; break;
            case "&": result = (left & right); break;
            case "|": result = (left | right); break;
        }
        vm.push(result);
    }

    @Override
    public String toString() {
        return "BOP " + operator;
    }
}
