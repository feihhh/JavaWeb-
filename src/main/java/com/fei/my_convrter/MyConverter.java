package com.fei.my_convrter;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyConverter implements Converter{

    String format = "yyyy-MM-dd";

    @Override
    public Object convert(Class aClass, Object o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse((String)o);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
