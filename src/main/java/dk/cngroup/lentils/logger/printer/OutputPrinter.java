package dk.cngroup.lentils.logger.printer;

import java.io.*;

public abstract class OutputPrinter implements Printer {

    protected PrintStream output;

    public abstract void initOutput();

    @Override
    public <T> void println(T message) {
        output.println(message);
    }
}
