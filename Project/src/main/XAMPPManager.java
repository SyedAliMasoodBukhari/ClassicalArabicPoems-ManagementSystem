package main;

import java.io.IOException;
import org.apache.logging.log4j.*;

public class XAMPPManager {

    private static final Logger logger = LogManager.getLogger(XAMPPManager.class);

    public static void startXAMPP() {
        String xamppPath = "C:\\xampp";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(xamppPath + "\\xampp_start.exe")
                    .redirectError(ProcessBuilder.Redirect.INHERIT)
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.start();
            Thread.sleep(3000);
            logger.info("XAMPP is Starting....");
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public static void stopXAMPP() {
        String xamppPath = "C:\\xampp";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(xamppPath + "\\xampp_stop.exe")
                    .redirectError(ProcessBuilder.Redirect.INHERIT)
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.start();
            logger.info("XAMPP is Stoping....");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
