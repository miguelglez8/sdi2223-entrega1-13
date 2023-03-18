package com.uniovi.sdimywallapop;

import com.uniovi.sdimywallapop.pageobjects.*;
import com.uniovi.sdimywallapop.services.OffersService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SdiMywallapopApplicationTests {

    //Miguel
    // static String PathFirefox = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
    // static String Geckodriver = "C:\\Users\\luism\\Desktop\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

    //Ton
     //static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
     //static String Geckodriver = "C:\\Users\\tonpm\\OneDrive\\Documentos\\MisDocumentos\\Clase\\2022\\SDI\\geckodriver-v0.30.0-win64.exe";

    //Alves
    static String PathFirefox = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\Alves\\Desktop\\selenium-test\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

   // static String PathFirefox = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
   // static String Geckodriver = "C:\\Users\\Aladino España\\Desktop\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

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

    /**
     * PR01. Registro de Usuario con datos válidos
     */
    @Test
    public void PR01() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test_", "test_", "test@email.com",
                "123456", "123456");
    }

    /**
     * PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
     * apellidos vacíos)
     */

    @Test
    public void PR02() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "awffw", "test_", "a",
                "123456", "123456");

        // Comprobamos el error de email vacío.
        PO_SignUpView.checkElementByKey(driver,"Error.signup.name.length", PO_Properties.getSPANISH());

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, " ", "fawf", "test_",
                "123456", "123456");
        // Comprobamos el error de nombre vacío.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
                PO_Properties.getSPANISH());

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test@email.com", " ", "test@email.com",
                "123456", "123456");
        // Comprobamos el error de apellidos vacío.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length",
                PO_Properties.getSPANISH());

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test_", "test_", "test@email.com",
                " ", " ");
        // Comprobamos el error de contraseña vacía.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length",
                PO_Properties.getSPANISH());
    }

    /**
     * PR03. Registro de Usuario con datos inválidos (repetición de contraseña
     * inválida).
     */

    @Test
    public void PR03() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test", "test", "test@email.com",
                "123456", "123457");
        // Comprobamos que el error existe
        PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
    }

    /**
     * PR04. Registro de Usuario con datos inválidos (email existente).
     */

    @Test
    public void PR04() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test", "test", "admin@email.com",
                "123456", "123456");
        // Comprobamos el error de email repetido.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.email.duplicate",
                PO_Properties.getSPANISH());
    }

    /**
     * PR05. Inicio de sesión con datos válidos (administrador).
     */

    @Test
    public void PR05() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Lista de los usuarios de la aplicacion";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * PR06. Inicio de sesión con datos válidos (usuario estándar).
     */

    @Test
    public void PR06() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user15@email.com", "user01");
        // Comprobamos que es el admin
        String checkText = "Ofertas";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * PR07. Inicio de sesión con datos inválidos (usuario estándar, campo email
     * y contraseña vacíos).
     */

    @Test
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, " ", " ");
        // Comprobamos que es el admin
        String checkText = "Identificate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * PR08. Inicio de sesión con datos válidos (usuario estándar, email
     * existente, pero contraseña incorrecta).
     */

    @Test
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user15@email.com", "a");
        // Comprobamos que es el admin
        String checkText = "Identificate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * PR09. Inicio de sesión con datos inválidos (usuario estándar, email no
     * existente en la aplicación).
     */

    @Test
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "userwwww5@email.com", "a");
        // Comprobamos que es el admin
        String checkText = "Identificate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * PR10. Hacer click en la opción de salir de sesión y comprobar que se
     * redirige a la página de inicio de sesión (Login).
     */

    @Test
    public void PR10() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user15@email.com", "user01");
        // Comprobamos que es el admin
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        String checkText = "Identificate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * PR11. Comprobar que el botón cerrar sesión no está visible si el usuario
     * no está autenticado.
     */

    @Test
    public void PR11() {
    //Vamos al formulario de logueo.
        Assertions.assertThrows(TimeoutException.class, () -> PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary"));
    }

    /**
     * PR12. Mostrar el listado de usuarios y comprobar que se muestran todos
     * los que existen en el sistema.
     */

    @Test
    public void PR12() {
        // Rellenamos el formulario
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");


        List<WebElement> elementos = PO_UserList.checkElementBy(driver, "class",
                "checkBox");

        assertTrue(elementos.size() == 15);
        PO_UserList.checkElementBy(driver, "text", "user01@email.com");
        PO_UserList.checkElementBy(driver, "text", "user02@email.com");
        PO_UserList.checkElementBy(driver, "text", "user03@email.com");
        PO_UserList.checkElementBy(driver, "text", "user04@email.com");
    }

    /**
     * PR13. Ir a la lista de usuarios, borrar el primer usuario de la lista,
     * comprobar que la lista se actualiza y dicho usuario desaparece.
     */
    @Test
    public void PR13() {
/*        // Rellenamos el formulario
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

        PO_UserList.deleteUser(driver, 0);

        // Esperar a que aparezca el cuadro de diálogo de confirmación
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Despedir el cuadro de diálogo haciendo clic en el botón "Aceptar"
        alert.accept();
        List<WebElement> elementos = PO_UserList.checkElementBy(driver, "class",
                "checkBox");
        PO_HomeView.clickOption(driver, "updateButton", "class", "btn btn-primary");
        System.out.println(elementos.size());
        assertTrue(elementos.size() == 15);
        PO_UserList.checkElementBy(driver, "text", "user02@email.com");
        PO_UserList.checkElementBy(driver, "text", "user03@email.com");
        PO_UserList.checkElementBy(driver, "text", "user04@email.com");*/
    }

    /**
     * PR14. Ir a la lista de usuarios, borrar el último usuario de la lista,
     * comprobar que la lista se actualiza y dicho usuario desaparece.
     */
    @Test
    public void PR14() {
/*        // Rellenamos el formulario
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

        PO_UserList.deleteUser(driver, 0);

        // Esperar a que aparezca el cuadro de diálogo de confirmación
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Despedir el cuadro de diálogo haciendo clic en el botón "Aceptar"
        alert.accept();
        List<WebElement> elementos = PO_UserList.checkElementBy(driver, "class",
                "checkBox");
        PO_HomeView.clickOption(driver, "updateButton", "class", "btn btn-primary");
        System.out.println(elementos.size());
        assertTrue(elementos.size() == 15);
        PO_UserList.checkElementBy(driver, "text", "user02@email.com");
        PO_UserList.checkElementBy(driver, "text", "user03@email.com");
        PO_UserList.checkElementBy(driver, "text", "user04@email.com");*/
    }

    @Test
    @Order(15)
    public void PR15() {
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        driver.get("http://localhost:8090/offer/add");
        PO_PrivateView.fillFormAddOffer(driver,"Mesa","Mesa de caoba","Muy grande","24");
        driver.get("http://localhost:8090/offer/myList?page=2");
        String checkText = "Mesa";
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
    }

    @Test
    @Order(16)
    public void PR16() {
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        driver.get("http://localhost:8090/offer/add");
        PO_PrivateView.fillFormAddOffer(driver,"Mesa","Mesa de caoba","Muy grande","-24");
        String checkText = "El precio no puede ser negativo.";
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
    }

    @Test
    @Order(17)
    public void PR17() {
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        driver.get("http://localhost:8090/offer/myList");
        List<WebElement> elements = driver.findElements(By.className("filas-list-offers"));
        Assertions.assertEquals(5, elements.size());
    }

    @Test
    @Order(18)
    public void PR18() {
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        driver.get("http://localhost:8090/offer/myList?size=200");
        PO_PrivateView.clickElement(driver, "//td[contains(text(), '€')]/following-sibling::*/a[contains(@href, 'offer/delete')]", 0);
        driver.get("http://localhost:8090/offer/myList?size=200");
        List<WebElement> elements = driver.findElements(By.className("filas-list-offers"));
        Assertions.assertEquals(10, elements.size());
    }

    @Test
    @Order(19)
    public void PR19() {
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        driver.get("http://localhost:8090/offer/myList?size=200");
        PO_PrivateView.clickElement(driver, "//td[contains(text(), '€')]/following-sibling::*/a[contains(@href, 'offer/delete')]", 9);
        driver.get("http://localhost:8090/offer/myList?size=200");
        List<WebElement> elements = driver.findElements(By.className("filas-list-offers"));
        Assertions.assertEquals(9, elements.size());
    }

    /**
     * PR20. Ir a la lista de ofertas, y buscar con el buscador vacío
     * comprobar que aparecen todas las ofertas
     */
    @Test
    @Order(20)
    public void PR20() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        // introducimos un campo vacío y buscamos
        driver.get("http://localhost:8090/offer/list?size=200&searchText=");
        // seleccionamos todas las que aparecen
        List<WebElement> rows = driver.findElements(By.className("filas-list-offers"));
        // comprobamos que el número de ofertas que aparecen son las que hay en el servicio
        Assertions.assertEquals(offersService.getOffers().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * PR21. Ir a la lista de ofertas, y buscar con el buscador el correo que queramos
     * comprobar que aparece la oferta con ese correo
     */
    @Test
    @Order(21)
    public void PR21() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        driver.get("http://localhost:8090/offer/list?size=200");
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

    /**
     * PR22. Ir a la lista de ofertas, y comprar una oferta cuyo precio sea menor a nuestro saldo (100)
     * comprobar que no hay ningún error, que se actualiza la vista y se descuenta el saldo bien
     */
    @Test
    @Order(22)
    public void PR22() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 11");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr[1]/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"myNavbar\"]/ul[2]/li[1]/h4")).getText()));
        // comprobamos que se descuenta correctamente el marcador
        Assertions.assertEquals(100 - offersService.getOffers().stream()
                .filter(offer -> offer.isSold()).toList().get(0).getPrice(), value);
        // logoutF
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * PR23. Ir a la lista de ofertas, y comprar una oferta cuyo precio sea igual a nuestro saldo (100)
     * comprobar que no hay ningún error, que se actualiza la vista y se descuenta el saldo bien
     */
    @Test
    @Order(23)
    public void PR23() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "user08@email.com", "user01");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 2");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr[1]/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"myNavbar\"]/ul[2]/li[1]/h4")).getText()));
        // comprobamos que se descuenta correctamente el marcador (está a cero)
        Assertions.assertEquals(100 - offersService.getOffers().stream()
                .filter(offer -> offer.isSold()).toList().get(0).getPrice(), value);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * PR24. Ir a la lista de ofertas, y comprar una oferta cuyo precio sea mayor a nuestro saldo (100)
     * comprobar que no hay un error, que se actualiza la vista y no se descuenta el saldo
     */
    @Test
    @Order(24)
    public void PR24() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "user03@email.com", "user01");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 43");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la intentamos comprar
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"myNavbar\"]/ul[2]/li[1]/h4")).getText()));
        // comprobamos que el marcador sigue igual (a 100) porque no se pudo comprar
        Assertions.assertEquals(value, 100);
        // seleccionamos el mensaje que aparece
        String textFail = driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[1]/div/span")).getText();
        // comprobamos que se corresponde con el mensaje de saldo no suficiente
        Assertions.assertEquals("El precio de la oferta es superior a su saldo (Saldo no suficiente)", textFail);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * PR25. Ir a la lista de ofertas compradas
     * comprobar que están las ofertas que hemos comprado
     */
    @Test
    @Order(25)
    public void PR25() {
        // login
        PO_PrivateView.refactorLogging(driver, "user04@email.com", "user01");
        // mostramos las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
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
                .filter(offer -> offer.isSold() && offer.getEmailComprador().equals("user04@email.com")).toList().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * PR26. Envío de un mensaje a una oferta. Se crea la conversación.
     * Comprobar que el mensaje aparece en la conversación.
     */
    @Test
    @Order(26)
    public void PR26() {
        // Login
        PO_PrivateView.refactorLogging(driver, "user04@email.com", "user01");
        // Mostramos las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // Enviamos un mensaje a la oferta 6
        PO_ConversationView.sendMessage(driver, "Oferta 6", "Hola, esto es un mensaje de prueba");
        // Comprobamos que aparece el mensaje
        String checkText = "Hola, esto es un mensaje de prueba";
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
    }

    /**
     * PR27. Acceder de nuevo a una conversación ya creada.
     * Comprobar que el mensaje nuevo aparece en la conversación.
     */
    @Test
    @Order(27)
    public void PR27() {
        // Login
        PO_PrivateView.refactorLogging(driver, "user04@email.com", "user01");
        // Mostramos las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // Enviamos un mensaje a la oferta 6
        PO_ConversationView.sendMessage(driver, "Oferta 6", "Hola, esto es un mensaje de prueba");
        // Volvemos a la lista de ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // Enviamos un nuevo mensaje a la misma oferta
        PO_ConversationView.sendMessage(driver, "Oferta 6", "Y esto es un segundo mensaje");
        // Comprobamos que aparece el mensaje
        String checkText = "Y esto es un segundo mensaje";
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
    }

    /**
     * PR28. Mostrar el listado de conversaciones ya abiertas.
     * Comprobar que el listado contiene la cantidad correcta de conversaciones.
     */
    @Test
    @Order(28)
    public void PR28() {
        // Login
        PO_PrivateView.refactorLogging(driver, "user04@email.com", "user01");
        // Mostramos las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // Enviamos un mensaje a la oferta 6
        PO_ConversationView.sendMessage(driver, "Oferta 6", "Hola, esto es un mensaje de prueba");
        // Mostramos las ofertas
        driver.get("http://localhost:8090/offer/list?size=200");
        // Enviamos un mensaje a la oferta 21
        PO_ConversationView.sendMessage(driver, "Oferta 21", "Hola, esto es otro mensaje de prueba");
        // Vamos al listado de conversaciones
        driver.get("http://localhost:8090/conversation/list");
        // Comprobamos que existe una conversación con el usuario que creó la oferta
        String checkText = "user01@email.com";
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
        // Todas las conversaciones que aparecen
        List<WebElement> rows = driver.findElements(By.className("conversation-list-rows"));
        // Vemos que la cantidad total de elementos que aparecen son 2
        Assertions.assertEquals(2, rows.size());
        // Logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * PR29. Ir a las siguientes vistas: pantalla principal, lista de usuarios, lista de todas las ofertas y
     * lista de las ofertas compradas
     * comprobar que están los elementos de las vistas internacionalizados
     */
    @Test
    @Order(29)
    public void PR29() {
        // página principal
        // texto en español
        String bienvenida = driver.findElement(By.xpath("/html/body/div/h2")).getText();
        // comprobamos que está en español
        Assertions.assertEquals(PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH())
                , bienvenida);
        // cambiar de idioma a inglés
        PO_PrivateView.changeLanguage(driver, "English");
        // texto en inglés
        String welcome = driver.findElement(By.xpath("/html/body/div/h2")).getText();
        // comprobamos que está en inglés
        Assertions.assertEquals(PO_HomeView.getP().getString("welcome.message", PO_Properties.getENGLISH())
                , welcome);
        // cambiar de idioma a español
        PO_PrivateView.changeLanguage(driver, "Spanish");
        // login
        PO_PrivateView.refactorLogging(driver, "admin@email.com", "admin");
        // listado de usuarios
        // texto en español
        String listadoUsuarios = driver.findElement(By.xpath("/html/body/form/h2")).getText();
        // comprobamos que está en español
        Assertions.assertEquals(PO_HomeView.getP().getString("user.list.title", PO_Properties.getSPANISH())
                , listadoUsuarios);
        // cambiar de idioma a inglés
        PO_PrivateView.changeLanguage(driver, "English");
        // texto en inglés
        String listUsers = driver.findElement(By.xpath("/html/body/form/h2")).getText();
        // comprobamos que está en inglés
        Assertions.assertEquals(PO_HomeView.getP().getString("user.list.title", PO_Properties.getENGLISH())
                , listUsers);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
        // login
        PO_PrivateView.refactorLogging(driver, "user01@email.com", "user01");
        // cambiar de idioma a español
        PO_PrivateView.changeLanguage(driver, "Spanish");
        // vamos a la pantalla de todas las ofertas
        driver.get("http://localhost:8090/offer/list");
        // texto en español
        String buscar = driver.findElement(By.xpath("//*[@id=\"main-container\"]/p[1]")).getText();
        // comprobamos que está en español
        Assertions.assertEquals(PO_HomeView.getP().getString("offer.list.search.title", PO_Properties.getSPANISH())
                , buscar);
        // cambiar de idioma a inglés
        PO_PrivateView.changeLanguage(driver, "English");
        // texto en inglés
        String search = driver.findElement(By.xpath("//*[@id=\"main-container\"]/p[1]")).getText();
        // comprobamos que está en inglés
        Assertions.assertEquals(PO_HomeView.getP().getString("offer.list.search.title", PO_Properties.getENGLISH())
                , search);
        // vamos a la pantalla de todas las ofertas compradas
        driver.get("http://localhost:8090/offer/listBuy");
        // cambiar de idioma a español
        PO_PrivateView.changeLanguage(driver, "Spanish");
        // texto en español
        String parrafoOfertas = driver.findElement(By.xpath("//*[@id=\"main-container\"]/p")).getText();
        // comprobamos que está en español
        Assertions.assertEquals(PO_HomeView.getP().getString("offer.list.msg", PO_Properties.getSPANISH())
                , parrafoOfertas);
        // cambiar de idioma a inglés
        PO_PrivateView.changeLanguage(driver, "English");
        // texto en inglés
        String paragraphOffers = driver.findElement(By.xpath("//*[@id=\"main-container\"]/p")).getText();
        // comprobamos que está en inglés
        Assertions.assertEquals(PO_HomeView.getP().getString("offer.list.msg", PO_Properties.getENGLISH())
                , paragraphOffers);
        // cambiar de idioma a español
        PO_PrivateView.changeLanguage(driver, "Spanish");
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * [Prueba30] Intentar acceder sin estar autenticado a la opción de listado de usuarios. Se deberá volver al
     * formulario de login.
     */
    @Test
    @Order(30)
    public void PR30() {
        //Intento acceder a la lista de usuarios
        driver.get("http://localhost:8090/user/list");
        //Compruebo que no me lo ha permitido y me envía al login
        Assertions.assertEquals("http://localhost:8090/login", driver.getCurrentUrl());
    }

    /**
     * [Prueba31] Intentar acceder sin estar autenticado a la opción de listado de conversaciones
     * de un usuario estándar. Se deberá volver al formulario de login.
     */
    @Test
    @Order(31)
    public void PR31() {
        //Intento acceder a la lista de usuarios
        driver.get("http://localhost:8090/conversation/list");
        //Compruebo que no me lo ha permitido y me envía al login
        Assertions.assertEquals("http://localhost:8090/login", driver.getCurrentUrl());
    }

    /**
     * [Prueba32] Estando autenticado como usuario estándar intentar acceder a una opción disponible solo
     * para usuarios administradores (Añadir menú de auditoria (visualizar logs)). Se deberá indicar un mensaje
     * de acción prohibida.
     */
    @Test
    @Order(32)
    public void PR32() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_LoginView.fillLoginForm(driver, "user15@email.com", "user01");
        //Intentamos acceder al log, opción disponible solo para administradores.
        driver.get("http://localhost:8090/log/list");
        //Comprobamos que está accediendo a la vista con el mensaje que indica que no está autorizado
        Assertions.assertEquals("http://localhost:8090/unauthorized", driver.getCurrentUrl());
    }

    /**
     * [Prueba33] Estando autenticado como usuario administrador visualizar todos los logs generados en una
     * serie de interacciones. Esta prueba deberá generar al menos dos interacciones de cada tipo y comprobar
     * que el listado incluye los logs correspondientes.
     */
    @Test
    @Order(33)
    public void PR33() {
        //Iniciamos sesión como administrador para reiniciar la lista de logs, por lo que este login no contará
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        driver.get("http://localhost:8090/log/list");
        //Reiniciamos la lista
        driver.findElements(By.tagName("button")).get(driver.findElements(By.tagName("button")).size()-1).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test_1", "test_1", "test1@email.com",
                "123456", "123456");
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
        // Vamos al formulario de registro por segunda vez
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test_2", "test_2", "test2@email.com",
                "123456", "123456");
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
        //Intentamos iniciar sesión erroneamente
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Credenciales con nombre erroneo
        PO_LoginView.fillLoginForm(driver, "adsasd", "admin");
        //Credenciales con contraseña erronea
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "asd");
        //Iniciamos sesión como administrador veces
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        driver.get("http://localhost:8090/log/list");
        WebElement element = driver.findElement(By.tagName("body"));
        String pageContent = element.getText();
        String loginEx = "LOGIN-EX";
        String loginErr= "LOGIN-ERR";
        String alta = "ALTA";
        String pet = "PET";
        String logout = "LOGOUT";
        int occurrences = pageContent.split(loginEx, -1).length - 1;
        // 4 login exitosos debido a que al registrarse también se tiene en cuenta como un login
        Assertions.assertEquals(occurrences, 3);
        // 2 login erroneos provocados
        occurrences = pageContent.split(loginErr, -1).length - 1;
        Assertions.assertEquals(occurrences, 2);
        // 3 logout, dos por las nuevas cuentas creadas y otro por el login.
        occurrences = pageContent.split(logout, -1).length - 1;
        Assertions.assertEquals(occurrences, 3);
        // 2 altas
        occurrences = pageContent.split(alta, -1).length - 1;
        Assertions.assertEquals(occurrences, 2);
        // Más de dos peticiones, cada acción proboca peticiones.
        occurrences = pageContent.split(pet, -1).length - 1;
        Assertions.assertTrue(occurrences > 2);
    }

    /**
     * [Prueba34] Estando autenticado como usuario administrador, ir a visualización de logs, pulsar el
     * botón/enlace borrar logs y comprobar que se eliminan los logs de la base de datos.
     */
    @Test
    @Order(34)
    public void PR34() {
        //Iniciamos sesión como administrador
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        driver.get("http://localhost:8090/log/list");
        driver.findElements(By.tagName("button")).get(driver.findElements(By.tagName("button")).size()-1).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        driver.get("http://localhost:8090/log/list");
        WebElement element = driver.findElement(By.tagName("body"));
        String pageContent = element.getText();
        //Solo aparecerá una petición PET, la que se produce al recargar la lista después de eliminarla
        String loginEx = "LOGIN-EX";
        String loginErr= "LOGIN-ERR";
        String alta = "ALTA";
        String pet = "PET";
        String logout = "LOGOUT";
        int occurrences = pageContent.split(loginEx, -1).length - 1;
        // 4 login exitosos debido a que al registrarse también se tiene en cuenta como un login
        Assertions.assertEquals(occurrences, 0);
        // 2 login erroneos provocados
        occurrences = pageContent.split(loginErr, -1).length - 1;
        Assertions.assertEquals(occurrences, 0);
        // 3 logout, dos por las nuevas cuentas creadas y otro por el login.
        occurrences = pageContent.split(logout, -1).length - 1;
        Assertions.assertEquals(occurrences, 0);
        // 2 altas
        occurrences = pageContent.split(alta, -1).length - 1;
        Assertions.assertEquals(occurrences, 0);
        // Más de dos peticiones, cada acción proboca peticiones.
        occurrences = pageContent.split(pet, -1).length - 1;
        Assertions.assertTrue(occurrences >= 1);
    }

    /**
     * PR35. Sobre el listado de conversaciones ya abiertas, pinchar el enlace Eliminar de la primera.
     * Comprobar que el listado se actualiza correctamente.
     */
    @Test
    @Order(35)
    public void PR35() {
        // Login
        PO_PrivateView.refactorLogging(driver, "user04@email.com", "user01");
        // Enviamos un mensaje a la oferta 6
        driver.get("http://localhost:8090/offer/list?size=200");
        PO_ConversationView.sendMessage(driver, "Oferta 6", "Hola, esto es un mensaje a la oferta 6");
        // Enviamos un mensaje a la oferta 23
        driver.get("http://localhost:8090/offer/list?size=200");
        PO_ConversationView.sendMessage(driver, "Oferta 23", "Hola, esto es un mensaje a la oferta 23");
        // Enviamos un mensaje a la oferta 48
        driver.get("http://localhost:8090/offer/list?size=200");
        PO_ConversationView.sendMessage(driver, "Oferta 48", "Hola, esto es un mensaje a la oferta 48");
        // Vamos al listado de conversaciones
        driver.get("http://localhost:8090/conversation/list");
        // Eliminamos el primer elemento de la tabla
        driver.findElement(By.xpath("//*[@id=\"tableConversations\"]/tbody/tr[1]/td[4]/a")).click();
        // Comprobamos que hay solo dos filas
        List<WebElement> rows = driver.findElements(By.className("conversation-list-rows"));
        Assertions.assertEquals(2, rows.size());
        // Logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    /**
     * PR35. Sobre el listado de conversaciones ya abiertas, pinchar el enlace Eliminar de la última.
     * Comprobar que el listado se actualiza correctamente.
     */
    @Test
    @Order(36)
    public void PR36() {
        // Login
        PO_PrivateView.refactorLogging(driver, "user04@email.com", "user01");
        // Enviamos un mensaje a la oferta 6
        driver.get("http://localhost:8090/offer/list?size=200");
        PO_ConversationView.sendMessage(driver, "Oferta 6", "Hola, esto es un mensaje a la oferta 6");
        // Enviamos un mensaje a la oferta 23
        driver.get("http://localhost:8090/offer/list?size=200");
        PO_ConversationView.sendMessage(driver, "Oferta 23", "Hola, esto es un mensaje a la oferta 23");
        // Enviamos un mensaje a la oferta 48
        driver.get("http://localhost:8090/offer/list?size=200");
        PO_ConversationView.sendMessage(driver, "Oferta 48", "Hola, esto es un mensaje a la oferta 48");
        // Enviamos un mensaje a la oferta 67
        driver.get("http://localhost:8090/offer/list?size=200");
        PO_ConversationView.sendMessage(driver, "Oferta 67", "Hola, esto es un mensaje a la oferta 67");
        // Vamos al listado de conversaciones
        driver.get("http://localhost:8090/conversation/list");
        // Eliminamos el último elemento de la tabla
        driver.findElement(By.xpath("//*[@id=\"tableConversations\"]/tbody/tr[4]/td[4]/a")).click();
        // Comprobamos que hay solo dos filas
        List<WebElement> rows = driver.findElements(By.className("conversation-list-rows"));
        Assertions.assertEquals(3, rows.size());
        // Logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

}
