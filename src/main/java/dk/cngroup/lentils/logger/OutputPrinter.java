package dk.cngroup.lentils.logger;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public abstract class OutputPrinter implements Printer {

    protected PrintStream output;

    public abstract void initOutput();

    @Override
    public void println(String message) {
        output.println(message);
    }
}
