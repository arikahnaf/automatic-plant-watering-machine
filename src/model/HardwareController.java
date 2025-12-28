package model;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import java.io.IOException;

/*
 * Wrapper for Firmata4j. 
 * Handles the setup for connecting to the Arduino board.
 */
public class HardwareController {

    private IODevice device;
    private Pin pumpPin;
    private Pin moistureSensorPin;

    public HardwareController(String portName) {
        this.device = new FirmataDevice(portName);
    }

    public void startConnection() throws IOException, InterruptedException {
        device.start();
        device.ensureInitializationIsDone();
        System.out.println("Board connected successfully.");

        moistureSensorPin = device.getPin(WateringConfig.MOISTURE_SENSOR_PIN);
        pumpPin = device.getPin(WateringConfig.PUMP_PIN);
        pumpPin.setMode(Pin.Mode.OUTPUT);
    }

    public void setPumpState(boolean on) throws IOException {
        pumpPin.setValue(on ? 1 : 0);
    }

    public long getMoistureReading() {
        return moistureSensorPin.getValue();
    }
    
    public IODevice getDevice() {
        return this.device;
    }
}