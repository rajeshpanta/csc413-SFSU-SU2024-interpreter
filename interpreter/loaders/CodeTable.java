package interpreter.loaders;

import java.util.HashMap;
import java.util.Map;


public final class CodeTable {
    private static final Map<String, String> codeMap = new HashMap<>();

    private CodeTable() {
        
    }

    /**
     * Initializes the mapping between bytecode tokens and their corresponding class names.
     */
    public static void init() {
        codeMap.clear();
        codeMap.put("HALT", "interpreter.bytecodes.HaltCode");
        codeMap.put("POP", "interpreter.bytecodes.PopCode");
        codeMap.put("FALSEBRANCH", "interpreter.bytecodes.FalseBranch");
        codeMap.put("GOTO", "interpreter.bytecodes.Goto");
        codeMap.put("STORE", "interpreter.bytecodes.Store");
        codeMap.put("LOAD", "interpreter.bytecodes.Load");
        codeMap.put("LIT", "interpreter.bytecodes.Lit");
        codeMap.put("ARGS", "interpreter.bytecodes.Args");
        codeMap.put("CALL", "interpreter.bytecodes.Call");
        codeMap.put("RETURN", "interpreter.bytecodes.Return");
        codeMap.put("BOP", "interpreter.bytecodes.Bop");
        codeMap.put("READ", "interpreter.bytecodes.Read");
        codeMap.put("WRITE", "interpreter.bytecodes.Write");
        codeMap.put("LABEL", "interpreter.bytecodes.Label");
        codeMap.put("VERBOSE", "interpreter.bytecodes.Verbose");
    }

    /**
     * Retrieves the class name of the bytecode associated with the given token.
     * 
     * @param token The bytecode token to map, e.g., "HALT".
     * @return The fully qualified class name of the bytecode, or null if the token is not recognized.
     */
    public static String getClassName(String token) {
        return codeMap.get(token);
    }
}
