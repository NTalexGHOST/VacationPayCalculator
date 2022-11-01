package microservices.VacationPayCalculator;

import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

public class JUnitControllerTest {

    CalculatorController calculatorController = new CalculatorController();
    double actualVacationPay, expectedVacationPay;

    @Test
    public void testSimpleMethodForEqual() {

        actualVacationPay = calculatorController.getVacationPay(29000, 7);
        expectedVacationPay = calculatorController.formatVacationPay(29000 / 29.3 * 7);
        assertEquals("Testing a simple method for equal", expectedVacationPay, actualVacationPay);
    }

    @Test
    public void testSimpleMethodForNotEqual() {

        actualVacationPay = calculatorController.getVacationPay(52796, 14);
        expectedVacationPay = calculatorController.formatVacationPay(27416 / 29.3 * 3);
        assertNotEquals("Testing a simple method for not equal", expectedVacationPay, actualVacationPay);
    }

    @Test
    public void testAdvancedMethodForEqual() {

        actualVacationPay = calculatorController.getVacationPay(68420, "01.01.2022", "23.01.2022");
        expectedVacationPay = calculatorController.formatVacationPay(68420 / 29.3 * 18);
        assertEquals("Testing an advanced method for not equal", expectedVacationPay, actualVacationPay);
    }
}
