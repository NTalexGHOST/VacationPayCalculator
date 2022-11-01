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
        expectedVacationPay = Double.parseDouble(String.format("%.2f", 29000 / 29.3 * 7).replace(',', '.'));
        assertEquals("Testing a simple method for equal", expectedVacationPay, actualVacationPay);
    }

    @Test
    public void testSimpleMethodForNotEqual() {

        actualVacationPay = calculatorController.getVacationPay(52796, 14);
        expectedVacationPay = Double.parseDouble(String.format("%.2f", 27416 / 29.3 * 3).replace(',', '.'));
        assertNotEquals("Testing a simple method for not equal", expectedVacationPay, actualVacationPay);
    }
}
