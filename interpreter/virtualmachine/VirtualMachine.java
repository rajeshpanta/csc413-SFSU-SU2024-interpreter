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