package dk.cngroup.lentils.logger.printer;

import org.springframework.stereotype.Component;

@Component()
public class StandardOutputPrinter extends OutputPrinter {

    @Override
    public void initOutput() {
        output = System.out;
    }
}
