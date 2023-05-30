package com.uniovi.sdimywallapop;

import com.uniovi.sdimywallapop.pageobjects.*;
import com.uniovi.sdimywallapop.services.OffersService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SdiMywallapopApplicationTests {
    static String PathFirefox = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\migue\\Desktop\\SDI\\LABORATORIO\\sesion06\\PL-SDI-Sesión5-material\\PL-SDI-Sesio╠ün5-material\\geckodriver-v0.30.0-win64.exe";
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";
    @Autowired
    private OffersService offersService;
    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }
    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    @Test
    @Order(1)
    public void PR20() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999990A", "123456");
        // introducimos un campo vacío y buscamos
        driver.get("http://localhost:8090/offer/list?size=100&searchText=");
        // seleccionamos todas las que aparecen
        List<WebElement> rows = driver.findElements(By.className("filas-list-offers"));
        // comprobamos que el número de ofertas que aparecen son las que hay en el servicio
        Assertions.assertEquals(offersService.getOffers().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(2)
    public void PR21() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999990A", "123456");
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que no existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("cdcc");
        // seleccionamos el botón de buscar
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // seleccionamos todas las que aparecen
        List<WebElement> rows = driver.findElements(By.className("filas-list-offers"));
        // efectivamente comprobamos que no existe ninguna con ese título
        Assertions.assertEquals(offersService.getOffers().stream()
                .filter(offer -> offer.getTitle().equals("cdcc")).toList().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(3)
    public void PR22() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999990A", "123456");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 1");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"main-container\"]/div[1]/h4")).getText()));
        // comprobamos que se descuenta correctamente el marcador
        Assertions.assertEquals(100 - offersService.getOffers().stream()
                .filter(offer -> offer.isSold()).toList().get(0).getPrice(), value);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(4)
    public void PR23() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999992C", "123456");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 2");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"main-container\"]/div[1]/h4")).getText()));
        // comprobamos que se descuenta correctamente el marcador (está a cero)
        Assertions.assertEquals(100 - offersService.getOffers().stream()
                .filter(offer -> offer.isSold()).toList().get(0).getPrice(), value);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(5)
    public void PR24() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999993D", "123456");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 3");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la intentamos comprar
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"main-container\"]/div[1]/h4")).getText()));
        // comprobamos que el marcador sigue igual (a 100) porque no se pudo comprar
        Assertions.assertEquals(value, 100);
        // seleccionamos el mensaje que aparece
        String textFail = driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/div/span")).getText();
        // comprobamos que se corresponde con el mensaje de saldo no suficiente
        Assertions.assertEquals("El precio de la oferta es superior a su saldo (Saldo no suficiente)", textFail);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(6)
    public void PR25() {
        // login
        PO_PrivateView.refactorLogging(driver, "99999977E", "123456");
        // mostramos las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // buscamos por título
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 6");
        // seleccionamos buscar
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        // vamos a la vista de ofertas compradas
        driver.get("http://localhost:8090/offer/listBuy");
        // seleccionamos todas las ofertas que aparecen
        List<WebElement> rows = driver.findElements(By.className("filas-listBuy-offers"));
        // vemos que solo puede haber una
        Assertions.assertEquals(offersService.getOffers().stream()
                .filter(offer -> offer.isSold() && offer.getDniComprador().equals("99999977E")).toList().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }




}
