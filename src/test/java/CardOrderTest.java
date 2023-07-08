import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
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
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(20))
                .shouldBe(Condition.visible);
    }

    public String generateNewDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd"));
    }

    String newPlanningDate = generateNewDate(7);

    @Test
    void shouldTestCardOrderTask2() {
        open("http://localhost:9999/");
        SelenideElement form = $(By.className("form"));
        form.$("[data-test-id=city] input").setValue("Уф");
        $(By.className("menu-item"));
        $(byText("Уфа")).click();
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id=date]").click();
        $(By.className("data-day"));
        $(byText(newPlanningDate)).click();
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$(By.className("button")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + newPlanningDate), Duration.ofSeconds(20))
                .shouldBe(Condition.visible);
    }
}