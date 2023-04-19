package com.example.dnevnjak20.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dnevnjak20.activities.MainActivity;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.model.User;
import com.example.dnevnjak20.model.enums.ObligationPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    private static final String DATABASE_NAME = "dnevnjakDB.db";
    private static final int DATABASE_VERSION = 1;

    public static DatabaseHelper getInstance(@Nullable Context context) {
        if(instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("ON CREATE DB");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'user' (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL" +
                ");");
        // TODO smisli sta ces sa pozicijom, kako i gde ces da punis podacima
        // da li ovu listu dailyPlans da popunim isto kad se pokrene aplikacija,
        // imam foreign key DateItem u Planu pa bih preko toga mogla da popunjavam liste.
//        db.execSQL("CREATE TABLE IF NOT EXISTS 'DateItem' (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "day INTEGER NOT NULL, " +
//                "month INTEGER NOT NULL, " +
//                "year INTEGER NOT NULL " +
//                ");");
        // podaci o DateItem mi ne trebaju u biti.
        db.execSQL("CREATE TABLE IF NOT EXISTS 'plan' (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "priority TEXT CHECK (priority IN ('LOW_PRIORITY', 'MID_PRIORITY', 'HIGH_PRIORITY', 'ALL')) NOT NULL, " +
                "long_info TEXT, " +
                "name TEXT, " +
                "plan_date TEXT, " +
                "plan_time_from TEXT, " +
                "plan_time_to TEXT" +
                ");");
//        setDefaultUser();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Plan> getPlansForTheDay(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = DBColumns.PLAN_DATE + " = ?";
        List<Plan> plansToReturn = new ArrayList<>();

        try (Cursor result = db.query(DBTables.PLAN_TABLE, null, where, new String[]{date}, null, null, null)){
            while(result.moveToNext()){
                String priorityStr = result.getString(1);
                String longInfo = result.getString(2);
                String name = result.getString(3);
                String planDateStr = result.getString(4);
                String planTimeFromStr = result.getString(5);
                String planTimeToStr = result.getString(6);

                plansToReturn.add(new Plan(name, ObligationPriority.valueOf(priorityStr), LocalDate.parse(planDateStr),
                        LocalTime.parse(planTimeFromStr), LocalTime.parse(planTimeToStr), longInfo));
            }
            Collections.sort(plansToReturn);
            return plansToReturn;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void savePlan(Plan plan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues planValues = new ContentValues();
        planValues.put(DBColumns.PLAN_PRIORITY, plan.getPriority().toString());
        planValues.put(DBColumns.PLAN_LONG_INFO, plan.getLongInfo());
        planValues.put(DBColumns.PLAN_NAME, plan.getName());
        planValues.put(DBColumns.PLAN_DATE, plan.getPlanDate().toString());
        planValues.put(DBColumns.PLAN_TIME_FROM, plan.getPlanTimeFrom().toString());
        planValues.put(DBColumns.PLAN_TIME_TO, plan.getPlanTimeTo().toString());
        db.insert(DBTables.PLAN_TABLE, null, planValues);
//        db.close();
    }
    public void saveUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(DBColumns.USER_NAME, user.getUsername());
        userValues.put(DBColumns.USER_EMAIL, user.getEmail());
        userValues.put(DBColumns.USER_PASSWORD, user.getPassword());
        db.insert(DBTables.USER_TABLE, null, userValues);
//        db.close();
    }

    public int deletePlan(Plan plan) {
        String where = DBColumns.PLAN_TIME_FROM + " = ?";
        System.out.println("plan id: " + plan.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DBTables.PLAN_TABLE, where, new String[]{plan.getPlanTimeFrom().toString()});
    }

    public boolean isUserPresent(User user) {
        if(getAllUsers().isEmpty()) setDefaultUser();
        return !getAllUsers().stream().filter(user1 -> user.getUsername().equals(user1.getUsername())
        && user.getPassword().equals(user1.getPassword())
        && user.getEmail().equals(user1.getEmail())).collect(Collectors.toList()).isEmpty();
    }

    public List<User> getAllUsers() {
        List<User> toReturnUsers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.query(DBTables.USER_TABLE, null, null, null, null, null, null);

        while(result.moveToNext()) {
            String usernameFromDB = result.getString(1);
            String emailFromDB = result.getString(2);
            String passwordFromDB = result.getString(3);
            toReturnUsers.add(new User(usernameFromDB, emailFromDB, passwordFromDB));
        }
        return toReturnUsers;
    }

    public void setDefaultUser() {
        saveUser(new User("jelena", "jelena@raf.rs", "Jelena1"));
    }

}
