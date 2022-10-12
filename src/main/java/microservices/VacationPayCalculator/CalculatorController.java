package microservices.VacationPayCalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class CalculatorController {

    @GetMapping("/calculate")
    public double getVacationPay(@RequestParam double averageSalary, int numOfVacationDays, Date firstVacationDay, Date lastVacationDay) {

        if (firstVacationDay == null & lastVacationDay == null) return averageSalary * 29.3 * numOfVacationDays;
        return 0;
    }
}
