package dk.cngroup.lentils.logger;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
public class StandardOutputPrinter extends OutputPrinter {

    @Override
    public void initOutput() {
        output = System.out;
    }
}
