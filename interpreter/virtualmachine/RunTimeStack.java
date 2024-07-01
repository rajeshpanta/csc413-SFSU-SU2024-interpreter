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