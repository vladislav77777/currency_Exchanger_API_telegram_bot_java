package com.example.telegram_bot_java.model;

import lombok.Data;

import java.util.Date;

@Data
public class CurrencyModel {
    String  cur_ID;
    Date date;
    String cur_Abbreviation;
    Integer cur_Scale;
    String cur_Name;
    Double cur_OfficialRate;
}
