package com.example.madhaviruwandika.teacher_assistant.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Madhavi Ruwandika on 3/26/2016.
 */
public class DBCreator extends SQLiteOpenHelper {

        SQLiteDatabase db;

        public DBCreator(Context context) {
            super(context, DBConstant.DB_name,null,1);
            Log.d("MYACTIVITY", "Called Controller====================================================================================================================================");
            db= this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d("MYACTIVITY", "EXECUTING.......................................................................................................");
            /*
                * Createing tables in database
             */
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.execSQL("CREATE TABLE MyAccount (Change_ID INTEGER PRIMARY KEY, Name TEXT NOT NULL, password Text, DateOFChange TEXT );");
            db.execSQL("CREATE TABLE Student (ID INTEGER PRIMARY KEY, Name TEXT NOT NULL, DateOfBirth TEXT , Address VARCHAR(100) );");
            db.execSQL("CREATE TABLE Parent (Parent_ID INTEGER PRIMARY KEY, S_id INTEGER NOT NULL, Name TEXT , TP_no TEXT ,email VARCHAR(100), FOREIGN KEY(S_id) REFERENCES Student(ID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE Attend (S_id INTEGER, Class_Id INTEGER, FOREIGN KEY(S_id) REFERENCES Student(ID) ON DELETE CASCADE ,FOREIGN KEY(Class_Id) REFERENCES TutionClass(ClassID) ON DELETE CASCADE );");
            db.execSQL("CREATE TABLE TutionClass (ClassID INTEGER PRIMARY KEY, ClassName TEXT NOT NULL UNIQUE, StartDate TEXT ,EndDate TEXT ,Day TEXT,Time TEXT,Fee REAL);");
            db.execSQL("CREATE TABLE StudentBehaviour (Record_id INTEGER PRIMARY KEY,S_id INTEGER, Class_Id INTEGER, Behaviour TEXT ,DateOfRec TEXT, FOREIGN KEY(S_id) REFERENCES Student(ID) ON DELETE CASCADE,FOREIGN KEY(Class_Id) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE Payment (Payment_id INTEGER PRIMARY KEY,S_id INTEGER, Class_Id INTEGER, DateOP TEXT,Month_Of_Payment TEXT,FOREIGN KEY(S_id) REFERENCES Student(ID) ON DELETE CASCADE,FOREIGN KEY(Class_Id) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE Extra_Class (ExClassID INTEGER PRIMARY KEY,Class_Id INTEGER, DateOfClass TEXT, time Text,ClassType Text,ClassState Text,FOREIGN KEY(Class_Id) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE Attendance_Sheet (Attendance_id INTEGER PRIMARY KEY,S_id INTEGER, Class_Id INTEGER,DateOPA TEXT,AttendenceState INTEGER,FOREIGN KEY(S_id) REFERENCES Student(ID) ON DELETE CASCADE,FOREIGN KEY(Class_Id) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE SyllabusLesson (Unit_id INTEGER, Lesson Text, Lesson_no INTEGER, Time_Period INTEGER, Special_act Text,Finished_amount REAL,Time_taken INTEGER, FOREIGN KEY(Unit_id) REFERENCES Unit(UnitID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE Unit(UnitID INTEGER PRIMARY KEY, ClassID INTEGER,Unit Text, FOREIGN KEY(ClassID) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE DailyWork (Daily_Work_ID INTEGER PRIMARY KEY, DateOT TEXT ,UnitID INTEGER,Lesson_No INTEGER, TimeSpent INTEGER,Content_Covered Real,Comment TEXT,FOREIGN KEY(UnitID) REFERENCES Unit(UnitID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE GroupMessage (Message_ID INTEGER PRIMARY KEY, Msg_type INTEGER, DateOfMsg TEXT ,Content TEXT ,Class_Id INTEGER, No_Of_Recip TEXT, FOREIGN KEY(Class_Id) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);" );
            db.execSQL("CREATE TABLE SingleMessage(Message_ID INTEGER PRIMARY KEY, Msg_type INTEGER, DateOfMsg TEXT ,Content TEXT ,Recipient INTEGER);" );
            db.execSQL("CREATE TABLE Exam(Exam_ID INTEGER PRIMARY KEY, Class_ID INTEGER, InclassType TEXT ,DateOfExam TEXT ,Lesson TEXT, FOREIGN KEY(Class_ID) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);" );
            db.execSQL("CREATE TABLE PerformedAt(Exam_ID INTEGER,S_id INTEGER, Mark INTEGER , FOREIGN KEY(S_id) REFERENCES Student(ID) ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE StartOfClass(ClassID INTEGER,Date TEXT, Start_Time TEXT, End_Time TEXT, FOREIGN KEY(ClassID) REFERENCES TutionClass(ClassID) ON DELETE CASCADE);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            /*
            Drop tables if they exist
             */
            db.execSQL("DROP TABLE IF EXISTS Student");
            db.execSQL("DROP TABLE IF EXISTS Parent");
            db.execSQL("DROP TABLE IF EXISTS Attend");
            db.execSQL("DROP TABLE IF EXISTS TutionClass");
            db.execSQL("DROP TABLE IF EXISTS StudentBehaviour");
            db.execSQL("DROP TABLE IF EXISTS Payment");
            db.execSQL("DROP TABLE IF EXISTS Extra_Class");
            db.execSQL("DROP TABLE IF EXISTS Attendance_Sheet");
            db.execSQL("DROP TABLE IF EXISTS SyllabusLesson");
            db.execSQL("DROP TABLE IF EXISTS Unit");
            db.execSQL("DROP TABLE IF EXISTS DailyWork");
            db.execSQL("DROP TABLE IF EXISTS GroupMessage");
            db.execSQL("DROP TABLE IF EXISTS SingleMessage");
            db.execSQL("DROP TABLE IF EXISTS Exam");
            db.execSQL("DROP TABLE IF EXISTS PerformedAt");
            db.execSQL("DROP TABLE IF EXISTS StartOfClass");


            onCreate(db);
        }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public int ClearAllTheTable(){

        try {
            db.execSQL("delete from MyAccount");
            db.execSQL("delete from Student");
            db.execSQL("delete from Parent");
            db.execSQL("delete from Attend");
            db.execSQL("delete from TutionClass");
            db.execSQL("delete from StudentBehaviour");
            db.execSQL("delete from Payment");
            db.execSQL("delete from Extra_Class");
            db.execSQL("delete from Attendance_Sheet");
            db.execSQL("delete from SyllabusLesson");
            db.execSQL("delete from Unit");
            db.execSQL("delete from DailyWork");
            db.execSQL("delete from GroupMessage");
            db.execSQL("delete from SingleMessage");
            db.execSQL("delete from Exam");
            db.execSQL("delete from PerformedAt");
            db.execSQL("delete from StartOfClass");
            return 1;

        }catch (Exception e){
            return -1;
        }


    }

}
