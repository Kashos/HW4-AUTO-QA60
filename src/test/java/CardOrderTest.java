import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {

    @Test
    void shouldTestCardOrderTask1() {
        open("http://localhost:9999/");
        SelenideElement form = $(By.className("form"));
        form.$("[data-test-id=city] input").setValue("Уфа");
        form.$("[data-test-id=date] input").setValue("05.07.2023");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$(By.className("button")).click();
        String planningDate = form.$("[data-test-id=date] input").getValue();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(20))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestCardOrderTask2() {
        open("http://localhost:9999/");
        SelenideElement form = $(By.className("form"));
        form.$("[data-test-id=city] input").setValue("Уф");
        $(By.className("menu-item")).click();
        form.$("[data-test-id=date]").click();
        int currentDate = Integer.parseInt($(by("class", "calendar__day calendar__day_state_current")).getText());
        currentDate = currentDate + 7;
        String okDate = Integer.toString(currentDate);
        $(byTagAndText("td", okDate)).click();
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$(By.className("button")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + okDate), Duration.ofSeconds(20))
                .shouldBe(Condition.visible);
    }
}