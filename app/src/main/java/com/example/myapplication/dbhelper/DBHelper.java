package com.example.myapplication.dbhelper;

public class DBHelper {

        public static final String DATABASE_NAME = "AppShoes";
        public static Integer DATABASE_VERSION = 1;

        public static void setDatabaseVersion(Integer databaseVersion) {
            if (databaseVersion > DATABASE_VERSION)
                DATABASE_VERSION = databaseVersion;
        }

        public static void upgradeDatabaseVersion() {
            DATABASE_VERSION += 1;
        }
    }


