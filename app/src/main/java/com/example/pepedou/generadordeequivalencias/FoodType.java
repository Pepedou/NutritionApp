package com.example.pepedou.generadordeequivalencias;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by José Luis Valencia Herrera on 2/01/16.
 */
public class FoodType extends SugarRecord {
    public String name;

    public FoodType() {
    }

    public List<Food> getFood() {
        return Food.find(Food.class, "foodType = ?", getId().toString());
    }
}
