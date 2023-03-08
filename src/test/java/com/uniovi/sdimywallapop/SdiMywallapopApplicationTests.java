package com.uniovi.sdimywallapop;

import com.uniovi.sdimywallapop.pageobjects.*;
import com.uniovi.sdimywallapop.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SdiMywallapopApplicationTests {
    static String PathFirefox = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\migue\\Desktop\\SDI\\LABORATORIO\\sesion06\\PL-SDI-Sesión5-material\\PL-SDI-Sesio╠ün5-material\\geckodriver-v0.30.0-win64.exe";
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";
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
        PO_PrivateView.refactorLogging(driver, "99999990A", "123456");
        //Pinchamos en la opción de menú
        WebElement menu = driver.findElement(By.id("offerDropdown"));
        menu.click();
        WebElement menuItem = menu.findElement(By.xpath("//a[contains(text(),'Menu Item')]"));
        menuItem.click();
    }

    @Test
    @Order(2)
    public void PR21() {

    }

    @Test
    @Order(3)
    public void PR22() {

    }

    @Test
    @Order(4)
    public void PR23() {

    }

    @Test
    @Order(5)
    public void PR24() {

    }

    @Test
    @Order(6)
    public void PR25() {

    }




}
