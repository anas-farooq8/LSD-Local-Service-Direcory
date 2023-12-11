package com.example.lsd.Controllers.Admin;

import com.example.lsd.DBUtils.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.chart.AreaChart;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    private Label providersCount;

    @FXML
    private Label seekersCount;

    @FXML
    private Label todayHiring;

    @FXML
    private Label totalCustomers;

    @FXML
    private Label totalHiring;

    @FXML
    private Label totalIncome;

    // Charts
    @FXML
    private AreaChart<String, Integer> customersChart;

    @FXML
    private LineChart<String, Integer> hiringChart;

    @FXML
    private BarChart<String, Double> incomeChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int seekerCount = DBUtils.countServiceSeekers();
        int providerCount = DBUtils.countServiceProviders();

        seekersCount.setText(String.valueOf(seekerCount));
        providersCount.setText(String.valueOf(providerCount));

        totalCustomers.setText(String.valueOf(seekerCount + providerCount));
        totalHiring.setText(String.valueOf(DBUtils.countBookings()));
        todayHiring.setText(String.valueOf(DBUtils.countBookingsToday()));
        totalIncome.setText(String.valueOf(DBUtils.sumOfMoneyForAllBookings()));

        onDisplayCustomersChart();
        onDisplayNumberOfHiring();
        onDisplayIncomePerDay();
    }

    private void onDisplayIncomePerDay() {
        incomeChart.getData().clear();
        incomeChart.getXAxis().setLabel("Date");
        incomeChart.getYAxis().setLabel("Amount");

        incomeChart.getData().add(DBUtils.displayIPDChart());
    }

    private void onDisplayNumberOfHiring() {
        hiringChart.getData().clear();
        hiringChart.getXAxis().setLabel("Booking Date");
        hiringChart.getYAxis().setLabel("Count");

        hiringChart.getData().add(DBUtils.displayNOBChart());
    }

    private void onDisplayCustomersChart() {
        customersChart.getData().clear();
        customersChart.getXAxis().setLabel("Joining Year");
        customersChart.getYAxis().setLabel("Count");

        customersChart.getData().add(DBUtils.displayNOSChart());            // No. of Service Seekers
        customersChart.getData().add(DBUtils.displayNOPChart());           // No. of Service Providers
    }
}
