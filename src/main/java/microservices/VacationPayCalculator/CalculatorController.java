package microservices.VacationPayCalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class CalculatorController {

    @GetMapping(value = "/calculate", params = {"averageSalary", "numOfVacationDays"})
    public double getVacationPay(@RequestParam double averageSalary, int numOfVacationDays) {

        return Double.parseDouble(String.format("%.2f", averageSalary / 29.3 * numOfVacationDays).replace(',', '.'));
    }

    @GetMapping(value = "/calculate", params = {"averageSalary", "firstVacationDay", "lastVacationDay"})
    public double getVacationPay(@RequestParam double averageSalary, Date firstVacationDay, Date lastVacationDay) {

        return 0;
    }
}
