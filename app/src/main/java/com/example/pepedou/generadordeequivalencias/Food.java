package com.example.pepedou.generadordeequivalencias;

import com.orm.SugarRecord;

/**
 * Created by José Luis Valencia Herrera on 2/01/16.
 */
public class Food extends SugarRecord {
    public String name;
    public FoodType foodType;
    public FoodUnit foodUnit;

    public Food() {
    }
}
