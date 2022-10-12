package microservices.VacationPayCalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class CalculatorController {

    @GetMapping("/calculate")
    public double getVacationPay(@RequestParam double averageSalary, int numOfVacationDays) {

        return averageSalary * 29.3 * numOfVacationDays;
    }

    /*@GetMapping("/calculate")
    public double getVacationPay(@RequestParam double averageSalary, Date firstVacationDay, Date lastVacationDay) {


        return 0;
    }*/
}
