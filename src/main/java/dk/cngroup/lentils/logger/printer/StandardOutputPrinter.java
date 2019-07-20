package dk.cngroup.lentils.logger.printer;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StandardOutputPrinter extends OutputPrinter {

    @PostConstruct
    public void initOutput() {
        setOutput(System.out);
    }
}
