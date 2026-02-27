package steps;

import config.DriverFactory;
import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.BookPage;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private BookPage bookPage;

    @Before
    public void setup() {
        driver = DriverFactory.createDriver();
        loginPage = new LoginPage(driver);
        bookPage = new BookPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("je suis sur la page de login")
    public void open_login_page() {
        loginPage.open();
    }

    @Given("je suis connecté avec {string} et {string}")
    public void login_and_be_on_home(String user, String password) {
        loginPage.open();
        loginPage.login(user, password);
        assertTrue(loginPage.isLoginSuccessful());
    }

    @When("je me connecte avec {string} et {string}")
    public void login(String user, String password) {
        loginPage.login(user, password);
    }

    @Then("un message d'erreur est affiché")
    public void error_message_displayed() {
        assertTrue(loginPage.isErrorDisplayed());
    }

    @Then("je suis redirigé vers le dashboard")
    public void redirected_to_dashboard() {
        assertTrue(loginPage.isLoginSuccessful());
    }

    // --- Book Steps ---

    @Given("je clique sur {string}")
    public void click_add_book(String label) {
        if (label.equals("Ajouter un livre")) {
            bookPage.clickAddBook();
        }
    }

    @When("je remplis le formulaire avec {string}, {string}, {string}")
    public void fill_form(String titre, String auteur, String annee) {
        bookPage.fillForm(titre, auteur, annee);
    }

    @When("je valide le formulaire")
    public void submit_form() {
        bookPage.submitForm();
    }

    @Then("le livre {string} est présent dans la liste")
    public void book_present(String titre) {
        assertTrue(bookPage.isBookInList(titre));
    }

    @When("je clique sur {string} pour le livre {string}")
    public void click_action_on_book(String action, String titre) {
        if (action.equals("Modifier")) {
            bookPage.clickEditBook(titre);
        } else if (action.equals("Supprimer")) {
            bookPage.clickDeleteBook(titre);
        }
    }

    @Then("le livre {string} n'est plus présent dans la liste")
    public void book_not_present(String titre) {
        assertFalse(bookPage.isBookInList(titre));
    }
}
