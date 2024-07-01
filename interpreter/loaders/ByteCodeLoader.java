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
