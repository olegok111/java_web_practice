package msu.cmc.jaweb;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebTest {

    @Test
    void mainPage() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        driver.quit();
    }

    @Test
    void toMainPage() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/films");
        WebElement mainPageLink = driver.findElementByLinkText("Главная страница");
        mainPageLink.click();
        assertEquals("Главная страница", driver.getTitle());
        driver.quit();
    }

    @Test
    void addClient() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement clientsPageLink = driver.findElementByLinkText("Клиенты");
        clientsPageLink.click();
        driver.findElementById("addClientButton").click();
        driver.findElementById("clientFullName").sendKeys("Тестовый клиент");
        driver.findElementById("clientEmail").sendKeys("test@example.com");
        driver.findElementById("clientPhone").sendKeys("+79111111111");
        driver.findElementById("submitButton").click();
        assertTrue(driver.findElementById("idName").getText().matches(".*Тестовый клиент.*"));
        driver.quit();
    }

    @Test
    void addBadClient() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement clientsPageLink = driver.findElementByLinkText("Клиенты");
        clientsPageLink.click();
        driver.findElementById("addClientButton").click();
        driver.findElementById("clientFullName").sendKeys("Некорректный клиент");
        driver.findElementById("clientEmail").sendKeys("nonaddress");
        driver.findElementById("clientPhone").sendKeys("asdfasdf");
        driver.findElementById("submitButton").click();
        assertEquals("Редактирование клиента", driver.getTitle());
        driver.quit();
    }

    @Test
    void editClient() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement clientsPageLink = driver.findElementByLinkText("Клиенты");
        clientsPageLink.click();
        driver.findElementById("addClientButton").click();
        driver.findElementById("clientFullName").sendKeys("Клиент А");
        driver.findElementById("clientEmail").sendKeys("test@example.com");
        driver.findElementById("clientPhone").sendKeys("+79111111111");
        driver.findElementById("submitButton").click();
        driver.findElementById("editButton").click();
        driver.findElementById("clientFullName").clear();
        driver.findElementById("clientFullName").sendKeys("Клиент Б");
        driver.findElementById("submitButton").click();
        assertTrue(driver.findElementById("idName").getText().matches(".*Клиент Б.*"));
        driver.quit();
    }

    @Test
    void deleteClient() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement clientsPageLink = driver.findElementByLinkText("Клиенты");
        clientsPageLink.click();
        driver.findElementById("addClientButton").click();
        driver.findElementById("clientFullName").sendKeys("Уникальный клиент");
        driver.findElementById("clientEmail").sendKeys("test@example.com");
        driver.findElementById("clientPhone").sendKeys("+79111111111");
        driver.findElementById("submitButton").click();
        Long clientId = Long.parseLong(driver.getCurrentUrl().substring(22)
                .replaceAll("[^0-9]", ""));
        driver.findElementById("deleteButton").click();
        driver.get("http://localhost:8080/clientInfo?clientId=" + clientId);
        assertEquals("Ошибка", driver.getTitle());
        driver.quit();
    }
    
    @Test
    void addFilm() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement filmsPageLink = driver.findElementByLinkText("Фильмы");
        filmsPageLink.click();
        driver.findElementById("addFilmButton").click();
        driver.findElementById("filmTitle").sendKeys("Тестовый фильм");
        driver.findElementById("filmGenre").sendKeys("жанр");
        driver.findElementById("filmCompany").sendKeys("ТестФильм");
        driver.findElementById("filmDirector").sendKeys("Майкл Бэй");
        driver.findElementById("filmReleaseYear").sendKeys("2000");
        driver.findElementById("filmPurchasePrice").sendKeys("100");
        driver.findElementById("submitButton").click();
        assertTrue(driver.findElementById("idName").getText().matches(".*Тестовый фильм.*"));
        driver.quit();
    }

    @Test
    void addBadFilm() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement filmsPageLink = driver.findElementByLinkText("Фильмы");
        filmsPageLink.click();
        driver.findElementById("addFilmButton").click();
        driver.findElementById("filmTitle").sendKeys("Некорректный фильм");
        driver.findElementById("filmGenre").sendKeys("жанр");
        driver.findElementById("filmCompany").sendKeys("ТестФильм");
        driver.findElementById("filmDirector").sendKeys("Майкл Бэй");
        driver.findElementById("filmReleaseYear").sendKeys("фффф");
        driver.findElementById("filmPurchasePrice").sendKeys("ыыыы");
        driver.findElementById("submitButton").click();
        assertEquals("Редактирование фильма", driver.getTitle());
        driver.quit();
    }

    @Test
    void editFilm() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement filmsPageLink = driver.findElementByLinkText("Фильмы");
        filmsPageLink.click();
        driver.findElementById("addFilmButton").click();
        driver.findElementById("filmTitle").sendKeys("Тестовый фильм");
        driver.findElementById("filmGenre").sendKeys("жанр");
        driver.findElementById("filmCompany").sendKeys("ТестФильм");
        driver.findElementById("filmDirector").sendKeys("Майкл Бэй");
        driver.findElementById("filmReleaseYear").sendKeys("2000");
        driver.findElementById("filmPurchasePrice").sendKeys("100");
        driver.findElementById("submitButton").click();
        driver.findElementById("editButton").click();
        driver.findElementById("filmDirector").clear();
        driver.findElementById("filmDirector").sendKeys("Аркадий Гайдай");
        driver.findElementById("submitButton").click();
        assertTrue(driver.findElementById("idName").getText().matches(".*Тестовый фильм.*"));
        driver.quit();
    }

    @Test
    void deleteFilm() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement filmsPageLink = driver.findElementByLinkText("Фильмы");
        filmsPageLink.click();
        driver.findElementById("addFilmButton").click();
        driver.findElementById("filmTitle").sendKeys("Тестовый фильм");
        driver.findElementById("filmGenre").sendKeys("жанр");
        driver.findElementById("filmCompany").sendKeys("ТестФильм");
        driver.findElementById("filmDirector").sendKeys("Майкл Бэй");
        driver.findElementById("filmReleaseYear").sendKeys("2000");
        driver.findElementById("filmPurchasePrice").sendKeys("100");
        driver.findElementById("submitButton").click();
        Long filmId = Long.parseLong(driver.getCurrentUrl().substring(22)
                .replaceAll("[^0-9]", ""));
        driver.findElementById("deleteButton").click();
        driver.get("http://localhost:8080/filmInfo?filmId=" + filmId);
        assertEquals("Ошибка", driver.getTitle());
        driver.quit();
    }

    @Test
    void addRental() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement filmsPageLink = driver.findElementByLinkText("Фильмы");
        filmsPageLink.click();
        driver.findElementById("addFilmButton").click();
        driver.findElementById("filmTitle").sendKeys("Тестовый фильм");
        driver.findElementById("filmGenre").sendKeys("жанр");
        driver.findElementById("filmCompany").sendKeys("ТестФильм");
        driver.findElementById("filmDirector").sendKeys("Майкл Бэй");
        driver.findElementById("filmReleaseYear").sendKeys("2000");
        driver.findElementById("filmPurchasePrice").sendKeys("100");
        driver.findElementById("submitButton").click();
        WebElement mainPageLink = driver.findElementByLinkText("Главная страница");
        mainPageLink.click();
        WebElement clientsPageLink = driver.findElementByLinkText("Клиенты");
        clientsPageLink.click();
        driver.findElementById("addClientButton").click();
        driver.findElementById("clientFullName").sendKeys("Тестовый клиент");
        driver.findElementById("clientEmail").sendKeys("test@example.com");
        driver.findElementById("clientPhone").sendKeys("+79111111111");
        driver.findElementById("submitButton").click();
        driver.findElementById("addRentalButton").click();

        WebElement filmDropdown = driver.findElementById("film");
        Select select = new Select(filmDropdown);
        select.selectByVisibleText("Тестовый фильм");

        driver.findElementById("purchase").click();
        driver.findElementById("startTime").sendKeys("01012023" + Keys.TAB + "1200");
        driver.findElementById("submitButton").click();

        List<WebElement> elements = driver.findElementsByClassName("entry");

        for (WebElement element : elements) {
            if (element.getText().matches(".*Тестовый клиент.*")) {
                assertTrue(element.getText().matches(".*Тестовый фильм.*"));
                break;
            }
        }

        driver.quit();
    }

    @Test
    void searchFilm() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement filmsPageLink = driver.findElementByLinkText("Фильмы");
        filmsPageLink.click();
        driver.findElementById("addFilmButton").click();
        driver.findElementById("filmTitle").sendKeys("78");
        driver.findElementById("filmGenre").sendKeys("78");
        driver.findElementById("filmCompany").sendKeys("78");
        driver.findElementById("filmDirector").sendKeys("78");
        driver.findElementById("filmReleaseYear").sendKeys("2000");
        driver.findElementById("filmPurchasePrice").sendKeys("100");
        driver.findElementById("submitButton").click();
        driver.get("http://localhost:8080/films");
        driver.findElementById("filmTitle").sendKeys("78");
        driver.findElementById("submitButton").click();

        try {
            WebElement errorElement = driver.findElementByClassName("noFilmsMsg");
            fail();
        } catch (Exception e) {
            assertTrue(true);
            driver.quit();
        }
    }

    @Test
    void searchClient() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement clientsPageLink = driver.findElementByLinkText("Клиенты");
        clientsPageLink.click();
        driver.findElementById("addClientButton").click();
        driver.findElementById("clientFullName").sendKeys("Сидоров Иван");
        driver.findElementById("clientEmail").sendKeys("test2@example.com");
        driver.findElementById("clientPhone").sendKeys("+79222222222");
        driver.findElementById("submitButton").click();
        driver.get("http://localhost:8080/clients");
        driver.findElementById("clientFullName").sendKeys("Сидоров");
        driver.findElementById("submitButton").click();

        try {
            WebElement errorElement = driver.findElementByClassName("noFilmsMsg");
            fail();
        } catch (Exception e) {
            assertTrue(true);
            driver.quit();
        }
    }

    @Test
    void searchRental() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        WebElement filmsPageLink = driver.findElementByLinkText("Фильмы");
        filmsPageLink.click();
        driver.findElementById("addFilmButton").click();
        driver.findElementById("filmTitle").sendKeys("78");
        driver.findElementById("filmGenre").sendKeys("78");
        driver.findElementById("filmCompany").sendKeys("78");
        driver.findElementById("filmDirector").sendKeys("78");
        driver.findElementById("filmReleaseYear").sendKeys("2000");
        driver.findElementById("filmPurchasePrice").sendKeys("100");
        driver.findElementById("submitButton").click();
        driver.get("http://localhost:8080/clients");
        driver.findElementById("addClientButton").click();
        driver.findElementById("clientFullName").sendKeys("Сидоров Иван");
        driver.findElementById("clientEmail").sendKeys("test2@example.com");
        driver.findElementById("clientPhone").sendKeys("+79222222222");
        driver.findElementById("submitButton").click();
        driver.findElementById("addRentalButton").click();

        WebElement filmDropdown = driver.findElementById("film");
        Select select = new Select(filmDropdown);
        select.selectByVisibleText("Тестовый фильм");

        driver.findElementById("purchase").click();
        driver.findElementById("startTime").sendKeys("01012013" + Keys.TAB + "1200");
        driver.findElementById("submitButton").click();

        driver.findElementById("rentalFrom").sendKeys("01012013" + Keys.TAB + "0000");
        driver.findElementById("rentalTo").sendKeys("01012013" + Keys.TAB + "2300");
        driver.findElementById("submitButton").click();

        try {
            WebElement errorElement = driver.findElementByClassName("noFilmsMsg");
            fail();
        } catch (Exception e) {
            assertTrue(true);
            driver.quit();
        }
    }
}
