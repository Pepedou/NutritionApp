package com.example.pepedou.generadordeequivalencias;

import com.orm.SugarRecord;

/**
 * Created by José Luis Valencia Herrera on 2/01/16.
 */
public class DailyMenuEntry extends SugarRecord {
    public DailyMenu dailyMenu;
    public FoodType foodType;
    public Integer foodQuantity;

    public DailyMenuEntry() {
    }
}
