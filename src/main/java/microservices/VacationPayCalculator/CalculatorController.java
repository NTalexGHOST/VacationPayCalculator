package microservices.VacationPayCalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
public class CalculatorController {

    public double formatVacationPay(double vacationPay) {

        return Double.parseDouble(String.format("%.2f", vacationPay).replace(',', '.'));
    }

    @GetMapping(value = "/calculate", params = {"averageSalary", "numOfVacationDays"})
    public double getVacationPay(@RequestParam double averageSalary, int numOfVacationDays) {

        return formatVacationPay(averageSalary / 29.3 * numOfVacationDays);
    }

    @GetMapping(value = "/calculate", params = {"averageSalary", "firstVacationDay", "lastVacationDay"})
    public double getVacationPay(@RequestParam double averageSalary, String firstVacationDay, String lastVacationDay) {

        ProductionCalendar productionCalendar = new ProductionCalendar();
        double vacationPay = 0.0d;
        int numOfPaymentDays = 0;

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        // Временный строковый массив для разделения даты на день, месяц и год для забивания её в календарь
        String[] tempStringForUnformattedDate = firstVacationDay.split("\\.");
        // Текущий проверяемый день
        Calendar currentDay = new GregorianCalendar(Integer.parseInt(tempStringForUnformattedDate[2]),
                Integer.parseInt(tempStringForUnformattedDate[1]) - 1, Integer.parseInt(tempStringForUnformattedDate[0]));

        tempStringForUnformattedDate = lastVacationDay.split("\\.");
        // Последний день отпуска к которому плюсуется один день для корректной работы цикла
        Calendar lastDay = new GregorianCalendar(Integer.parseInt(tempStringForUnformattedDate[2]),
                Integer.parseInt(tempStringForUnformattedDate[1]) - 1, Integer.parseInt(tempStringForUnformattedDate[0]));
        lastDay.add(Calendar.DAY_OF_MONTH, 1);
        while (!currentDay.equals(lastDay)) {
            if (!productionCalendar.isHoliday(dateFormat.format(currentDay.getTime())))
                numOfPaymentDays += 1;
            currentDay.add(Calendar.DAY_OF_MONTH, 1);
        }

        vacationPay = averageSalary / 29.3 * numOfPaymentDays;

        return formatVacationPay(vacationPay);
    }
}
