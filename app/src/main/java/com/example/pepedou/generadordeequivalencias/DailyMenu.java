package com.example.pepedou.generadordeequivalencias;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by José Luis Valencia Herrera on 2/01/16.
 */
public class DailyMenu extends SugarRecord {
    public String menuName;

    public DailyMenu() {
    }

    public List<DailyMenuEntry> getMenuEntries() {
        return DailyMenuEntry.find(DailyMenuEntry.class, "daily_menu = ?", getId().toString());
    }
}
