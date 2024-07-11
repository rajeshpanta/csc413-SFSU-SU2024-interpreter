

# Grading Report for P1



## Student Name: 

Rajesh.Panta


## Commit Count: 

2



## Git Diff Since Base Commit: 


<details>
    <summary>Git Diff</summary>

~~~bash
diff --git a/.DS_Store b/.DS_Store
new file mode 100644
index 0000000..afa371a
Binary files /dev/null and b/.DS_Store differ
diff --git a/README.md b/README.md
index 2b9a292..24521b1 100644
--- a/README.md
+++ b/README.md
@@ -1,7 +1,7 @@
 # CSC 413 - Project Two - The Interpreter
 
-## Student Name  : Name here
+## Student Name  : Rajesh Panta
 
-## Student ID    : ID here
+## Student ID    : 920636337
 
-## Student Email : Email here
+## Student Email : rpanta@sfsu.edu
diff --git a/documentation/Panta.Rajesh.pdf b/documentation/Panta.Rajesh.pdf
new file mode 100644
index 0000000..4ce94b1
Binary files /dev/null and b/documentation/Panta.Rajesh.pdf differ
diff --git a/interpreter/.DS_Store b/interpreter/.DS_Store
new file mode 100644
index 0000000..8f7711a
Binary files /dev/null and b/interpreter/.DS_Store differ
diff --git a/interpreter/bytecodes/Args.java b/interpreter/bytecodes/Args.java
new file mode 100644
index 0000000..c3322a2
--- /dev/null
+++ b/interpreter/bytecodes/Args.java
@@ -0,0 +1,29 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Args extends ByteCode {
+    private int numArgs;
+
+    public Args(List<String> args) {
+        super(args);
+        if (args.size() > 1) {
+            this.numArgs = Integer.parseInt(args.get(1));  // Ensure this value is correctly parsed
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.newFrameAt(numArgs);  // Set up a new frame with the number of arguments specified
+        // // Debug output for verbose mode
+        // if (vm.isVerboseMode()) {
+        //     System.out.println(toString());
+        // }
+    }
+
+    @Override
+    public String toString() {
+        return "ARGS " + numArgs;  // Display syntax for ARGS in verbose mode
+    }
+}
diff --git a/interpreter/bytecodes/Bop.java b/interpreter/bytecodes/Bop.java
new file mode 100644
index 0000000..c61907e
--- /dev/null
+++ b/interpreter/bytecodes/Bop.java
@@ -0,0 +1,40 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Bop extends ByteCode {
+    private String operator;
+
+    public Bop(List<String> args) {
+        super(args);
+        this.operator = args.get(1);
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        int right = vm.pop();
+        int left = vm.pop();
+        int result = 0;
+        switch (operator) {
+            case "+": result = left + right; break;
+            case "-": result = left - right; break;
+            case "/": result = left / right; break;
+            case "*": result = left * right; break;
+            case "==": result = (left == right) ? 1 : 0; break;
+            case "!=": result = (left != right) ? 1 : 0; break;
+            case "<=": result = (left <= right) ? 1 : 0; break;
+            case "<": result = (left < right) ? 1 : 0; break;
+            case ">=": result = (left >= right) ? 1 : 0; break;
+            case ">": result = (left > right) ? 1 : 0; break;
+            case "&": result = (left & right); break;
+            case "|": result = (left | right); break;
+        }
+        vm.push(result);
+    }
+
+    @Override
+    public String toString() {
+        return "BOP " + operator;
+    }
+}
diff --git a/interpreter/bytecodes/ByteCode.java b/interpreter/bytecodes/ByteCode.java
new file mode 100644
index 0000000..c6deb1d
--- /dev/null
+++ b/interpreter/bytecodes/ByteCode.java
@@ -0,0 +1,55 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+/**
+ * Abstract class ByteCode serves as the base for all bytecode classes in the language X.
+ * Each bytecode class will extend this class and implement the execute method to define
+ * its specific behavior.
+ */
+public abstract class ByteCode {
+    // The name of the bytecode, typically the first token in the line from the .cod file
+    private String code;
+
+    /**
+     * Constructs a ByteCode object using the first token of a line from a .cod file
+     * which typically represents the bytecode name.
+     *
+     * @param byteCodeLine the list of strings parsed from a line in the .cod file,
+     *                     where the first element is the bytecode name.
+     */
+    public ByteCode(List<String> byteCodeLine) {
+        if (byteCodeLine != null && !byteCodeLine.isEmpty()) {
+            this.code = byteCodeLine.get(0);
+        }
+    }
+
+    /**
+     * Retrieves the bytecode name.
+     *
+     * @return the name of the bytecode.
+     */
+    public String getCode() {
+        return this.code;
+    }
+
+    /**
+     * Returns a string representation of the bytecode,
+     * which is primarily used for debugging and logging purposes.
+     *
+     * @return the name of the bytecode as a string.
+     */
+    @Override
+    public String toString() {
+        return this.code;
+    }
+
+    /**
+     * Abstract method that each specific bytecode class will implement.
+     * This method defines how the bytecode interacts with the VirtualMachine.
+     *
+     * @param vm the VirtualMachine instance that is executing this bytecode.
+     */
+    public abstract void execute(VirtualMachine vm);
+}
diff --git a/interpreter/bytecodes/Call.java b/interpreter/bytecodes/Call.java
new file mode 100644
index 0000000..2af0b39
--- /dev/null
+++ b/interpreter/bytecodes/Call.java
@@ -0,0 +1,34 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Call extends ByteCode {
+    private String functionLabel;
+    private int targetAddress;  // This address should be resolved before execution starts
+
+    public Call(List<String> args) {
+        super(args);
+        if (args.size() > 1) {
+            this.functionLabel = args.get(1); // Ensure that there is a label argument
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.pushReturnAddress(vm.getProgramCounter() + 1); // Correctly pushing the return address
+        vm.setProgramCounter(targetAddress); // Setting the program counter to the target address
+    }
+
+    public void setTargetAddress(int address) {
+        this.targetAddress = address; // Used during address resolution
+    }
+    public String getLabel() {
+        return functionLabel;
+    }
+
+    @Override
+    public String toString() {
+        return "CALL " + functionLabel; // Simple string representation
+    }
+}
diff --git a/interpreter/bytecodes/FalseBranch.java b/interpreter/bytecodes/FalseBranch.java
new file mode 100644
index 0000000..9c1bbba
--- /dev/null
+++ b/interpreter/bytecodes/FalseBranch.java
@@ -0,0 +1,34 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class FalseBranch extends ByteCode {
+    private String label;
+    private int targetAddress;
+
+    public FalseBranch(List<String> args) {
+        super(args);
+        this.label = args.size() > 1 ? args.get(1) : null;
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        int value = vm.pop();  // Using the correct method name
+        if (value == 0) {
+            vm.setProgramCounter(targetAddress);
+        }
+    }
+
+    public void setTargetAddress(int address) {
+        this.targetAddress = address;
+    }
+    public String getLabel() {
+        return label;
+    }
+
+    @Override
+    public String toString() {
+        return "FALSEBRANCH " + label;
+    }
+}
diff --git a/interpreter/bytecodes/Goto.java b/interpreter/bytecodes/Goto.java
new file mode 100644
index 0000000..99cbb1f
--- /dev/null
+++ b/interpreter/bytecodes/Goto.java
@@ -0,0 +1,37 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+/**
+ * Goto ByteCode for unconditional jumps within the program.
+ */
+public class Goto extends ByteCode {
+    private String label;
+    private int targetAddress;  // This will be resolved before the program begins executing.
+
+    public Goto(List<String> args) {
+        super(args);
+        if (!args.isEmpty()) {
+            this.label = args.get(1);  // Assuming the label is the second token.
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.setProgramCounter(targetAddress);  // Unconditionally jump to the resolved label address.
+    }
+
+    public void setTargetAddress(int address) {
+        this.targetAddress = address;
+    }
+
+    public String getLabel() {
+        return label;
+    }
+
+    @Override
+    public String toString() {
+        return "GOTO " + label;
+    }
+}
diff --git a/interpreter/bytecodes/HaltCode.java b/interpreter/bytecodes/HaltCode.java
new file mode 100644
index 0000000..335d2db
--- /dev/null
+++ b/interpreter/bytecodes/HaltCode.java
@@ -0,0 +1,43 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+/**
+ * HaltCode is a ByteCode class that signals the VirtualMachine to stop executing
+ * the program. It does not terminate the interpreter but merely stops the program's execution.
+ */
+public class HaltCode extends ByteCode {
+
+    /**
+     * Constructs a HaltCode object. Halt takes no arguments, so the constructor does not
+     * need to process any additional input other than the standard bytecode line handling.
+     *
+     * @param byteCodeLine the parsed line from the .cod file. Halt expects no arguments.
+     */
+    public HaltCode(List<String> byteCodeLine) {
+        super(byteCodeLine);  // Pass to the base class constructor
+    }
+
+    /**
+     * Executes the Halt operation on the given VirtualMachine. This method instructs
+     * the VirtualMachine to cease program execution.
+     *
+     * @param vm the VirtualMachine instance that is executing this bytecode.
+     */
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.stopExecution();  // A method you need to implement in the VirtualMachine class
+    }
+
+    /**
+     * Provides a string representation of the bytecode for debugging and logging purposes.
+     * In VERBOSE mode, this bytecode does not need to display any additional information.
+     *
+     * @return the name of this bytecode, "HALT".
+     */
+    @Override
+    public String toString() {
+        return "HALT";
+    }
+}
diff --git a/interpreter/bytecodes/Label.java b/interpreter/bytecodes/Label.java
new file mode 100644
index 0000000..e4e352f
--- /dev/null
+++ b/interpreter/bytecodes/Label.java
@@ -0,0 +1,41 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+
+import java.util.List;
+
+/**
+ * The Label ByteCode marks positions in the program for jumps.
+ * It does not affect the runtime behavior directly but is used in address resolution.
+ */
+public class Label extends ByteCode {
+    private String label;
+
+    /**
+     * Constructs a Label bytecode with its label.
+     *
+     * @param byteCodeLine The list of strings parsed from a line in the .cod file,
+     *                     where the first element is the bytecode name, and the second
+     *                     is the label.
+     */
+    public Label(List<String> byteCodeLine) {
+        super(byteCodeLine);  // Pass the line to the superclass constructor
+        if (byteCodeLine.size() > 1) {
+            this.label = byteCodeLine.get(1);  // The label is the second element in the list
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        // No operational execution needed; labels are for marking positions only.
+    }
+
+    public String getLabel() {
+        return label;
+    }
+
+    @Override
+    public String toString() {
+        return "LABEL " + label;
+    }
+}
diff --git a/interpreter/bytecodes/Lit.java b/interpreter/bytecodes/Lit.java
new file mode 100644
index 0000000..a4e8e2f
--- /dev/null
+++ b/interpreter/bytecodes/Lit.java
@@ -0,0 +1,31 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Lit extends ByteCode {
+    private int value;
+    private String id;
+
+    public Lit(List<String> args) {
+        super(args);
+        this.value = Integer.parseInt(args.get(1));  // The value is mandatory
+        if (args.size() > 2) {
+            this.id = args.get(2);  // The identifier is optional
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.push(this.value);  // Use VirtualMachine's method to push the value
+    }
+
+    @Override
+    public String toString() {
+        if (id != null) {
+            return "LIT " + value + " " + id + " int " + id;
+        } else {
+            return "LIT " + value;
+        }
+    }
+}
diff --git a/interpreter/bytecodes/Load.java b/interpreter/bytecodes/Load.java
new file mode 100644
index 0000000..6c470eb
--- /dev/null
+++ b/interpreter/bytecodes/Load.java
@@ -0,0 +1,33 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Load extends ByteCode {
+    private int offset;
+    private String id;
+
+    public Load(List<String> args) {
+        super(args);
+        this.offset = Integer.parseInt(args.get(1)); // Ensure this value is correctly parsed
+        if (args.size() > 2) {
+            this.id = args.get(2); // Optional identifier
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.loadFromRunStack(offset);
+    }
+
+    @Override
+    public String toString() {
+        if (id != null) {
+            // Ensures the description is left-aligned at a fixed column width
+            return String.format("LOAD %d %-17s <load %s>", offset, id, id);
+        } else {
+            // Simple format when there is no id involved
+            return String.format("LOAD %d", offset);
+        }
+    }
+}
\ No newline at end of file
diff --git a/interpreter/bytecodes/PopCode.java b/interpreter/bytecodes/PopCode.java
new file mode 100644
index 0000000..e6e095c
--- /dev/null
+++ b/interpreter/bytecodes/PopCode.java
@@ -0,0 +1,56 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+/**
+ * PopCode is a ByteCode that removes a specified number of values from the runtime stack.
+ * It ensures that the removal does not cross frame boundaries.
+ */
+public class PopCode extends ByteCode {
+    // Number of values to pop from the runtime stack
+    private int numToPop;
+
+    /**
+     * Constructs a PopCode object. This constructor initializes the bytecode with the number
+     * of values to pop from the stack, which is provided as an argument in the bytecode line.
+     *
+     * @param byteCodeLine the parsed line from the .cod file, expecting the second element
+     *                     to be the number of values to pop.
+     */
+    public PopCode(List<String> byteCodeLine) {
+        super(byteCodeLine);
+        if (byteCodeLine.size() > 1) {
+            try {
+                this.numToPop = Integer.parseInt(byteCodeLine.get(1));
+            } catch (NumberFormatException e) {
+                // Handle potential parsing error, perhaps set to a default or throw an exception
+                throw new IllegalArgumentException("Invalid number format for POP bytecode");
+            }
+        } else {
+            throw new IllegalArgumentException("POP bytecode requires one argument");
+        }
+    }
+
+    /**
+     * Executes the Pop operation on the given VirtualMachine. This method removes the specified
+     * number of values from the runtime stack without crossing frame boundaries.
+     *
+     * @param vm the VirtualMachine instance that is executing this bytecode.
+     */
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.popRunStack(numToPop);
+    }
+
+    /**
+     * Provides a string representation of the Pop bytecode, suitable for VERBOSE mode display.
+     * It returns the operation name "POP" followed by the number of elements it is set to pop.
+     *
+     * @return the verbose display string for this bytecode, e.g., "POP 3".
+     */
+    @Override
+    public String toString() {
+        return "POP " + numToPop;
+    }
+}
diff --git a/interpreter/bytecodes/Read.java b/interpreter/bytecodes/Read.java
new file mode 100644
index 0000000..2d538bc
--- /dev/null
+++ b/interpreter/bytecodes/Read.java
@@ -0,0 +1,37 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+
+import java.util.List;
+import java.util.Scanner;
+
+public class Read extends ByteCode {
+    // Using a static scanner to avoid creating multiple instances if not necessary
+    private static final Scanner scanner = new Scanner(System.in);
+
+    public Read(List<String> args) {
+        super(args);  // Assuming superclass requires this for some reason
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        System.out.print("Please enter an integer: "); // Prompt as per requirement
+        while (!scanner.hasNextInt()) {
+            scanner.nextLine(); // Clear the invalid input before re-prompting
+            System.out.println("Invalid input. Please enter an integer.");
+            System.out.print("Please enter an integer: ");
+        }
+        int input = scanner.nextInt();
+        scanner.nextLine(); // Consume the remaining newline
+        vm.pushRunStack(input); // Push the validated integer to the runtime stack
+        
+        if (vm.isVerboseMode()) {
+            System.out.println(this); // Display this bytecode's action if VERBOSE mode is ON
+        }
+    }
+
+    @Override
+    public String toString() {
+        return "READ"; // Ensures the display format is as specified
+    }
+}
diff --git a/interpreter/bytecodes/Return.java b/interpreter/bytecodes/Return.java
new file mode 100644
index 0000000..574008f
--- /dev/null
+++ b/interpreter/bytecodes/Return.java
@@ -0,0 +1,31 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Return extends ByteCode {
+    private String functionIdentifier;
+
+    public Return(List<String> args) {
+        super(args);
+        if (args.size() > 1) {
+            this.functionIdentifier = args.get(1); // Ensuring that there is at least one argument
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        // Pop the current frame
+        vm.popFrame(); 
+        // Set the program counter back to the address from the returnAddress stack
+        vm.setProgramCounter(vm.popReturnAddress()); 
+    }
+
+    @Override
+    public String toString() {
+        // This method should be handled correctly elsewhere where VM context is accessible.
+        // We cannot use vm here because it's not in scope. 
+        // Therefore, return a placeholder or ensure that the toString is handled at the right place.
+        return functionIdentifier != null ? "RETURN " + functionIdentifier : "RETURN";
+    }
+}
diff --git a/interpreter/bytecodes/Store.java b/interpreter/bytecodes/Store.java
new file mode 100644
index 0000000..73ef063
--- /dev/null
+++ b/interpreter/bytecodes/Store.java
@@ -0,0 +1,32 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Store extends ByteCode {
+    private int offset;
+    private String id;
+    private int valuePopped;
+
+    public Store(List<String> args) {
+        super(args);
+        this.offset = Integer.parseInt(args.get(1));  // Offset is mandatory
+        if (args.size() > 2) {
+            this.id = args.get(2);  // Identifier is optional
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.store(this.offset);  // Correctly using the VirtualMachine's method
+    }
+
+    @Override
+    public String toString() {
+        if (id != null) {
+            return "STORE " + offset + " " + id + " " + id + "=" + valuePopped;
+        } else {
+            return "STORE " + offset;
+        }
+    }
+}
diff --git a/interpreter/bytecodes/Verbose.java b/interpreter/bytecodes/Verbose.java
new file mode 100644
index 0000000..9840b78
--- /dev/null
+++ b/interpreter/bytecodes/Verbose.java
@@ -0,0 +1,30 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+import java.util.List;
+
+public class Verbose extends ByteCode {
+    private String mode; // Holds the "ON" or "OFF" state.
+
+    public Verbose(List<String> byteCodeLine) {
+        super(byteCodeLine);
+        if (byteCodeLine.size() > 1) {
+            this.mode = byteCodeLine.get(1).toUpperCase();
+            if (!"ON".equals(mode) && !"OFF".equals(mode)) {
+                throw new IllegalArgumentException("Invalid mode for VERBOSE bytecode. Only 'ON' or 'OFF' expected.");
+            }
+        } else {
+            throw new IllegalArgumentException("VERBOSE bytecode requires one argument ('ON' or 'OFF').");
+        }
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        vm.setVerboseMode("ON".equals(mode));
+    }
+
+    @Override
+    public String toString() {
+        return "VERBOSE " + mode;
+    }
+}
diff --git a/interpreter/bytecodes/Write.java b/interpreter/bytecodes/Write.java
new file mode 100644
index 0000000..967a8d5
--- /dev/null
+++ b/interpreter/bytecodes/Write.java
@@ -0,0 +1,30 @@
+package interpreter.bytecodes;
+
+import interpreter.virtualmachine.VirtualMachine;
+
+import java.util.List;
+
+/**
+ * The Write ByteCode is used to display information to the console. 
+ * The only thing Write is allowed to display is the top value of the runtime stack.
+ */
+public class Write extends ByteCode {
+
+    public Write(List<String> byteCodeLine) {
+        super(byteCodeLine);  // Ensure compatibility with the ByteCode superclass constructor
+    }
+
+    @Override
+    public void execute(VirtualMachine vm) {
+        int topValue = vm.peekRunStack();  // Assume a method exists to peek the top of the stack
+        System.out.println(topValue);      // Print the top of the runtime stack
+        if (vm.isVerboseMode()) {          // Check VERBOSE mode for conditional display
+            System.out.println(this);      // Print bytecode information if VERBOSE mode is on
+        }
+    }
+
+    @Override
+    public String toString() {
+        return "WRITE";
+    }
+}
diff --git a/interpreter/loaders/ByteCodeLoader.java b/interpreter/loaders/ByteCodeLoader.java
index add318f..dfa2a20 100644
--- a/interpreter/loaders/ByteCodeLoader.java
+++ b/interpreter/loaders/ByteCodeLoader.java
@@ -1,26 +1,60 @@
 package interpreter.loaders;
 
-import interpreter.loaders.Program;
+import interpreter.bytecodes.ByteCode;
+import java.io.BufferedReader;
+import java.io.FileReader;
+import java.io.IOException;
+import java.util.ArrayList;
+import java.util.List;
 
 public final class ByteCodeLoader {
     private String codSourceFileName;
-    
+
     /**
-     * Constructs ByteCodeLoader object given a COD source code
-     * file name
+     * Constructs ByteCodeLoader object given a COD source code file name.
      * @param fileName name of .cod File to load.
      */
-    public ByteCodeLoader(String fileName){
+    public ByteCodeLoader(String fileName) {
         this.codSourceFileName = fileName;
     }
-    
+
     /**
      * Loads a program from a .cod file.
      * @return a constructed Program Object.
-     * @throws InvalidProgramException thrown when 
-     * loadCodes fails.
+     * @throws InvalidProgramException thrown when loadCodes fails.
      */
     public Program loadCodes() throws InvalidProgramException {
-       return null;
+        Program program = new Program();
+        try (BufferedReader reader = new BufferedReader(new FileReader(this.codSourceFileName))) {
+            String line;
+            while ((line = reader.readLine()) != null) {
+                List<String> tokens = parseTokens(line);
+                if (tokens.isEmpty()) {
+                    continue; // Skip empty lines or handle them as needed
+                }
+                String bytecodeName = tokens.get(0);
+                String className = CodeTable.getClassName(bytecodeName);
+                if (className == null) {
+                    throw new InvalidProgramException("Invalid bytecode '" + bytecodeName + "' found in .cod file.");
+                }
+                ByteCode bytecode = (ByteCode) Class.forName(className).getDeclaredConstructor(List.class).newInstance(tokens);
+                program.addCode(bytecode);
+            }
+        } catch (IOException | ReflectiveOperationException e) {
+            throw new InvalidProgramException(e, "Failed to load bytecodes from file: " + this.codSourceFileName);
+        }
+        
+
+        return program;
+    }
+
+    /**
+     * Tokenizes a line of input from the .cod file into individual elements.
+     * @param line the line to tokenize.
+     * @return a list of tokens.
+     */
+    private List<String> parseTokens(String line) {
+        String[] parts = line.trim().split("\\s+");
+        return new ArrayList<>(List.of(parts));
     }
 }
diff --git a/interpreter/loaders/CodeTable.java b/interpreter/loaders/CodeTable.java
index 4b2d000..406aedc 100644
--- a/interpreter/loaders/CodeTable.java
+++ b/interpreter/loaders/CodeTable.java
@@ -1,31 +1,45 @@
-/**
- * Code table of byte codes in language X
- * @key name of a specific byte code
- * @value name of the class that the key belongs to.
- */
 package interpreter.loaders;
 
-public final class CodeTable {
+import java.util.HashMap;
+import java.util.Map;
 
-   private CodeTable() {
-      // do nothing
-   }
 
-   /**
-    * fill code table with class name mappings
-    */
-   public static void init() {
+public final class CodeTable {
+    private static final Map<String, String> codeMap = new HashMap<>();
 
-   }
+    private CodeTable() {
+        
+    }
 
-   /**
-    * Returns the ByteCode class name for a given token.
-    * 
-    * @param token bytecode to map. For example, HALT --> HaltCode
-    * @return class name of bytecode
-    */
-   public static String getClassName(String token) {
-      return null;
-   }
+    /**
+     * Initializes the mapping between bytecode tokens and their corresponding class names.
+     */
+    public static void init() {
+        codeMap.clear();
+        codeMap.put("HALT", "interpreter.bytecodes.HaltCode");
+        codeMap.put("POP", "interpreter.bytecodes.PopCode");
+        codeMap.put("FALSEBRANCH", "interpreter.bytecodes.FalseBranch");
+        codeMap.put("GOTO", "interpreter.bytecodes.Goto");
+        codeMap.put("STORE", "interpreter.bytecodes.Store");
+        codeMap.put("LOAD", "interpreter.bytecodes.Load");
+        codeMap.put("LIT", "interpreter.bytecodes.Lit");
+        codeMap.put("ARGS", "interpreter.bytecodes.Args");
+        codeMap.put("CALL", "interpreter.bytecodes.Call");
+        codeMap.put("RETURN", "interpreter.bytecodes.Return");
+        codeMap.put("BOP", "interpreter.bytecodes.Bop");
+        codeMap.put("READ", "interpreter.bytecodes.Read");
+        codeMap.put("WRITE", "interpreter.bytecodes.Write");
+        codeMap.put("LABEL", "interpreter.bytecodes.Label");
+        codeMap.put("VERBOSE", "interpreter.bytecodes.Verbose");
+    }
 
+    /**
+     * Retrieves the class name of the bytecode associated with the given token.
+     * 
+     * @param token The bytecode token to map, e.g., "HALT".
+     * @return The fully qualified class name of the bytecode, or null if the token is not recognized.
+     */
+    public static String getClassName(String token) {
+        return codeMap.get(token);
+    }
 }
diff --git a/interpreter/loaders/Program.java b/interpreter/loaders/Program.java
index ad09e34..81822ac 100644
--- a/interpreter/loaders/Program.java
+++ b/interpreter/loaders/Program.java
@@ -1,54 +1,65 @@
 package interpreter.loaders;
 
+import interpreter.bytecodes.ByteCode;
+import interpreter.bytecodes.Call;
+import interpreter.bytecodes.Goto;
+import interpreter.bytecodes.FalseBranch;
+import interpreter.bytecodes.Label;
+
 import java.util.ArrayList;
+import java.util.HashMap;
 import java.util.List;
-public class Program {
 
-    private List<ByteCode> program;
+public class Program {
+    private List<ByteCode> byteCodes;
+    private HashMap<String, Integer> labelAddresses;  // For mapping label identifiers to bytecode indices
 
-    /**
-     * Instantiates a program object using an
-     * ArrayList
-     */
     public Program() {
-
+        this.byteCodes = new ArrayList<>();
+        this.labelAddresses = new HashMap<>();
     }
 
-    /**
-     * Gets the size of the current program.
-     * @return size of program
-     */
-    public int getSize() {
-        return 0;
+    public void addCode(ByteCode byteCode) {
+        byteCodes.add(byteCode);
+        if (byteCode instanceof Label) {
+            Label label = (Label) byteCode;
+            labelAddresses.put(label.getLabel(), byteCodes.size() - 1);
+        }
     }
 
-    /**
-     * Grabs an instance of a bytecode at an index.
-     * @param programCounter index of bytecode to get.
-     * @return a bytecode.
-     */
     public ByteCode getCode(int programCounter) {
-        return null;
+        if (programCounter >= 0 && programCounter < byteCodes.size()) {
+            return byteCodes.get(programCounter);
+        }
+        return null;  // Return null if the index is out of bounds
     }
 
-    /**
-     * Adds a bytecode instance to the Program List.
-     * @param c bytecode to be added
-     */
-    public void addCode(ByteCode c) {
-
+    public int getSize() {
+        return byteCodes.size();
     }
 
-    /**
-     * Makes multiple passes through the program ArrayList
-     * resolving addrss for Goto,Call and FalseBranch
-     * bytecodes. These bytecodes can only jump to Label
-     * codes that have a matching label value.
-     * HINT: make note of what type of data-structure
-     * ByteCodes are stored in.
-     * **** METHOD SIGNATURE CANNOT BE CHANGED *****
-     */
     public void resolveAddress() {
-
+        // Iterate over all bytecodes to resolve addresses for goto, call, and false branch instructions
+        for (ByteCode bytecode : byteCodes) {
+            if (bytecode instanceof Goto) {
+                Goto gotoCode = (Goto) bytecode;
+                Integer targetIndex = labelAddresses.get(gotoCode.getLabel());
+                if (targetIndex != null) {
+                    gotoCode.setTargetAddress(targetIndex);
+                }
+            } else if (bytecode instanceof Call) {
+                Call callCode = (Call) bytecode;
+                Integer targetIndex = labelAddresses.get(callCode.getLabel());
+                if (targetIndex != null) {
+                    callCode.setTargetAddress(targetIndex);
+                }
+            } else if (bytecode instanceof FalseBranch) {
+                FalseBranch falseBranchCode = (FalseBranch) bytecode;
+                Integer targetIndex = labelAddresses.get(falseBranchCode.getLabel());
+                if (targetIndex != null) {
+                    falseBranchCode.setTargetAddress(targetIndex);
+                }
+            }
+        }
     }
-}   
\ No newline at end of file
+}
diff --git a/interpreter/virtualmachine/RunTimeStack.java b/interpreter/virtualmachine/RunTimeStack.java
index 3289bc0..d5ce3b7 100644
--- a/interpreter/virtualmachine/RunTimeStack.java
+++ b/interpreter/virtualmachine/RunTimeStack.java
@@ -1,20 +1,73 @@
-package interpreter.virtualmachine;
-
-import java.util.ArrayList;
-import java.util.List;
-import java.util.Stack;
-
-class RunTimeStack {
-
-    private List<Integer> runTimeStack;
-    private Stack<Integer> framePointer;
-
-    public RunTimeStack() {
-        runTimeStack = new ArrayList<>();
-        framePointer = new Stack<>();
-        // Add initial frame pointer value, main is the entry
-        // point of our language, so its frame pointer is 0.
-        framePointer.add(0);
-    }
-
-}
+package interpreter.virtualmachine; 
+ 
+import java.util.ArrayList; 
+import java.util.List; 
+import java.util.Stack; 
+ 
+public class RunTimeStack { 
+ 
+    private List<Integer> runTimeStack; 
+    private Stack<Integer> framePointer; 
+ 
+    public RunTimeStack() { 
+        this.runTimeStack = new ArrayList<>(); 
+        this.framePointer = new Stack<>(); 
+        framePointer.push(0); // Start frame pointer at the base 
+    } 
+ 
+    public int peek() { 
+        return runTimeStack.get(runTimeStack.size() - 1); 
+    } 
+ 
+    public int push(int value) { 
+        runTimeStack.add(value); 
+        return value; 
+    } 
+ 
+    public int pop() { 
+        return runTimeStack.remove(runTimeStack.size() - 1); 
+    } 
+ 
+    public int store(int offset) { 
+        int location = framePointer.peek() + offset; 
+        int valueToStore = runTimeStack.get(runTimeStack.size() - 1); 
+        runTimeStack.set(location, valueToStore); 
+        return runTimeStack.remove(runTimeStack.size() - 1); 
+    } 
+ 
+    public int load(int offset) { 
+        int location = framePointer.peek() + offset; 
+        int valueToLoad = runTimeStack.get(location); 
+        runTimeStack.add(valueToLoad); 
+        return valueToLoad; 
+    } 
+ 
+    public void newFrameAt(int offset) { 
+        framePointer.push(runTimeStack.size() - offset); 
+    } 
+ 
+    public void popFrame() { 
+        int frameStart = framePointer.pop(); 
+        int returnValue = runTimeStack.get(runTimeStack.size() - 1); 
+        for (int i = runTimeStack.size() - 1; i >= frameStart; i--) { 
+            runTimeStack.remove(i); 
+        } 
+        if (framePointer.size() > 0) { 
+            runTimeStack.add(returnValue); 
+        } 
+    } 
+ 
+    public String verboseDisplay() { 
+        StringBuilder sb = new StringBuilder(); 
+        int lastFrameIndex = 0; 
+        for (Integer frameStart : framePointer) { 
+            sb.append("["); 
+            for (int i = frameStart; i < runTimeStack.size(); i++) { 
+                sb.append(runTimeStack.get(i)).append(" "); 
+            } 
+            sb.append("] "); 
+            lastFrameIndex = frameStart; 
+        } 
+        return sb.toString(); 
+    } 
+}
\ No newline at end of file
diff --git a/interpreter/virtualmachine/VirtualMachine.java b/interpreter/virtualmachine/VirtualMachine.java
index 5d7be36..3d7e877 100644
--- a/interpreter/virtualmachine/VirtualMachine.java
+++ b/interpreter/virtualmachine/VirtualMachine.java
@@ -1,20 +1,105 @@
-package interpreter.virtualmachine;
-
-import java.util.Stack;
-import interpreter.loaders.Program;
-
-public class VirtualMachine {
-
-    private RunTimeStack   runTimeStack;
-    private Stack<Integer> returnAddress;
-    private Program        program;
-    private int            programCounter;
-    private boolean        isRunning;
-
-    public VirtualMachine(Program program) {
-        this.program = program;
-        this.runTimeStack = new RunTimeStack();
-        this.returnAddress = new Stack<>();
-        this.programCounter = 0;
-    }
-}
+package interpreter.virtualmachine; 
+ 
+import interpreter.bytecodes.ByteCode; 
+import interpreter.loaders.Program; 
+import java.util.Stack; 
+ 
+public class VirtualMachine { 
+ 
+    private RunTimeStack runTimeStack; 
+    private Stack<Integer> returnAddress; 
+    private Program program; 
+    private int programCounter; 
+    private boolean isRunning; 
+    private boolean verboseMode; 
+ 
+    public VirtualMachine(Program program) { 
+        this.program = program; 
+        this.runTimeStack = new RunTimeStack(); 
+        this.returnAddress = new Stack<>(); 
+        this.programCounter = 0; 
+        this.isRunning = true; 
+        this.verboseMode = true; 
+    } 
+ 
+    public void executeProgram() { 
+        while (isRunning) { 
+            ByteCode code = program.getCode(programCounter); 
+            System.out.println(code.toString()); // Print current bytecode being executed 
+            code.execute(this); 
+            if (verboseMode) { 
+                System.out.println(runTimeStack.verboseDisplay()); // Print stack state after execution 
+            } 
+            programCounter++; 
+            if (programCounter >= program.getSize()) { 
+                isRunning = false; 
+            } 
+        } 
+    } 
+ 
+    public void stopExecution() { 
+        isRunning = false; 
+    } 
+ 
+    public void pushRunStack(int value) { 
+        runTimeStack.push(value); 
+    } 
+ 
+    public void popRunStack(int numToPop) { 
+        for (int i = 0; i < numToPop; i++) { 
+            runTimeStack.pop(); 
+        } 
+    } 
+ 
+    public int pop() { 
+        return runTimeStack.pop(); 
+    } 
+ 
+    public void push(int value) { 
+        runTimeStack.push(value); 
+    } 
+ 
+    public int loadFromRunStack(int offset) { 
+        return runTimeStack.load(offset); 
+    } 
+ 
+    public int store(int offset) { 
+        return runTimeStack.store(offset); 
+    } 
+ 
+    public void newFrameAt(int numArgs) { 
+        runTimeStack.newFrameAt(numArgs); 
+    } 
+ 
+    public void popFrame() { 
+        runTimeStack.popFrame(); 
+    } 
+ 
+    public void pushReturnAddress(int address) { 
+        returnAddress.push(address); 
+    } 
+ 
+    public int popReturnAddress() { 
+        return returnAddress.pop(); 
+    } 
+ 
+    public void setProgramCounter(int newPC) { 
+        this.programCounter = newPC; 
+    } 
+ 
+    public int getProgramCounter() { 
+        return this.programCounter; 
+    } 
+ 
+    public void setVerboseMode(boolean mode) { 
+        this.verboseMode = mode; 
+    } 
+ 
+    public boolean isVerboseMode() { 
+        return this.verboseMode; 
+    } 
+ 
+    public int peekRunStack() { 
+        return runTimeStack.peek(); 
+    } 
+}
\ No newline at end of file
diff --git a/target/interpreter/Interpreter.class b/target/interpreter/Interpreter.class
new file mode 100644
index 0000000..b484699
Binary files /dev/null and b/target/interpreter/Interpreter.class differ
diff --git a/target/interpreter/bytecodes/Args.class b/target/interpreter/bytecodes/Args.class
new file mode 100644
index 0000000..cf1783e
Binary files /dev/null and b/target/interpreter/bytecodes/Args.class differ
diff --git a/target/interpreter/bytecodes/Bop.class b/target/interpreter/bytecodes/Bop.class
new file mode 100644
index 0000000..1f81ce3
Binary files /dev/null and b/target/interpreter/bytecodes/Bop.class differ
diff --git a/target/interpreter/bytecodes/ByteCode.class b/target/interpreter/bytecodes/ByteCode.class
new file mode 100644
index 0000000..2c23e1b
Binary files /dev/null and b/target/interpreter/bytecodes/ByteCode.class differ
diff --git a/target/interpreter/bytecodes/Call.class b/target/interpreter/bytecodes/Call.class
new file mode 100644
index 0000000..6bb648d
Binary files /dev/null and b/target/interpreter/bytecodes/Call.class differ
diff --git a/target/interpreter/bytecodes/FalseBranch.class b/target/interpreter/bytecodes/FalseBranch.class
new file mode 100644
index 0000000..2e4d965
Binary files /dev/null and b/target/interpreter/bytecodes/FalseBranch.class differ
diff --git a/target/interpreter/bytecodes/Goto.class b/target/interpreter/bytecodes/Goto.class
new file mode 100644
index 0000000..cb2e271
Binary files /dev/null and b/target/interpreter/bytecodes/Goto.class differ
diff --git a/target/interpreter/bytecodes/HaltCode.class b/target/interpreter/bytecodes/HaltCode.class
new file mode 100644
index 0000000..1808cb6
Binary files /dev/null and b/target/interpreter/bytecodes/HaltCode.class differ
diff --git a/target/interpreter/bytecodes/Label.class b/target/interpreter/bytecodes/Label.class
new file mode 100644
index 0000000..88cbfd5
Binary files /dev/null and b/target/interpreter/bytecodes/Label.class differ
diff --git a/target/interpreter/bytecodes/Lit.class b/target/interpreter/bytecodes/Lit.class
new file mode 100644
index 0000000..288e2ee
Binary files /dev/null and b/target/interpreter/bytecodes/Lit.class differ
diff --git a/target/interpreter/bytecodes/Load.class b/target/interpreter/bytecodes/Load.class
new file mode 100644
index 0000000..0f65261
Binary files /dev/null and b/target/interpreter/bytecodes/Load.class differ
diff --git a/target/interpreter/bytecodes/PopCode.class b/target/interpreter/bytecodes/PopCode.class
new file mode 100644
index 0000000..0e9963f
Binary files /dev/null and b/target/interpreter/bytecodes/PopCode.class differ
diff --git a/target/interpreter/bytecodes/Read.class b/target/interpreter/bytecodes/Read.class
new file mode 100644
index 0000000..6d3e526
Binary files /dev/null and b/target/interpreter/bytecodes/Read.class differ
diff --git a/target/interpreter/bytecodes/Return.class b/target/interpreter/bytecodes/Return.class
new file mode 100644
index 0000000..042c149
Binary files /dev/null and b/target/interpreter/bytecodes/Return.class differ
diff --git a/target/interpreter/bytecodes/Store.class b/target/interpreter/bytecodes/Store.class
new file mode 100644
index 0000000..1a31cb4
Binary files /dev/null and b/target/interpreter/bytecodes/Store.class differ
diff --git a/target/interpreter/bytecodes/Verbose.class b/target/interpreter/bytecodes/Verbose.class
new file mode 100644
index 0000000..3c5a891
Binary files /dev/null and b/target/interpreter/bytecodes/Verbose.class differ
diff --git a/target/interpreter/bytecodes/Write.class b/target/interpreter/bytecodes/Write.class
new file mode 100644
index 0000000..fdb6e2b
Binary files /dev/null and b/target/interpreter/bytecodes/Write.class differ
diff --git a/target/interpreter/loaders/ByteCodeLoader.class b/target/interpreter/loaders/ByteCodeLoader.class
new file mode 100644
index 0000000..3fb1ba0
Binary files /dev/null and b/target/interpreter/loaders/ByteCodeLoader.class differ
diff --git a/target/interpreter/loaders/CodeTable.class b/target/interpreter/loaders/CodeTable.class
new file mode 100644
index 0000000..b7c1455
Binary files /dev/null and b/target/interpreter/loaders/CodeTable.class differ
diff --git a/target/interpreter/loaders/InvalidProgramException.class b/target/interpreter/loaders/InvalidProgramException.class
new file mode 100644
index 0000000..ac9737f
Binary files /dev/null and b/target/interpreter/loaders/InvalidProgramException.class differ
diff --git a/target/interpreter/loaders/Program.class b/target/interpreter/loaders/Program.class
new file mode 100644
index 0000000..5b90682
Binary files /dev/null and b/target/interpreter/loaders/Program.class differ
diff --git a/target/interpreter/virtualmachine/RunTimeStack.class b/target/interpreter/virtualmachine/RunTimeStack.class
new file mode 100644
index 0000000..4de18e5
Binary files /dev/null and b/target/interpreter/virtualmachine/RunTimeStack.class differ
diff --git a/target/interpreter/virtualmachine/VirtualMachine.class b/target/interpreter/virtualmachine/VirtualMachine.class
new file mode 100644
index 0000000..d68941c
Binary files /dev/null and b/target/interpreter/virtualmachine/VirtualMachine.class differ

~~~

</details>




## Compiling Source Code Results: 



~~~bash

~~~
    


## Code Review


<details>
    <summary>./interpreter/Interpreter.java</summary>

~~~java
package interpreter;

import interpreter.loaders.ByteCodeLoader;
import interpreter.loaders.CodeTable;
import interpreter.loaders.InvalidProgramException;
import interpreter.loaders.Program;
import interpreter.virtualmachine.VirtualMachine;

/**
 * <pre>
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the ByteCodes from file
 *     3. Run the virtual machine
 * 
 *     THIS FILE CANNOT BE MODIFIED. DO NOT
 *     LET ANY EXCEPTIONS REACH THE
 * 
 * </pre>
 */
public class Interpreter {

    private ByteCodeLoader byteCodeLoader;

    public Interpreter(String codeFile) {
        byteCodeLoader = new ByteCodeLoader(codeFile);
    }

    void run() {
        CodeTable.init();
        Program program = null;
        try{
            program = byteCodeLoader.loadCodes();
        } catch(InvalidProgramException ex){
            System.out.println(ex);
            ex.printStackTrace();
            System.exit(-2);
        }
        program.resolveAddress();
        VirtualMachine virtualMachine = new VirtualMachine(program);
        virtualMachine.executeProgram();
    }

    public static void main(String args[]) {

        if (args.length == 0) {
            System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(1);
        }
        (new Interpreter(args[0])).run();
    }
}
~~~

</details>



<details>
    <summary>./interpreter/virtualmachine/VirtualMachine.java</summary>

~~~java
package interpreter.virtualmachine; 
 
import interpreter.bytecodes.ByteCode; 
import interpreter.loaders.Program; 
import java.util.Stack; 
 
public class VirtualMachine { 
 
    private RunTimeStack runTimeStack; 
    private Stack<Integer> returnAddress; 
    private Program program; 
    private int programCounter; 
    private boolean isRunning; 
    private boolean verboseMode; 
 
    public VirtualMachine(Program program) { 
        this.program = program; 
        this.runTimeStack = new RunTimeStack(); 
        this.returnAddress = new Stack<>(); 
        this.programCounter = 0; 
        this.isRunning = true; 
        this.verboseMode = true; 
    } 
 
    public void executeProgram() { 
        while (isRunning) { 
            ByteCode code = program.getCode(programCounter); 
            System.out.println(code.toString()); // Print current bytecode being executed 
            code.execute(this); 
            if (verboseMode) { 
                System.out.println(runTimeStack.verboseDisplay()); // Print stack state after execution 
            } 
            programCounter++; 
            if (programCounter >= program.getSize()) { 
                isRunning = false; 
            } 
        } 
    } 
 
    public void stopExecution() { 
        isRunning = false; 
    } 
 
    public void pushRunStack(int value) { 
        runTimeStack.push(value); 
    } 
 
    public void popRunStack(int numToPop) { 
        for (int i = 0; i < numToPop; i++) { 
            runTimeStack.pop(); 
        } 
    } 
 
    public int pop() { 
        return runTimeStack.pop(); 
    } 
 
    public void push(int value) { 
        runTimeStack.push(value); 
    } 
 
    public int loadFromRunStack(int offset) { 
        return runTimeStack.load(offset); 
    } 
 
    public int store(int offset) { 
        return runTimeStack.store(offset); 
    } 
 
    public void newFrameAt(int numArgs) { 
        runTimeStack.newFrameAt(numArgs); 
    } 
 
    public void popFrame() { 
        runTimeStack.popFrame(); 
    } 
 
    public void pushReturnAddress(int address) { 
        returnAddress.push(address); 
    } 
 
    public int popReturnAddress() { 
        return returnAddress.pop(); 
    } 
 
    public void setProgramCounter(int newPC) { 
        this.programCounter = newPC; 
    } 
 
    public int getProgramCounter() { 
        return this.programCounter; 
    } 
 
    public void setVerboseMode(boolean mode) { 
        this.verboseMode = mode; 
    } 
 
    public boolean isVerboseMode() { 
        return this.verboseMode; 
    } 
 
    public int peekRunStack() { 
        return runTimeStack.peek(); 
    } 
}
~~~

</details>



<details>
    <summary>./interpreter/virtualmachine/RunTimeStack.java</summary>

~~~java
package interpreter.virtualmachine; 
 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Stack; 
 
public class RunTimeStack { 
 
    private List<Integer> runTimeStack; 
    private Stack<Integer> framePointer; 
 
    public RunTimeStack() { 
        this.runTimeStack = new ArrayList<>(); 
        this.framePointer = new Stack<>(); 
        framePointer.push(0); // Start frame pointer at the base 
    } 
 
    public int peek() { 
        return runTimeStack.get(runTimeStack.size() - 1); 
    } 
 
    public int push(int value) { 
        runTimeStack.add(value); 
        return value; 
    } 
 
    public int pop() { 
        return runTimeStack.remove(runTimeStack.size() - 1); 
    } 
 
    public int store(int offset) { 
        int location = framePointer.peek() + offset; 
        int valueToStore = runTimeStack.get(runTimeStack.size() - 1); 
        runTimeStack.set(location, valueToStore); 
        return runTimeStack.remove(runTimeStack.size() - 1); 
    } 
 
    public int load(int offset) { 
        int location = framePointer.peek() + offset; 
        int valueToLoad = runTimeStack.get(location); 
        runTimeStack.add(valueToLoad); 
        return valueToLoad; 
    } 
 
    public void newFrameAt(int offset) { 
        framePointer.push(runTimeStack.size() - offset); 
    } 
 
    public void popFrame() { 
        int frameStart = framePointer.pop(); 
        int returnValue = runTimeStack.get(runTimeStack.size() - 1); 
        for (int i = runTimeStack.size() - 1; i >= frameStart; i--) { 
            runTimeStack.remove(i); 
        } 
        if (framePointer.size() > 0) { 
            runTimeStack.add(returnValue); 
        } 
    } 
 
    public String verboseDisplay() { 
        StringBuilder sb = new StringBuilder(); 
        int lastFrameIndex = 0; 
        for (Integer frameStart : framePointer) { 
            sb.append("["); 
            for (int i = frameStart; i < runTimeStack.size(); i++) { 
                sb.append(runTimeStack.get(i)).append(" "); 
            } 
            sb.append("] "); 
            lastFrameIndex = frameStart; 
        } 
        return sb.toString(); 
    } 
}
~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Load.java</summary>

~~~java
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
~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Verbose.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Verbose extends ByteCode {
    private String mode; // Holds the "ON" or "OFF" state.

    public Verbose(List<String> byteCodeLine) {
        super(byteCodeLine);
        if (byteCodeLine.size() > 1) {
            this.mode = byteCodeLine.get(1).toUpperCase();
            if (!"ON".equals(mode) && !"OFF".equals(mode)) {
                throw new IllegalArgumentException("Invalid mode for VERBOSE bytecode. Only 'ON' or 'OFF' expected.");
            }
        } else {
            throw new IllegalArgumentException("VERBOSE bytecode requires one argument ('ON' or 'OFF').");
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setVerboseMode("ON".equals(mode));
    }

    @Override
    public String toString() {
        return "VERBOSE " + mode;
    }
}

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/ByteCode.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * Abstract class ByteCode serves as the base for all bytecode classes in the language X.
 * Each bytecode class will extend this class and implement the execute method to define
 * its specific behavior.
 */
public abstract class ByteCode {
    // The name of the bytecode, typically the first token in the line from the .cod file
    private String code;

    /**
     * Constructs a ByteCode object using the first token of a line from a .cod file
     * which typically represents the bytecode name.
     *
     * @param byteCodeLine the list of strings parsed from a line in the .cod file,
     *                     where the first element is the bytecode name.
     */
    public ByteCode(List<String> byteCodeLine) {
        if (byteCodeLine != null && !byteCodeLine.isEmpty()) {
            this.code = byteCodeLine.get(0);
        }
    }

    /**
     * Retrieves the bytecode name.
     *
     * @return the name of the bytecode.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns a string representation of the bytecode,
     * which is primarily used for debugging and logging purposes.
     *
     * @return the name of the bytecode as a string.
     */
    @Override
    public String toString() {
        return this.code;
    }

    /**
     * Abstract method that each specific bytecode class will implement.
     * This method defines how the bytecode interacts with the VirtualMachine.
     *
     * @param vm the VirtualMachine instance that is executing this bytecode.
     */
    public abstract void execute(VirtualMachine vm);
}

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/HaltCode.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * HaltCode is a ByteCode class that signals the VirtualMachine to stop executing
 * the program. It does not terminate the interpreter but merely stops the program's execution.
 */
public class HaltCode extends ByteCode {

    /**
     * Constructs a HaltCode object. Halt takes no arguments, so the constructor does not
     * need to process any additional input other than the standard bytecode line handling.
     *
     * @param byteCodeLine the parsed line from the .cod file. Halt expects no arguments.
     */
    public HaltCode(List<String> byteCodeLine) {
        super(byteCodeLine);  // Pass to the base class constructor
    }

    /**
     * Executes the Halt operation on the given VirtualMachine. This method instructs
     * the VirtualMachine to cease program execution.
     *
     * @param vm the VirtualMachine instance that is executing this bytecode.
     */
    @Override
    public void execute(VirtualMachine vm) {
        vm.stopExecution();  // A method you need to implement in the VirtualMachine class
    }

    /**
     * Provides a string representation of the bytecode for debugging and logging purposes.
     * In VERBOSE mode, this bytecode does not need to display any additional information.
     *
     * @return the name of this bytecode, "HALT".
     */
    @Override
    public String toString() {
        return "HALT";
    }
}

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Return.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Lit.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Call.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

public class Call extends ByteCode {
    private String functionLabel;
    private int targetAddress;  // This address should be resolved before execution starts

    public Call(List<String> args) {
        super(args);
        if (args.size() > 1) {
            this.functionLabel = args.get(1); // Ensure that there is a label argument
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushReturnAddress(vm.getProgramCounter() + 1); // Correctly pushing the return address
        vm.setProgramCounter(targetAddress); // Setting the program counter to the target address
    }

    public void setTargetAddress(int address) {
        this.targetAddress = address; // Used during address resolution
    }
    public String getLabel() {
        return functionLabel;
    }

    @Override
    public String toString() {
        return "CALL " + functionLabel; // Simple string representation
    }
}

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Args.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Label.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Store.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/FalseBranch.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Bop.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/PopCode.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * PopCode is a ByteCode that removes a specified number of values from the runtime stack.
 * It ensures that the removal does not cross frame boundaries.
 */
public class PopCode extends ByteCode {
    // Number of values to pop from the runtime stack
    private int numToPop;

    /**
     * Constructs a PopCode object. This constructor initializes the bytecode with the number
     * of values to pop from the stack, which is provided as an argument in the bytecode line.
     *
     * @param byteCodeLine the parsed line from the .cod file, expecting the second element
     *                     to be the number of values to pop.
     */
    public PopCode(List<String> byteCodeLine) {
        super(byteCodeLine);
        if (byteCodeLine.size() > 1) {
            try {
                this.numToPop = Integer.parseInt(byteCodeLine.get(1));
            } catch (NumberFormatException e) {
                // Handle potential parsing error, perhaps set to a default or throw an exception
                throw new IllegalArgumentException("Invalid number format for POP bytecode");
            }
        } else {
            throw new IllegalArgumentException("POP bytecode requires one argument");
        }
    }

    /**
     * Executes the Pop operation on the given VirtualMachine. This method removes the specified
     * number of values from the runtime stack without crossing frame boundaries.
     *
     * @param vm the VirtualMachine instance that is executing this bytecode.
     */
    @Override
    public void execute(VirtualMachine vm) {
        vm.popRunStack(numToPop);
    }

    /**
     * Provides a string representation of the Pop bytecode, suitable for VERBOSE mode display.
     * It returns the operation name "POP" followed by the number of elements it is set to pop.
     *
     * @return the verbose display string for this bytecode, e.g., "POP 3".
     */
    @Override
    public String toString() {
        return "POP " + numToPop;
    }
}

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Write.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;

import java.util.List;

/**
 * The Write ByteCode is used to display information to the console. 
 * The only thing Write is allowed to display is the top value of the runtime stack.
 */
public class Write extends ByteCode {

    public Write(List<String> byteCodeLine) {
        super(byteCodeLine);  // Ensure compatibility with the ByteCode superclass constructor
    }

    @Override
    public void execute(VirtualMachine vm) {
        int topValue = vm.peekRunStack();  // Assume a method exists to peek the top of the stack
        System.out.println(topValue);      // Print the top of the runtime stack
        if (vm.isVerboseMode()) {          // Check VERBOSE mode for conditional display
            System.out.println(this);      // Print bytecode information if VERBOSE mode is on
        }
    }

    @Override
    public String toString() {
        return "WRITE";
    }
}

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Read.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;

import java.util.List;
import java.util.Scanner;

public class Read extends ByteCode {
    // Using a static scanner to avoid creating multiple instances if not necessary
    private static final Scanner scanner = new Scanner(System.in);

    public Read(List<String> args) {
        super(args);  // Assuming superclass requires this for some reason
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.print("Please enter an integer: "); // Prompt as per requirement
        while (!scanner.hasNextInt()) {
            scanner.nextLine(); // Clear the invalid input before re-prompting
            System.out.println("Invalid input. Please enter an integer.");
            System.out.print("Please enter an integer: ");
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline
        vm.pushRunStack(input); // Push the validated integer to the runtime stack
        
        if (vm.isVerboseMode()) {
            System.out.println(this); // Display this bytecode's action if VERBOSE mode is ON
        }
    }

    @Override
    public String toString() {
        return "READ"; // Ensures the display format is as specified
    }
}

~~~

</details>



<details>
    <summary>./interpreter/bytecodes/Goto.java</summary>

~~~java
package interpreter.bytecodes;

import interpreter.virtualmachine.VirtualMachine;
import java.util.List;

/**
 * Goto ByteCode for unconditional jumps within the program.
 */
public class Goto extends ByteCode {
    private String label;
    private int targetAddress;  // This will be resolved before the program begins executing.

    public Goto(List<String> args) {
        super(args);
        if (!args.isEmpty()) {
            this.label = args.get(1);  // Assuming the label is the second token.
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setProgramCounter(targetAddress);  // Unconditionally jump to the resolved label address.
    }

    public void setTargetAddress(int address) {
        this.targetAddress = address;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "GOTO " + label;
    }
}

~~~

</details>



<details>
    <summary>./interpreter/loaders/ByteCodeLoader.java</summary>

~~~java
package interpreter.loaders;

import interpreter.bytecodes.ByteCode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ByteCodeLoader {
    private String codSourceFileName;

    /**
     * Constructs ByteCodeLoader object given a COD source code file name.
     * @param fileName name of .cod File to load.
     */
    public ByteCodeLoader(String fileName) {
        this.codSourceFileName = fileName;
    }

    /**
     * Loads a program from a .cod file.
     * @return a constructed Program Object.
     * @throws InvalidProgramException thrown when loadCodes fails.
     */
    public Program loadCodes() throws InvalidProgramException {
        Program program = new Program();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.codSourceFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> tokens = parseTokens(line);
                if (tokens.isEmpty()) {
                    continue; // Skip empty lines or handle them as needed
                }
                String bytecodeName = tokens.get(0);
                String className = CodeTable.getClassName(bytecodeName);
                if (className == null) {
                    throw new InvalidProgramException("Invalid bytecode '" + bytecodeName + "' found in .cod file.");
                }
                ByteCode bytecode = (ByteCode) Class.forName(className).getDeclaredConstructor(List.class).newInstance(tokens);
                program.addCode(bytecode);
            }
        } catch (IOException | ReflectiveOperationException e) {
            throw new InvalidProgramException(e, "Failed to load bytecodes from file: " + this.codSourceFileName);
        }
        

        return program;
    }

    /**
     * Tokenizes a line of input from the .cod file into individual elements.
     * @param line the line to tokenize.
     * @return a list of tokens.
     */
    private List<String> parseTokens(String line) {
        String[] parts = line.trim().split("\\s+");
        return new ArrayList<>(List.of(parts));
    }
}

~~~

</details>



<details>
    <summary>./interpreter/loaders/CodeTable.java</summary>

~~~java
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

~~~

</details>



<details>
    <summary>./interpreter/loaders/InvalidProgramException.java</summary>

~~~java
package interpreter.loaders;

/**
 * Exception for when loadCode fails.
 * This exception is used to bubble up all
 * exceptions that can be thrown by loadCodes.
 * 
 * DO NOT ADD ANY ADDITIONAL Constructors.
 */
public class InvalidProgramException extends Exception {
    public InvalidProgramException(Throwable ex) {
        super(ex);
    }

    public InvalidProgramException(Throwable ex, String message){
        super(message, ex);
    }

    public InvalidProgramException(String message){
        super(message);
    }
}

~~~

</details>



<details>
    <summary>./interpreter/loaders/Program.java</summary>

~~~java
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

~~~

</details>




## Factorial Verbose -- Input : 6



~~~bash
VERBOSE ON
[] 
GOTO start<<1>>
[] 
GOTO continue<<3>>
[] 
ARGS 0
[] [] 
CALL Read
[] [] 
READ
Please enter an integer: Exception in thread "main" java.util.NoSuchElementException: No line found
	at java.base/java.util.Scanner.nextLine(Scanner.java:1660)
	at interpreter.bytecodes.Read.execute(Read.java:25)
	at interpreter.virtualmachine.VirtualMachine.executeProgram(VirtualMachine.java:29)
	at interpreter.Interpreter.run(Interpreter.java:41)
	at interpreter.Interpreter.main(Interpreter.java:50)

~~~
    


## Factorial -- Input : 6



~~~bash
GOTO start<<1>>
[] 
GOTO continue<<3>>
[] 
ARGS 0
[] [] 
CALL Read
[] [] 
READ
Please enter an integer: Exception in thread "main" java.util.NoSuchElementException: No line found
	at java.base/java.util.Scanner.nextLine(Scanner.java:1660)
	at interpreter.bytecodes.Read.execute(Read.java:25)
	at interpreter.virtualmachine.VirtualMachine.executeProgram(VirtualMachine.java:29)
	at interpreter.Interpreter.run(Interpreter.java:41)
	at interpreter.Interpreter.main(Interpreter.java:50)

~~~
    


## Fib Verbose -- Input : 6



~~~bash
VERBOSE ON
[] 
GOTO start<<1>>
[] 
LIT 0 x int x
[0 ] 
GOTO continue<<3>>
[0 ] 
LIT 0 k int k
[0 0 ] 
LIT 5
[0 0 5 ] 
STORE 0 x x=0
[5 0 ] 
ARGS 0
[5 0 ] [] 
CALL Read
[5 0 ] [] 
READ
Please enter an integer: Exception in thread "main" java.util.NoSuchElementException: No line found
	at java.base/java.util.Scanner.nextLine(Scanner.java:1660)
	at interpreter.bytecodes.Read.execute(Read.java:25)
	at interpreter.virtualmachine.VirtualMachine.executeProgram(VirtualMachine.java:29)
	at interpreter.Interpreter.run(Interpreter.java:41)
	at interpreter.Interpreter.main(Interpreter.java:50)

~~~
    


## Fib -- Input : 6



~~~bash
GOTO start<<1>>
[] 
LIT 0 x int x
[0 ] 
GOTO continue<<3>>
[0 ] 
LIT 0 k int k
[0 0 ] 
LIT 5
[0 0 5 ] 
STORE 0 x x=0
[5 0 ] 
ARGS 0
[5 0 ] [] 
CALL Read
[5 0 ] [] 
READ
Please enter an integer: Exception in thread "main" java.util.NoSuchElementException: No line found
	at java.base/java.util.Scanner.nextLine(Scanner.java:1660)
	at interpreter.bytecodes.Read.execute(Read.java:25)
	at interpreter.virtualmachine.VirtualMachine.executeProgram(VirtualMachine.java:29)
	at interpreter.Interpreter.run(Interpreter.java:41)
	at interpreter.Interpreter.main(Interpreter.java:50)

~~~
    


## Function Args Verbose Test



~~~bash
VERBOSE ON
[] 
GOTO CONTINUE<<1>>
[] 
GOTO CONTINUE<<2>>
[] 
GOTO CONTINUE<<3>>
[] 
GOTO CONTINUE<<4>>
[] 
LIT 0
[0 ] 
LIT 1
[0 1 ] 
ARGS 2
[0 1 ] [0 1 ] 
CALL doublePrint<<1>>
[0 1 ] [0 1 ] 
LOAD 0
[0 1 0 ] [0 1 0 ] 
WRITE
0
WRITE
[0 1 0 ] [0 1 0 ] 
POP 1
[0 1 ] [0 1 ] 
LOAD 1
[0 1 1 ] [0 1 1 ] 
WRITE
1
WRITE
[0 1 1 ] [0 1 1 ] 
POP 1
[0 1 ] [0 1 ] 
RETURN doublePrint<<1>>
[1 ] 
LIT 0
[1 0 ] 
LIT 1
[1 0 1 ] 
LIT 2
[1 0 1 2 ] 
ARGS 3
[1 0 1 2 ] [0 1 2 ] 
CALL triplePrint<<1>>
[1 0 1 2 ] [0 1 2 ] 
LOAD 0
[1 0 1 2 0 ] [0 1 2 0 ] 
WRITE
0
WRITE
[1 0 1 2 0 ] [0 1 2 0 ] 
POP 1
[1 0 1 2 ] [0 1 2 ] 
LOAD 1
[1 0 1 2 1 ] [0 1 2 1 ] 
WRITE
1
WRITE
[1 0 1 2 1 ] [0 1 2 1 ] 
POP 1
[1 0 1 2 ] [0 1 2 ] 
LOAD 2
[1 0 1 2 2 ] [0 1 2 2 ] 
WRITE
2
WRITE
[1 0 1 2 2 ] [0 1 2 2 ] 
POP 2
[1 0 1 ] [0 1 ] 
RETURN triplePrint<<1>>
[1 1 ] 
LIT 0
[1 1 0 ] 
LIT 1
[1 1 0 1 ] 
LIT 2
[1 1 0 1 2 ] 
LIT 3
[1 1 0 1 2 3 ] 
ARGS 4
[1 1 0 1 2 3 ] [0 1 2 3 ] 
CALL quadruplePrint<<1>>
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 0
[1 1 0 1 2 3 0 ] [0 1 2 3 0 ] 
WRITE
0
WRITE
[1 1 0 1 2 3 0 ] [0 1 2 3 0 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 1
[1 1 0 1 2 3 1 ] [0 1 2 3 1 ] 
WRITE
1
WRITE
[1 1 0 1 2 3 1 ] [0 1 2 3 1 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 2
[1 1 0 1 2 3 2 ] [0 1 2 3 2 ] 
WRITE
2
WRITE
[1 1 0 1 2 3 2 ] [0 1 2 3 2 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 3
[1 1 0 1 2 3 3 ] [0 1 2 3 3 ] 
WRITE
3
WRITE
[1 1 0 1 2 3 3 ] [0 1 2 3 3 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
RETURN quadruplePrint<<1>>
[1 1 3 ] 
HALT
[1 1 3 ] 

~~~
    


## Function Args Test



~~~bash
GOTO CONTINUE<<1>>
[] 
GOTO CONTINUE<<2>>
[] 
GOTO CONTINUE<<3>>
[] 
GOTO CONTINUE<<4>>
[] 
LIT 0
[0 ] 
LIT 1
[0 1 ] 
ARGS 2
[0 1 ] [0 1 ] 
CALL doublePrint<<1>>
[0 1 ] [0 1 ] 
LOAD 0
[0 1 0 ] [0 1 0 ] 
WRITE
0
WRITE
[0 1 0 ] [0 1 0 ] 
POP 1
[0 1 ] [0 1 ] 
LOAD 1
[0 1 1 ] [0 1 1 ] 
WRITE
1
WRITE
[0 1 1 ] [0 1 1 ] 
POP 1
[0 1 ] [0 1 ] 
RETURN doublePrint<<1>>
[1 ] 
LIT 0
[1 0 ] 
LIT 1
[1 0 1 ] 
LIT 2
[1 0 1 2 ] 
ARGS 3
[1 0 1 2 ] [0 1 2 ] 
CALL triplePrint<<1>>
[1 0 1 2 ] [0 1 2 ] 
LOAD 0
[1 0 1 2 0 ] [0 1 2 0 ] 
WRITE
0
WRITE
[1 0 1 2 0 ] [0 1 2 0 ] 
POP 1
[1 0 1 2 ] [0 1 2 ] 
LOAD 1
[1 0 1 2 1 ] [0 1 2 1 ] 
WRITE
1
WRITE
[1 0 1 2 1 ] [0 1 2 1 ] 
POP 1
[1 0 1 2 ] [0 1 2 ] 
LOAD 2
[1 0 1 2 2 ] [0 1 2 2 ] 
WRITE
2
WRITE
[1 0 1 2 2 ] [0 1 2 2 ] 
POP 2
[1 0 1 ] [0 1 ] 
RETURN triplePrint<<1>>
[1 1 ] 
LIT 0
[1 1 0 ] 
LIT 1
[1 1 0 1 ] 
LIT 2
[1 1 0 1 2 ] 
LIT 3
[1 1 0 1 2 3 ] 
ARGS 4
[1 1 0 1 2 3 ] [0 1 2 3 ] 
CALL quadruplePrint<<1>>
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 0
[1 1 0 1 2 3 0 ] [0 1 2 3 0 ] 
WRITE
0
WRITE
[1 1 0 1 2 3 0 ] [0 1 2 3 0 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 1
[1 1 0 1 2 3 1 ] [0 1 2 3 1 ] 
WRITE
1
WRITE
[1 1 0 1 2 3 1 ] [0 1 2 3 1 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 2
[1 1 0 1 2 3 2 ] [0 1 2 3 2 ] 
WRITE
2
WRITE
[1 1 0 1 2 3 2 ] [0 1 2 3 2 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
LOAD 3
[1 1 0 1 2 3 3 ] [0 1 2 3 3 ] 
WRITE
3
WRITE
[1 1 0 1 2 3 3 ] [0 1 2 3 3 ] 
POP 1
[1 1 0 1 2 3 ] [0 1 2 3 ] 
RETURN quadruplePrint<<1>>
[1 1 3 ] 
HALT
[1 1 3 ] 

~~~
    
