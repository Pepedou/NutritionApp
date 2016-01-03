package com.example.pepedou.generadordeequivalencias;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by José Luis Valencia Herrera on 2/01/16.
 */
public class FoodUnit extends SugarRecord {
    public String longName, shortName;

    public FoodUnit() {
    }

    public List<Food> getFood() {
        return Food.find(Food.class, "foodUnit = ?", getId().toString());
    }
}
