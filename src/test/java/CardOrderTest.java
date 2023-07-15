import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);

    @Test
    void shouldTestCardOrderTask1() {
        open("http://localhost:9999/");
        SelenideElement form = $(By.className("form"));
        form.$("[data-test-id=city] input").setValue("Уфа");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(planningDate);
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$(By.className("button")).click();
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(20))
                .shouldBe(Condition.visible);
    }

    public String generateNewDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("d"));
    }
    int plusDays = 17;

    String currentPlanningDate = generateDate(plusDays);
    int newPlanningDate = Integer.parseInt(generateNewDate(plusDays));
    int currentDate = Integer.parseInt(generateNewDate(0));

    @Test
    void shouldTestCardOrderTask2() {
        open("http://localhost:9999/");
        SelenideElement form = $(By.className("form"));
        form.$("[data-test-id=city] input").setValue("Но");
        $$(By.className("menu-item__control")).findBy(text("Новосибирск")).click();
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String newCurrentDate;
        if (newPlanningDate < currentDate) {
            $("[data-step=\"1\"]").click();
            newCurrentDate = Integer.toString(newPlanningDate);
        } else {
            newCurrentDate = Integer.toString(newPlanningDate);
        }
            $$(By.className("calendar__day")).findBy(text(newCurrentDate)).click();
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$(By.className("button")).click();
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + currentPlanningDate), Duration.ofSeconds(20))
                .shouldBe(Condition.visible);
    }
}