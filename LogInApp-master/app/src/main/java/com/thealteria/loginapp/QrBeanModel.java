package com.thealteria.loginapp;

public class QrBeanModel {
    private String QrText;
    private String Date;
    public String SpecDate;

    public QrBeanModel(String qrText, String date, String specDate) {
        QrText = qrText;
        Date = date;
        SpecDate = specDate;
    }

    public String getSpecDate() {
        return SpecDate;
    }

    public void setSpecDate(String specDate) {
        SpecDate = specDate;
    }

    public String getQrText() {
        return QrText;
    }

    public void setQrText(String qrText) {
        QrText = qrText;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}


