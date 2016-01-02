/**
 * Created by José Luis Valencia Herrera on 1/01/16.
 */
package com.example.pepedou.generadordeequivalencias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "FOOD_EQUIVALENTS_DB";
    static final String FOOD_TABLE_NAME = "FOOD";
    static final String DAILY_MENU_TABLE_NAME = "DAILY_MENU";
    static final String DAILY_MENU_ENTRY_TABLE_NAME = "DAILY_MENU_ENTRY";
    static final String FOOD_TYPE_TABLE_NAME = "FOOD_TYPE";
    static final String FOOD_UNIT_TABLE_NAME = "FOOD_UNIT";

    static final String FOOD_NAME_COLUMN = "name";
    static final String FOOD_QUANTITY_COLUMN = "quantity";
    static final String DAILY_MENU_NAME_COLUMN = "name";
    static final String DAILY_MENU_FK_COLUMN = "daily_menu_id";
    static final String DAILY_MENU_ENTRY_QUANTITY_COLUMN = "food_quantity";
    static final String FOOD_TYPE_FK_COLUMN = "food_type_id";
    static final String FOOD_UNIT_FK_COLUMN = "food_unit_id";
    static final String FOOD_TYPE_TYPE_COLUMN = "type";
    static final String FOOD_UNIT_LONG_NAME_COLUMN = "long_unit_name";
    static final String FOOD_UNIT_SHORT_NAME_COLUMN = "short_unit_name";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FOOD_TYPE_TABLE_NAME +
                "(id INTEGER PRIMARY KEY, " +
                FOOD_TYPE_TYPE_COLUMN + " VARCHAR(30) NOT NULL)");

        db.execSQL("CREATE TABLE " + FOOD_UNIT_TABLE_NAME +
                "(id INTEGER PRIMARY KEY, " +
                FOOD_UNIT_LONG_NAME_COLUMN + " VARCHAR(10) NOT NULL, " +
                FOOD_UNIT_SHORT_NAME_COLUMN + " VARCHAR(5))");

        db.execSQL("CREATE TABLE " + FOOD_TABLE_NAME +
                "(id INTEGER PRIMARY KEY, " +
                FOOD_NAME_COLUMN + " VARCHAR(15) NOT NULL, " +
                FOOD_QUANTITY_COLUMN + " INTEGER, " +
                FOOD_TYPE_FK_COLUMN + " INTEGER, " +
                FOOD_UNIT_FK_COLUMN + " INTEGER, " +
                "FOREIGN KEY ('" + FOOD_TYPE_FK_COLUMN + "') REFERENCES " + FOOD_TYPE_TABLE_NAME + " ('id'), " +
                "FOREIGN KEY ('" + FOOD_UNIT_FK_COLUMN + "') REFERENCES " + FOOD_UNIT_TABLE_NAME + " ('id'))");

        db.execSQL("CREATE TABLE " + DAILY_MENU_TABLE_NAME +
                "(id INTEGER PRIMARY KEY, " +
                DAILY_MENU_NAME_COLUMN + " VARCHAR(30) NOT NULL)");

        db.execSQL("CREATE TABLE " + DAILY_MENU_ENTRY_TABLE_NAME +
                "(id INTEGER PRIMARY KEY," +
                DAILY_MENU_FK_COLUMN + " INTEGER NOT NULL," +
                FOOD_TYPE_FK_COLUMN + " INTEGER NOT NULL," +
                DAILY_MENU_ENTRY_QUANTITY_COLUMN + " INTEGER NOT NULL DEFAULT 0," +
                "FOREIGN KEY (" + DAILY_MENU_FK_COLUMN + ") REFERENCES " + DAILY_MENU_TABLE_NAME + " ('id')," +
                "FOREIGN KEY (" + FOOD_TYPE_FK_COLUMN + ") REFERENCES " + FOOD_TYPE_TABLE_NAME + " ('id'))");

        insertDefaultEntries(db);
    }

    private void insertDefaultEntries(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        int foodTypeId = 1, foodUnitId = 1;
        String[] foodTypes = new String[]{
                "Verduras",
                "Frutas",
                "Cereales y Tubérculos",
                "Leguminosas",
                "Alimentos de Origen Natural",
                "Leche",
                "Aceites y Grasas",
                "Azúcares",
                "Aceites y Grasas B"
        };
        String[][] foodUnits = new String[][]{
                {"Pieza", "pz"},
                {"Taza", "tza"},
                {"Cucharada", "cuch"},
                {"Cucharadita", "cuchta"},
                {"Frasco", "fsco"},
                {"Sobre", "sbr"},
                {"Rebanada", "rbda"},
                {"Paquete", "pqt"},
                {"Gramo", "gr"},
                {"Mililitros", "ml"}
        };

        for (String entry : foodTypes) {
            cv.put("id", foodTypeId++);
            cv.put(FOOD_TYPE_TYPE_COLUMN, entry);
            db.insert(FOOD_TYPE_TABLE_NAME, null, cv);
        }

        for (String[] entry : foodUnits) {
            cv.put("id", foodUnitId++);
            cv.put(FOOD_UNIT_LONG_NAME_COLUMN, entry[0]);
            cv.put(FOOD_UNIT_SHORT_NAME_COLUMN, entry[1]);
            db.insert(FOOD_UNIT_TABLE_NAME, null, cv);
        }

        cv.put("id", 1);
        cv.put(FOOD_NAME_COLUMN, "Acelga");
        cv.put(FOOD_QUANTITY_COLUMN, 2);
        cv.put(FOOD_TYPE_FK_COLUMN, 1);
        cv.put(FOOD_UNIT_FK_COLUMN, 2);
        db.insert(FOOD_TABLE_NAME, null, cv);

        cv.put("id", 1);
        cv.put(DAILY_MENU_NAME_COLUMN, "Menú por defecto");
        db.insert(DAILY_MENU_TABLE_NAME, null, cv);

        cv.put("id", 1);
        cv.put(DAILY_MENU_FK_COLUMN, 1);
        cv.put(FOOD_TYPE_FK_COLUMN, 3);
        cv.put(DAILY_MENU_ENTRY_QUANTITY_COLUMN, 8);
        db.insert(DAILY_MENU_ENTRY_TABLE_NAME, null, cv);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TYPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_UNIT_TABLE_NAME);

        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + FOOD_TYPE_TABLE_NAME, null);
    }
}
