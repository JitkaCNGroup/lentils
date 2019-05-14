package dk.cngroup.lentils.logger.printer;

public interface Printer {
    <T> void println(T message);
}
