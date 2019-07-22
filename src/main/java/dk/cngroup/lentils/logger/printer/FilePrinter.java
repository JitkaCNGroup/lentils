package dk.cngroup.lentils.logger.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Component
@Primary
public class FilePrinter extends OutputPrinter {

    private static Logger logger = LoggerFactory.getLogger(FilePrinter.class);

    @Value("${lentils.actionlogfile:/var/log/lentils/lentils.log}")
    private String logfile;

    @PostConstruct
    public void initOutput() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(createFile(), true)) {
            setOutput(new PrintStream(fileOutputStream));
        } catch (IOException e) {
            logger.warn(
                    "Log file {} cannot be created or open. Log will be written to standard error output.",
                    logfile);
            setOutput(System.out);
        }
    }

    private File createFile() throws IOException {
        final File file = new File(logfile);

        if (file.createNewFile()) {
            logger.info("New log file {} created", logfile);
        } else {
            logger.info("Using existing log file {}", logfile);
        }

        return file;
    }
}
