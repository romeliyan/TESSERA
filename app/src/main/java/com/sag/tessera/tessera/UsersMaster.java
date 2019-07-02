package com.sag.tessera.tessera;

import android.provider.BaseColumns;

public final class UsersMaster {

    private UsersMaster(){}

    public static class Users implements BaseColumns{

        public static final String TABLE_NAME="users";
        public static final String COLUMN_NAME_FIRST_NAME = "firstname";
        public static final String COLUMN_NAME_LAST_NAME="lastname";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FIREBASE_UID = "firebase_uid";

        public static final String TABLE_SCORE = "score_table";
        public static final String COLUMN_NAME_SCORE = "score";
    }
}
