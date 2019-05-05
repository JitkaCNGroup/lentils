package dk.cngroup.lentils.logger;

import org.springframework.stereotype.Component;

@Component
public class StdoutPrinter implements Printer {
    @Override
    public void println(String message) {
        System.out.println(message);
    }
}
