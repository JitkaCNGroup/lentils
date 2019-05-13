package dk.cngroup.lentils.logger.printer;

import java.io.*;

public abstract class OutputPrinter implements Printer {

    private PrintStream output;

    public OutputPrinter() {
        initOutput();
    }

    public abstract void initOutput();

    @Override
    public <T> void println(final T message) {
        output.println(message);
    }

    public void setOutput(final PrintStream output) {
        this.output = output;
    }
}
