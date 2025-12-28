package console_app;

import model.*;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Connects the hardware, sets up the logic, and runs the loop every second.
 */
public class Main {
    
    private static int timeSeconds = 0;

    public static void main(String[] args) {
        try {
            HardwareController hardware = new HardwareController(WateringConfig.PORT_NAME);
            hardware.startConnection();

            WateringLogic logic = new WateringLogic();
            Dashboard dashboard = new Dashboard(hardware);
            Timer timer = new Timer();

            System.out.println("System Started. Monitoring soil...");

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        long moisture = hardware.getMoistureReading();
                        double voltage = logic.convertToVoltage(moisture);
                        
                        boolean pumpOn = logic.shouldPumpBeActive(moisture);
                        String status = pumpOn ? "PUMP: ON (Dry)" : "PUMP: OFF (Wet)";

                        hardware.setPumpState(pumpOn);
                        dashboard.update(timeSeconds++, moisture, voltage, status);

                        System.out.println("T=" + timeSeconds + "s | Raw: " + moisture + " | " + status);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000); 

        } catch (Exception e) {
            System.err.println("Fatal Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}