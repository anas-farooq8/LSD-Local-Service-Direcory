package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Account;
import com.example.lsd.Models.ServiceSeeker;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.format.DateTimeFormatter;

public class AccountController implements Initializable {

    private String username;
    private ServiceSeeker serviceSeeker;

    private Account account;

    @FXML
    private TextField accNo;

    @FXML
    private TextField balance;

    @FXML
    private Label cardAccNo;

    @FXML
    private Label cardExpiryDate;

    @FXML
    private TextField cardNo;

    @FXML
    private TextField cnic;

    @FXML
    private TextField cvv;

    @FXML
    private TextField expiryDate;

    @FXML
    private Label name;

    @FXML
    private TextField pin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setUsername(String username) {
        this.username = username;
        serviceSeeker = DBUtils.getServiceSeekerInfo(username);
        setAccountViewData();
    }

    private void setAccountViewData() {
        account = DBUtils.getAccountByCnic(serviceSeeker.getCnic());
        setCreditCardData();
        setFieldsData();
    }

    private void setFieldsData() {
        accNo.setText(account.getAccountNumber());
        cnic.setText(serviceSeeker.getCnic());
        cardNo.setText(account.getCreditCardNumber());
        expiryDate.setText(account.getCreditCardExpiry().toString());
        cvv.setText(String.valueOf(account.getCvv()));
        balance.setText("$ " + String.valueOf(account.getBalance()));
        pin.setText(String.valueOf(account.getPin()));
    }

    private void setCreditCardData() {
        name.setText(serviceSeeker.getName());
        cardAccNo.setText(formatAccountNumber(account.getCreditCardNumber()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd");
        String formattedExpiryDate = account.getCreditCardExpiry().format(formatter);
        cardExpiryDate.setText(formattedExpiryDate);
    }

    private String formatAccountNumber(String creditCardNumber) {
        StringBuilder formattedNumber = new StringBuilder();
        for (int i = 0; i < creditCardNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedNumber.append("    "); // 4 spaces
            }
            formattedNumber.append(creditCardNumber.charAt(i));
        }
        return formattedNumber.toString();
    }
}
