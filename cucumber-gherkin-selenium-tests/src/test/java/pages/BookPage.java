package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class BookPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public BookPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddBook() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Ajouter un livre"))).click();
    }

    public void fillForm(String titre, String auteur, String annee) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("titre")));
        driver.findElement(By.name("titre")).clear();
        driver.findElement(By.name("titre")).sendKeys(titre);
        driver.findElement(By.name("auteur")).clear();
        driver.findElement(By.name("auteur")).sendKeys(auteur);
        driver.findElement(By.name("annee")).clear();
        driver.findElement(By.name("annee")).sendKeys(annee);
    }

    public void submitForm() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public boolean isBookInList(String titre) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
            List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
            for (WebElement row : rows) {
                if (row.getText().contains(titre)) {
                    return true;
                }
            }
        } catch (TimeoutException e) {
            // maybe table is not present because empty
        }
        return false;
    }

    public void clickEditBook(String titre) {
        WebElement row = driver.findElement(By.xpath("//tr[contains(., '" + titre + "')]"));
        row.findElement(By.xpath(".//button[contains(text(), 'Modifier')]")).click();
    }

    public void clickDeleteBook(String titre) {
        WebElement row = driver.findElement(By.xpath("//tr[contains(., '" + titre + "')]"));
        row.findElement(By.xpath(".//button[contains(text(), 'Supprimer')]")).click();
        
        // Gérer l'alerte de confirmation
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (TimeoutException e) {
            // Pas d'alerte présente
        }
    }
}
