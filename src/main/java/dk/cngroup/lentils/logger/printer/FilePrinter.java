package dk.cngroup.lentils.logger.printer;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Primary
public class FilePrinter extends OutputPrinter {

    public static final String LOG_FILE = "lentils.log";

    @Override
    public void initOutput() {
        try {
            File file = new File(LOG_FILE);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            output = new PrintStream(fileOutputStream);
        } catch (IOException e) {
            System.err.println("Log file cannot be created or open. Log will be written to standard error output.");
            output = System.out;
        }
    }
}
