package microservices.VacationPayCalculator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ProductionCalendar {

    // Список с праздничными днями
    private List<String> holidayDays = new ArrayList<>();
    // Список проверенных годов
    private List<String> checkedYears = new ArrayList<>();

    public void getHolidaysByYear(String year) {

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            // Задаём html страницу с производственных календарём
            Document document = Jsoup.connect("https://hh.ru/article/calendar" + year)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
            // Выбираем все элементы на странице, соответствующие месяцам
            Elements months = document.select("ul.calendar-list__numbers");
            int monthNum = 0;

            for (Element month : months) {
                // Делаем выборку выходных дней в месяце по соответствующему тегу
                // стоит отметить, что нам нужно получить именно праздничные дни, не являющиеся субботой или воскресеньем, за которые оплата отпускных вестись не будет
                Elements days = month.select("li.calendar-list__numbers__item_day-off");
                for (Element day : days) {
                    Calendar currentDay = new GregorianCalendar(Integer.parseInt(year), monthNum, Integer.parseInt(day.text().split(" ")[0]));
                    dateFormat.format(currentDay.getTime());
                    // Получаем день недели проверяемого дня
                    int dayWeek = currentDay.get(Calendar.DAY_OF_WEEK);

                    // Все нужные нам праздничные дни в документе помечены фразой "Выходной день. " с последующим именованием конкретного праздника
                    // Обычные выходные дни помечены там как просто "Выходной день"
                    // Поэтому выполняем проверку по данной надписи и является ли день субботой или воскресеньем
                    if (dayWeek != 1 & dayWeek != 7 & day.select("div.calendar-hint").get(0).text().contains("Выходной день."))
                        holidayDays.add(dateFormat.format(currentDay.getTime()));
                }
                monthNum += 1;
            }
            checkedYears.add(year);
        } catch (IOException e) { }
    }

    public boolean isHoliday(String date) {

        String year = date.split("\\.")[2];
        // Проверяет, есть ли год в списке уже проверенных, если нет, запускает его проверку
        if (!checkedYears.contains(year)) getHolidaysByYear(year);
        if (holidayDays.contains(date)) return true;

        return false;
    }

}
