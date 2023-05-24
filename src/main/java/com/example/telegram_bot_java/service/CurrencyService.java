package com.example.telegram_bot_java.service;

import com.example.telegram_bot_java.model.CurrencyModel;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CurrencyService {

    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException, ParseException {
        URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }
        JSONObject object = new JSONObject(result);

//        JSONObject rubleObject = object.getJSONObject("Valute").getJSONObject("RUB");
        JSONObject targetCurrencyObject = object.getJSONObject("Valute").getJSONObject(message.toUpperCase());

        model.setCur_ID(targetCurrencyObject.getString("ID"));
        model.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(object.getString("Date")));
        model.setCur_Abbreviation(targetCurrencyObject.getString("CharCode"));
        model.setCur_Scale(targetCurrencyObject.getInt("Nominal"));
        model.setCur_Name(targetCurrencyObject.getString("Name"));
        model.setCur_OfficialRate(targetCurrencyObject.getDouble("Value"));

        return "Официальный курс " + model.getCur_Abbreviation() + " к российскому рублю\n" +
                "на дату: " + getFormatDate(model) + "\n" +
                "составляет: " + model.getCur_OfficialRate() + " рублей за " + model.getCur_Scale() + " " + model.getCur_Abbreviation();
    }

    private static String getFormatDate(CurrencyModel model) {
        return new SimpleDateFormat("dd MMM yyyy").format(model.getDate());
    }
}
