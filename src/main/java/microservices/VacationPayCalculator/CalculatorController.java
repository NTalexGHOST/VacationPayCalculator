package microservices.VacationPayCalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
public class CalculatorController {

    ProductionCalendar productionCalendar = new ProductionCalendar();

    public double formatVacationPay(double vacationPay) {

        return Double.parseDouble(String.format("%.2f", vacationPay).replace(',', '.'));
    }

    @GetMapping(value = "/calculate", params = {"averageSalary", "numOfVacationDays"})
    public double getVacationPay(@RequestParam double averageSalary, int numOfVacationDays) {

        return formatVacationPay(averageSalary / 29.3 * numOfVacationDays);
    }

    @GetMapping(value = "/calculate", params = {"averageSalary", "firstVacationDay", "lastVacationDay"})
    public double getVacationPay(@RequestParam double averageSalary, String firstVacationDay, String lastVacationDay) {

        double vacationPay = 0.0d;
        int numOfPaymentDays = 0;
        String[] tempStringForUnformattedDate;

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        tempStringForUnformattedDate = firstVacationDay.split(".");
        Calendar currentDay = new GregorianCalendar(Integer.parseInt(tempStringForUnformattedDate[2]),
                Integer.parseInt(tempStringForUnformattedDate[1]), Integer.parseInt(tempStringForUnformattedDate[0]));

        tempStringForUnformattedDate = lastVacationDay.split(".");
        Calendar lastDay = new GregorianCalendar(Integer.parseInt(tempStringForUnformattedDate[2]),
                Integer.parseInt(tempStringForUnformattedDate[1]), Integer.parseInt(tempStringForUnformattedDate[0]));
        lastDay.add(Calendar.DAY_OF_MONTH, 1);

        while (currentDay != lastDay) {
            if (!productionCalendar.isHoliday(dateFormat.format(currentDay.getTime())))
                numOfPaymentDays += 1;
            currentDay.add(Calendar.DAY_OF_MONTH, 1);
        }

        vacationPay = averageSalary / 29.3 * numOfPaymentDays;

        return formatVacationPay(vacationPay);
    }
}
