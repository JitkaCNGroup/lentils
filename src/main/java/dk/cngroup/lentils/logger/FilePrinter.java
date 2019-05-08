package dk.cngroup.lentils.logger;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FilePrinter extends OutputPrinter {

    @Override
    public void initOutput() {
        try {
            File file = new File("log.txt"); // TODO rename file
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            output = new PrintStream(fileOutputStream);
        } catch (IOException e) {
            System.err.println("Log file cannot be created or open. Log will be written to stderr.");
            output = System.out;
        }
    }
}
