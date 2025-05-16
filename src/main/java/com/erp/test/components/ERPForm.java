package com.erp.test.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ERP sistemlerindeki formlar için özel bileşen.
 * Form işlemlerini kolaylaştırmak için tasarlanmıştır.
 */
public class ERPForm {
    private static final Logger logger = LoggerFactory.getLogger(ERPForm.class);
    
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By formLocator;
    private WebElement formElement;
    
    /**
     * ERP form constructor'ı.
     *
     * @param driver WebDriver instance
     * @param formLocator Form locator'ı
     */
    public ERPForm(WebDriver driver, By formLocator) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.formLocator = formLocator;
        initForm();
    }
    
    /**
     * Formu başlatır.
     */
    private void initForm() {
        logger.debug("Initializing ERP form: {}", formLocator);
        formElement = driver.findElement(formLocator);
    }
    
    /**
     * Form içindeki bir metin alanına değer girer.
     *
     * @param labelText Etiket metni
     * @param value Girilecek değer
     */
    public void enterTextByLabel(String labelText, String value) {
        logger.debug("Entering text '{}' into field with label: {}", value, labelText);
        WebElement inputField = findInputFieldByLabel(labelText);
        inputField.clear();
        inputField.sendKeys(value);
    }
    
    /**
     * Form içindeki bir metin alanını etiketine göre bulur.
     *
     * @param labelText Etiket metni
     * @return Input elementi
     */
    private WebElement findInputFieldByLabel(String labelText) {
        // Try to find by label with 'for' attribute
        List<WebElement> labels = formElement.findElements(By.xpath(".//label[contains(text(),'" + labelText + "')]"));
        
        if (!labels.isEmpty()) {
            WebElement label = labels.get(0);
            String forAttribute = label.getAttribute("for");
            
            if (forAttribute != null && !forAttribute.isEmpty()) {
                return formElement.findElement(By.id(forAttribute));
            }
            
            // If no 'for' attribute, try to find input as a sibling or child of parent
            WebElement parent = label.findElement(By.xpath("./.."));
            List<WebElement> inputs = parent.findElements(By.xpath(".//input | .//textarea"));
            
            if (!inputs.isEmpty()) {
                return inputs.get(0);
            }
        }
        
        // Try to find by placeholder
        List<WebElement> inputsByPlaceholder = formElement.findElements(
                By.xpath(".//input[@placeholder='" + labelText + "'] | .//textarea[@placeholder='" + labelText + "']"));
        
        if (!inputsByPlaceholder.isEmpty()) {
            return inputsByPlaceholder.get(0);
        }
        
        // Try to find by name or id
        List<WebElement> inputsByNameOrId = formElement.findElements(
                By.xpath(".//input[@name='" + labelText + "' or @id='" + labelText + "'] | " +
                         ".//textarea[@name='" + labelText + "' or @id='" + labelText + "']"));
        
        if (!inputsByNameOrId.isEmpty()) {
            return inputsByNameOrId.get(0);
        }
        
        logger.error("Input field not found for label: {}", labelText);
        throw new IllegalArgumentException("Input field not found for label: " + labelText);
    }
    
    /**
     * Form içindeki bir dropdown'ı etiketine göre bulur.
     *
     * @param labelText Etiket metni
     * @return Select elementi
     */
    private Select findSelectByLabel(String labelText) {
        // Try to find by label with 'for' attribute
        List<WebElement> labels = formElement.findElements(By.xpath(".//label[contains(text(),'" + labelText + "')]"));
        
        if (!labels.isEmpty()) {
            WebElement label = labels.get(0);
            String forAttribute = label.getAttribute("for");
            
            if (forAttribute != null && !forAttribute.isEmpty()) {
                return new Select(formElement.findElement(By.id(forAttribute)));
            }
            
            // If no 'for' attribute, try to find select as a sibling or child of parent
            WebElement parent = label.findElement(By.xpath("./.."));
            List<WebElement> selects = parent.findElements(By.xpath(".//select"));
            
            if (!selects.isEmpty()) {
                return new Select(selects.get(0));
            }
        }
        
        // Try to find by name or id
        List<WebElement> selectsByNameOrId = formElement.findElements(
                By.xpath(".//select[@name='" + labelText + "' or @id='" + labelText + "']"));
        
        if (!selectsByNameOrId.isEmpty()) {
            return new Select(selectsByNameOrId.get(0));
        }
        
        logger.error("Select field not found for label: {}", labelText);
        throw new IllegalArgumentException("Select field not found for label: " + labelText);
    }
    
    /**
     * Form içindeki bir dropdown'dan görünür metne göre seçim yapar.
     *
     * @param labelText Etiket metni
     * @param visibleText Seçilecek görünür metin
     */
    public void selectByVisibleText(String labelText, String visibleText) {
        logger.debug("Selecting option '{}' from dropdown with label: {}", visibleText, labelText);
        Select select = findSelectByLabel(labelText);
        select.selectByVisibleText(visibleText);
    }
    
    /**
     * Form içindeki bir dropdown'dan değere göre seçim yapar.
     *
     * @param labelText Etiket metni
     * @param value Seçilecek değer
     */
    public void selectByValue(String labelText, String value) {
        logger.debug("Selecting option with value '{}' from dropdown with label: {}", value, labelText);
        Select select = findSelectByLabel(labelText);
        select.selectByValue(value);
    }
    
    /**
     * Form içindeki bir checkbox'ı işaretler veya işaretini kaldırır.
     *
     * @param labelText Etiket metni
     * @param check İşaretlenecekse true, işaret kaldırılacaksa false
     */
    public void setCheckbox(String labelText, boolean check) {
        logger.debug("Setting checkbox with label '{}' to: {}", labelText, check);
        WebElement checkbox = findCheckboxByLabel(labelText);
        boolean isChecked = checkbox.isSelected();
        
        if (check != isChecked) {
            checkbox.click();
        }
    }
    
    /**
     * Form içindeki bir checkbox'ı etiketine göre bulur.
     *
     * @param labelText Etiket metni
     * @return Checkbox elementi
     */
    private WebElement findCheckboxByLabel(String labelText) {
        // Try to find by label with 'for' attribute
        List<WebElement> labels = formElement.findElements(By.xpath(".//label[contains(text(),'" + labelText + "')]"));
        
        if (!labels.isEmpty()) {
            WebElement label = labels.get(0);
            String forAttribute = label.getAttribute("for");
            
            if (forAttribute != null && !forAttribute.isEmpty()) {
                return formElement.findElement(By.id(forAttribute));
            }
            
            // If no 'for' attribute, try to find checkbox as a sibling or child of parent
            WebElement parent = label.findElement(By.xpath("./.."));
            List<WebElement> checkboxes = parent.findElements(By.xpath(".//input[@type='checkbox']"));
            
            if (!checkboxes.isEmpty()) {
                return checkboxes.get(0);
            }
        }
        
        // Try to find by name or id
        List<WebElement> checkboxesByNameOrId = formElement.findElements(
                By.xpath(".//input[@type='checkbox' and (@name='" + labelText + "' or @id='" + labelText + "')]"));
        
        if (!checkboxesByNameOrId.isEmpty()) {
            return checkboxesByNameOrId.get(0);
        }
        
        logger.error("Checkbox not found for label: {}", labelText);
        throw new IllegalArgumentException("Checkbox not found for label: " + labelText);
    }
    
    /**
     * Form içindeki bir radio button'ı seçer.
     *
     * @param groupName Grup adı
     * @param value Seçilecek değer
     */
    public void selectRadioButton(String groupName, String value) {
        logger.debug("Selecting radio button with value '{}' from group: {}", value, groupName);
        List<WebElement> radioButtons = formElement.findElements(
                By.xpath(".//input[@type='radio' and @name='" + groupName + "' and @value='" + value + "']"));
        
        if (!radioButtons.isEmpty()) {
            radioButtons.get(0).click();
        } else {
            logger.error("Radio button not found for group: {}, value: {}", groupName, value);
            throw new IllegalArgumentException("Radio button not found for group: " + groupName + ", value: " + value);
        }
    }
    
    /**
     * Form içindeki bir düğmeye tıklar.
     *
     * @param buttonText Düğme metni
     */
    public void clickButton(String buttonText) {
        logger.debug("Clicking button with text: {}", buttonText);
        List<WebElement> buttons = formElement.findElements(
                By.xpath(".//button[contains(text(),'" + buttonText + "')] | " +
                         ".//input[@type='button' and @value='" + buttonText + "'] | " +
                         ".//input[@type='submit' and @value='" + buttonText + "']"));
        
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        } else {
            logger.error("Button not found with text: {}", buttonText);
            throw new IllegalArgumentException("Button not found with text: " + buttonText);
        }
    }
    
    /**
     * Form içindeki tüm alanların değerlerini bir harita olarak döndürür.
     *
     * @return Alan adı -> Değer haritası
     */
    public Map<String, String> getAllFieldValues() {
        Map<String, String> fieldValues = new HashMap<>();
        
        // Get all input fields
        List<WebElement> inputs = formElement.findElements(By.xpath(".//input[@type='text' or @type='number' or @type='email' or @type='password'] | .//textarea"));
        for (WebElement input : inputs) {
            String name = input.getAttribute("name");
            if (name != null && !name.isEmpty()) {
                fieldValues.put(name, input.getAttribute("value"));
            }
        }
        
        // Get all select fields
        List<WebElement> selects = formElement.findElements(By.xpath(".//select"));
        for (WebElement select : selects) {
            String name = select.getAttribute("name");
            if (name != null && !name.isEmpty()) {
                Select selectElement = new Select(select);
                WebElement selectedOption = selectElement.getFirstSelectedOption();
                fieldValues.put(name, selectedOption.getText());
            }
        }
        
        // Get all checkboxes
        List<WebElement> checkboxes = formElement.findElements(By.xpath(".//input[@type='checkbox']"));
        for (WebElement checkbox : checkboxes) {
            String name = checkbox.getAttribute("name");
            if (name != null && !name.isEmpty()) {
                fieldValues.put(name, checkbox.isSelected() ? "true" : "false");
            }
        }
        
        return fieldValues;
    }
    
    /**
     * Form gönderildiğinde belirli bir elementin görünür olmasını bekler.
     *
     * @param locator Beklenecek element locator'ı
     * @param timeoutInSeconds Zaman aşımı süresi (saniye)
     */
    public void waitForElementAfterSubmit(By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element after form submit: {}", locator);
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        longWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
