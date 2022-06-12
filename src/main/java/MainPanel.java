import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class MainPanel extends JPanel {
    private JButton openWhatsappButton;
    private JLabel loginSuccessful;
    private JTextField enterPhone;
    private JTextField enterMessage;
    private JLabel enterPhoneLabel;
    private JLabel enterMessageLabel;
    private JLabel whyIsThePhoneEmptyOrInvalidLabel;
    private JLabel whyIsTheMessageEmptyLabel;
    private JLabel messageSentLabel;
    private JLabel messageReadOrDeliveredLabel;
    private JLabel responseLabel;
    private JButton createSummery;
    public String phoneNumberFormatValidationAndReformation(String givenPhoneNumber) {
        String ans = new String();
        final int CORRECT_LENGTH_VERSION_A = 12;
        final String CORRECT_START_OF_PHONE_NUMBER_VERSION_A = "9725";
        final int START = 0;
        final int END_VERSION_A = 4;
        final int CORRECT_LENGTH_VERSION_B = 10;
        final String CORRECT_START_OF_PHONE_NUMBER_VERSION_B = "05";
        final int END_VERSION_B = 2;
        final int START_OF_REFORMATTED_VERSION_B_ORIGIN = 2;
        if ((CORRECT_LENGTH_VERSION_A == givenPhoneNumber.length()) && (CORRECT_START_OF_PHONE_NUMBER_VERSION_A.equals(givenPhoneNumber.substring(START, END_VERSION_A)))) {
            ans = givenPhoneNumber;
        }
        if ((CORRECT_LENGTH_VERSION_B == givenPhoneNumber.length()) && (CORRECT_START_OF_PHONE_NUMBER_VERSION_B.equals(givenPhoneNumber.substring(START, END_VERSION_B)))) {
            ans = CORRECT_START_OF_PHONE_NUMBER_VERSION_A + givenPhoneNumber.substring(START_OF_REFORMATTED_VERSION_B_ORIGIN);
        }
        return ans;
    }

    public MainPanel(int x, int y, int width, int height) {
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\danie\\OneDrive\\Desktop\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().minimize();
        openWhatsappButton = new JButton("Send");
        openWhatsappButton.setBounds(Constants.WINDOW_WIDTH / Constants.TWO - Constants.BUTTON_WIDTH / Constants.TWO, Constants.WINDOW_HEIGHT / Constants.TWO - Constants.BUTTON_HEIGHT / Constants.TWO, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        this.add(openWhatsappButton);
        loginSuccessful = new JLabel("Logged in successfully!");
        AtomicReference additions =new AtomicReference(" ");
        enterPhone = new JTextField();
        enterPhone.setBounds(Constants.WINDOW_WIDTH / Constants.FOUR - Constants.TEXT_FIELD_WIDTH, Constants.WINDOW_HEIGHT / Constants.TWO - Constants.SPACE_BETWEEN_LINES * Constants.FOUR, Constants.TEXT_FIELD_WIDTH * Constants.TWO, Constants.TEXT_FIELD_HEIGHT);
        this.add(enterPhone);
        enterPhoneLabel = new JLabel("Enter a phone number: ");
        enterPhoneLabel.setBounds(enterPhone.getX() - Constants.ONE - Constants.FOUR * Constants.LABEL_WIDTH / Constants.THREE, enterPhone.getY(), Constants.FOUR * Constants.LABEL_WIDTH / Constants.THREE, Constants.LABEL_HEIGHT);
        this.add(enterPhoneLabel);
        whyIsThePhoneEmptyOrInvalidLabel = new JLabel("~You must enter a number...");
        whyIsThePhoneEmptyOrInvalidLabel.setBounds(enterPhone.getX() + Constants.ONE + Constants.TEXT_FIELD_WIDTH * Constants.TWO, enterPhone.getY(), Constants.FOUR * Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        enterMessage = new JTextField();
        enterMessage.setBounds(Constants.THREE * Constants.WINDOW_WIDTH / Constants.FOUR - Constants.TEXT_FIELD_WIDTH, Constants.WINDOW_HEIGHT / Constants.TWO - Constants.SPACE_BETWEEN_LINES * Constants.FOUR, Constants.TEXT_FIELD_WIDTH * Constants.TWO, Constants.TEXT_FIELD_HEIGHT);
        this.add(enterMessage);
        enterMessageLabel = new JLabel("Enter a message: ");
        enterMessageLabel.setBounds(enterMessage.getX() - Constants.ONE - Constants.LABEL_WIDTH, enterMessage.getY(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(enterMessageLabel);
        whyIsTheMessageEmptyLabel = new JLabel("~You must enter a message...");
        whyIsTheMessageEmptyLabel.setBounds(enterMessage.getX() + Constants.ONE + Constants.TEXT_FIELD_WIDTH * Constants.TWO, enterMessage.getY(), Constants.TWO * Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        loginSuccessful.setBounds(Constants.WINDOW_WIDTH / Constants.TWO - Constants.LABEL_WIDTH, openWhatsappButton.getY() - Constants.TWO * Constants.SPACE_BETWEEN_LINES, Constants.TWO * Constants.LABEL_WIDTH, Constants.BUTTON_HEIGHT);
        messageSentLabel = new JLabel("Message sent");
        messageSentLabel.setBounds(Constants.WINDOW_WIDTH / Constants.TWO - Constants.LABEL_WIDTH / Constants.TWO, openWhatsappButton.getY() + Constants.TWO * Constants.SPACE_BETWEEN_LINES, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        messageReadOrDeliveredLabel = new JLabel("Message has been delivered");
        messageReadOrDeliveredLabel.setBounds(Constants.WINDOW_WIDTH / Constants.TWO - Constants.LABEL_WIDTH, messageSentLabel.getY() + Constants.TWO * Constants.SPACE_BETWEEN_LINES, Constants.TWO * Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        responseLabel = new JLabel("No response yet");
        responseLabel.setBounds(Constants.WINDOW_WIDTH / Constants.TWO - Constants.LABEL_WIDTH, messageSentLabel.getY() + Constants.FOUR * Constants.SPACE_BETWEEN_LINES, Constants.TWO * Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        createSummery = new JButton("Create summery");
        createSummery.setBounds(Constants.WINDOW_WIDTH / Constants.TWO - Constants.BUTTON_WIDTH , responseLabel.getY() + Constants.THREE * Constants.SPACE_BETWEEN_LINES, Constants.BUTTON_WIDTH*Constants.TWO, Constants.BUTTON_HEIGHT);
        this.add(createSummery);
        AtomicBoolean validNumber= new AtomicBoolean(false);
        AtomicReference<String> printToResponseLabel = new AtomicReference<>(new String());
        openWhatsappButton.addActionListener(e -> {
            boolean toBraek = false;
            List<WebElement> messagesDoubleChecksBefore = null;
            boolean toContinue = true;
            if (enterMessage.getText().equals("") || enterMessage.getText() == null) {
                whyIsTheMessageEmptyLabel.setText("~You must enter a message...");
                this.add(whyIsTheMessageEmptyLabel);
                repaint();
                toContinue = false;
            } else {
                whyIsTheMessageEmptyLabel.setText("");
                repaint();
            }
            String phoneNumber = phoneNumberFormatValidationAndReformation(enterPhone.getText());
            if (enterPhone.getText().equals("") || enterPhone.getText() == null) {
                whyIsThePhoneEmptyOrInvalidLabel.setText("~You must enter a number...");
                this.add(whyIsThePhoneEmptyOrInvalidLabel);
                repaint();
                toContinue = false;
            } else {
                if (phoneNumber.equals("") || phoneNumber == null) {
                    whyIsThePhoneEmptyOrInvalidLabel.setText("~phone number invalid");
                    this.add(whyIsThePhoneEmptyOrInvalidLabel);
                    repaint();
                    toContinue = false;
                }else {
                    this.remove(whyIsThePhoneEmptyOrInvalidLabel);
                }
            }
            if (toContinue) {
                driver.get("https://web.whatsapp.com/send?phone=" + phoneNumber);
                driver.manage().window().maximize();

                try {
                    Thread.sleep(Constants.SLEEP_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                boolean loggedIn = false;
                while (true) {
                    if (!loggedIn) {
                        try {

                            if (driver.findElement(By.id("pane-side")).isEnabled()) {

                                this.add(loginSuccessful);
                                repaint();
                                loggedIn = true;
                            } else {
                                continue;
                            }

                        } catch (NoSuchElementException exception) {
                            continue;
                        }
                    }
                    if (loggedIn) {
                        boolean triedToSend = false;

                        try {
                            if (driver.findElement(By.xpath("/html/body/div[1]/div/span[2]/div/span/div/div/div/div/div/div[1]")).isEnabled()) {
                                validNumber.set(false);
                                whyIsThePhoneEmptyOrInvalidLabel.setText("~phone number invalid");
                                this.add(whyIsThePhoneEmptyOrInvalidLabel);
                                repaint();
                                if (additions.get().equals(" ")) {
                                    additions.set("Before the current results the user tried to message "+ enterPhone.getText() + " which is an invalid number / it's owner isn't a WhatsApp user");
                                }else {
                                    additions.set(additions.get()+" then the user tried to to message "+ enterPhone.getText() + " which is an invalid number / it's owner isn't a WhatsApp user as well");
                                }
                                break;
                            }else {
                                validNumber.set(true);
                            }
                        } catch (NoSuchElementException exception) {
                            validNumber.set(true);
                        }
                        if (validNumber.get()) {
                            try {
                                if (driver.findElement(By.cssSelector("div[ title=\"loading messages…\"]")).isEnabled()) {
                                    System.out.println("massage loading");
                                    continue;
                                }
                            } catch (NoSuchElementException exception) {

                            }
                            List<WebElement> messagesChecksBefore = null;
                            try {
                                System.out.println("message not loading anymore");


                                messagesDoubleChecksBefore = driver.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
                                messagesChecksBefore = driver.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
                                messagesChecksBefore.addAll(driver.findElements(By.cssSelector("span[data-icon='msg-check']")));
                                messagesChecksBefore.addAll(driver.findElements(By.cssSelector("span[data-icon='msg-time']")));
                                System.out.println(messagesChecksBefore.size());
                            } catch (NoSuchElementException exception) {
                            }
                            try {
                                Thread.sleep(Constants.SLEEP_TIME);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                WebElement typingBox = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[4]/div/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]"));
                                typingBox.sendKeys(enterMessage.getText());
                                driver.findElement(By.xpath("/html/body/div[1]/div/div/div[4]/div/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span")).click();
                                triedToSend = true;
                            } catch (NoSuchElementException exception) {
                            }
                            try {
                                Thread.sleep(Constants.ONE_HUNDRED * Constants.SLEEP_TIME);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            List<WebElement> messagesChecksAfter = null;
                            try {
                                messagesChecksAfter = driver.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
                                messagesChecksAfter.addAll(driver.findElements(By.cssSelector("span[data-icon='msg-check']")));
                                messagesChecksAfter.addAll(driver.findElements(By.cssSelector("span[data-icon='msg-time']")));
                                System.out.println(messagesChecksAfter.size());
                            } catch (NoSuchElementException exception) {
                            }
                            if (messagesChecksAfter.size() == Constants.ONE + messagesChecksBefore.size() && triedToSend) {
                                this.add(messageSentLabel);
                                repaint();
                                System.out.println("should paint the message sent label");
                                toBraek = true;
                            }


                            if (toBraek) {
                                break;
                            } else {
                                System.out.println("reattempting to send...");
                            }
                        }
                    }

                }
                if (validNumber.get()) {
                    AtomicReference<List<WebElement>> messagesInTimeOfReading =new AtomicReference<List<WebElement>>();
                    List<WebElement> effectivelyFinalMessagesDoubleChecksBefore = messagesDoubleChecksBefore;
                    AtomicBoolean threadClosed = new AtomicBoolean(false);
                    new Thread(() -> {
                        while (true) {
                            System.out.println("loops in thread");
                            List<WebElement> messagesDoubleChecksAfter = null;
                            boolean toBreakThatLoop = false;
                            try {
                                messagesDoubleChecksAfter = driver.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
                            } catch (NoSuchElementException exception) {

                            }
                            if (messagesDoubleChecksAfter.size() > effectivelyFinalMessagesDoubleChecksBefore.size()) {
                                toBreakThatLoop = true;
                                WebElement lastDoubleCheck = messagesDoubleChecksAfter.get(messagesDoubleChecksAfter.size() - Constants.ONE);
                                while (true) {
                                    if (lastDoubleCheck.getAccessibleName().equals("Read ") || lastDoubleCheck.getAccessibleName().equals("Read")) {
                                        messageReadOrDeliveredLabel.setText("Message has been read");
                                        this.add(messageReadOrDeliveredLabel);
                                        repaint();
                                        System.out.println("message was read");
                                        messagesInTimeOfReading.set(getMessages(driver));
                                        break;
                                    } else {
                                        messageReadOrDeliveredLabel.setText("Message has been delivered");
                                        this.add(messageReadOrDeliveredLabel);
                                        repaint();
                                    }
                                }
                                if (toBreakThatLoop) {
                                    break;
                                }
                            } else {
                                try {
                                    Thread.sleep(Constants.SLEEP_TIME);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        }
                        threadClosed.set(true);
                    }).start();
                    this.add(responseLabel);
                    int messagesInTimeOfReadingCount = 0;
                    if (messagesInTimeOfReading.get() != null) {
                        messagesInTimeOfReadingCount = messagesInTimeOfReading.get().size();
                        System.out.println("found incoming messages");
                    }
                    int effectivelyFinalMessagesInTimeOfReadingCount = messagesInTimeOfReadingCount;
                    new Thread(() -> {
                        while (true) {
                            if (threadClosed.get()) {
                                List<WebElement> currentMessages = getMessages(driver);
                                int currentMessagesCount = 0;

                                if (currentMessages != null) {
                                    currentMessagesCount = currentMessages.size();
                                }
                                if (currentMessagesCount > effectivelyFinalMessagesInTimeOfReadingCount) {
                                    WebElement response = currentMessages.get(currentMessages.size() - Constants.ONE);
                                    printToResponseLabel.set("Response: " + response.getAccessibleName());
                                    int textLength = printToResponseLabel.get().length();
                                    responseLabel.setBounds(Constants.WINDOW_WIDTH / Constants.TWO - Constants.TWO * textLength, messageSentLabel.getY() + Constants.FOUR * Constants.SPACE_BETWEEN_LINES, Constants.FOUR * textLength, Constants.LABEL_HEIGHT);
                                    responseLabel.setText(printToResponseLabel.get());
                                    repaint();
                                    driver.close();
                                    break;
                                } else {
                                    try {
                                        Thread.sleep(Constants.TEN_SECONDS_IN_MILLISECONDS);
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }).start();
                }
            }
        });
        createSummery.addActionListener(e -> {
            File summeryFile = new File("C:\\Users\\danie\\IdeaProjects\\WhatsappBot\\src\\main\\resources\\SummeryFile.txt");
            if (!summeryFile.exists()) {
                try {
                    summeryFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                FileWriter fileWriter = new FileWriter(summeryFile.getPath());
                String phoneNumberPrint = textFiledContentPrintToFileFormat(enterPhone);
                String messagePrint = textFiledContentPrintToFileFormat(enterMessage);
                fileWriter.write("Recipient: " + phoneNumberPrint + "\n" +
                        "Message: " + messagePrint + "\n" +
                        responseLabel.getText() + "\n"+
                        additions.get());
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public String textFiledContentPrintToFileFormat(JTextField textField) {
        String ans = textField.getText();
        if (textField.getText().equals("") || textField.getText() == null) {
            ans = "Not entered";
        }
        return ans;
    }

    public List<WebElement> getMessages(ChromeDriver driver) {
        List<WebElement> messages = null;
        try {
            messages.addAll(driver.findElements(By.cssSelector("div._2wUmf._21bY5.message-in.focusable-list-item")));
        } catch (NoSuchElementException exception) {

        } catch (NullPointerException exception2) {

        }
        try {
            messages.addAll(driver.findElements(By.cssSelector("div.message-in.focusable-list-item")));
        } catch (NoSuchElementException exception) {

        } catch (NullPointerException exception2) {

        }
        return messages;
    }
}
