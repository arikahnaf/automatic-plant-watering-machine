package junit_tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.WateringLogic;

class WateringLogicTest {

    @Test
    void testDrySoilActivatesPump() {
        WateringLogic logic = new WateringLogic();
        boolean result = logic.shouldPumpBeActive(800); 
        assertTrue(result, "Pump should trigger when soil is dry (>= 700)");
    }

    @Test
    void testWetSoilStopsPump() {
        WateringLogic logic = new WateringLogic();
        logic.shouldPumpBeActive(800); // Set state to ON
        
        boolean result = logic.shouldPumpBeActive(400); 
        assertFalse(result, "Pump should stop when soil is wet (<= 550)");
    }
}