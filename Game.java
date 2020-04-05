// Chun-Kit Chung
// Done with partner Mohammad Shaikh
// https://youtu.be/69HO1Zh9-gQ

package com.example.acer.caps;

import java.util.List;
import java.util.Map;

import ca.roumani.i2c.Country;
import ca.roumani.i2c.CountryDB;

public class Game {
    private CountryDB db;

    public Game(){
        this.db = new CountryDB();
    }

    public String qa(){
        List<String> CapList = db.getCapitals();
        int index = (int) (CapList.size() * Math.random());
        String c = CapList.get(index);
        Map<String, Country> DataList = db.getData();
        Country ref = DataList.get(c);

        if (Math.random() < 0.5) {
            String countryGivenQ = "What is the capital of " + ref.getName() + "?" + "\n" + c;
            return countryGivenQ;
        } else {
            String capitalGivenQ = c + " is the capital of " + " ?\n" + ref.getName();
            return capitalGivenQ;
        }
    }
}
