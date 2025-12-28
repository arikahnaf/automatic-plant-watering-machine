package model;

import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.I2CDevice;
import edu.princeton.cs.algs4.StdDraw;
import java.io.IOException;
import java.util.HashMap;

/*
 * Handles the visuals: The Graph on the laptop and the OLED screen on the kit.
 */
public class Dashboard {
    
    private SSD1306 oledDisplay;
    private HashMap<Integer, Double> voltageHistory; 
    
    public Dashboard(HardwareController hardware) {
        this.voltageHistory = new HashMap<>();
        try {
            I2CDevice i2cObject = hardware.getDevice().getI2CDevice(WateringConfig.I2C_ADDRESS);
            oledDisplay = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
            oledDisplay.init();
        } catch (IOException e) {
            System.err.println("Failed to init OLED: " + e.getMessage());
        }
        setupGraph();
    }

    private void setupGraph() {
        StdDraw.setCanvasSize(800, 400);
        StdDraw.setXscale(0, 60);
        StdDraw.setYscale(0, 5.5);
        StdDraw.setPenRadius(0.005);
        StdDraw.text(30, 5.2, "Soil Moisture Voltage vs. Time");
        StdDraw.text(30, -0.2, "Time (Seconds)");
        StdDraw.text(-1, 2.5, "Voltage (V)");
    }

    public void update(int timeSeconds, long rawMoisture, double voltage, String status) {
        voltageHistory.put(timeSeconds, voltage);

        try {
            oledDisplay.getCanvas().clear();
            oledDisplay.getCanvas().drawString(0, 0, "Moisture: " + rawMoisture);
            oledDisplay.getCanvas().drawString(0, 20, "V: " + String.format("%.2f", voltage));
            oledDisplay.getCanvas().drawString(0, 40, status);
            oledDisplay.display();
        } catch (Exception e) { /* Ignore glitches */ }

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(timeSeconds, voltage);
    }
}