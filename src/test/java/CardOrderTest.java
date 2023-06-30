import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
public class CardOrderTest {
    @Test
    void shouldTestCardOrder() {
        open("http://localhost:9999/");
        SelenideElement form = $(By.className("form"));
        form.$("[data-test-id=city] input").setValue("Уф");
        $(By.className("menu-item")).click();
        form.$("[data-test-id=date]").click();
        $(by("data-day", "1688922000000")).click();
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$(By.className("button")).click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(20));
    }
}