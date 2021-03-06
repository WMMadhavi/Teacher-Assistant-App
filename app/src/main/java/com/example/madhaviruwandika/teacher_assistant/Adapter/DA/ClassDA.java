package com.example.madhaviruwandika.teacher_assistant.Adapter.DA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.madhaviruwandika.teacher_assistant.Database.DBConnection;
import com.example.madhaviruwandika.teacher_assistant.Database.DBConstant;
import com.example.madhaviruwandika.teacher_assistant.Database.DataAccess.ClassDAO;
import com.example.madhaviruwandika.teacher_assistant.Model.AppConstant;
import com.example.madhaviruwandika.teacher_assistant.Model.Attendence;
import com.example.madhaviruwandika.teacher_assistant.Model.Exam;
import com.example.madhaviruwandika.teacher_assistant.Model.ExtraClass;
import com.example.madhaviruwandika.teacher_assistant.Model.Payment;
import com.example.madhaviruwandika.teacher_assistant.Model.StartOfClass;
import com.example.madhaviruwandika.teacher_assistant.Model.Student;
import com.example.madhaviruwandika.teacher_assistant.Model.TutionClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madhavi Ruwandika on 4/12/2016.
 */
public class ClassDA implements ClassDAO{

    SQLiteDatabase db;

    public ClassDA(Context context) {
        db = DBConnection.getInstance(context).getWritableDatabase();
    }

    @Override
    public long addClass(TutionClass tutionClass) {

        Log.d("MYACTIVITY", "INSERTING TO Tution Class");

        // set values to contentValues
        ContentValues contentValues = new ContentValues();
        Log.d("CLASSID","UUUUUUUUUUUUUUUUUUUU>>>>>>>>>>>>>>"+(getClassID()+1)+"<<<<<<<<<<<<<<<<<<UUUUUUUUUUUUUUUUUUU");
        contentValues.put(DBConstant.tutionClass_col1,getClassID()+1);
        contentValues.put(DBConstant.tutionClass_col2,tutionClass.getClassName());
        contentValues.put(DBConstant.tutionClass_col3,tutionClass.getStartDate().toString());
        contentValues.put(DBConstant.tutionClass_col4,tutionClass.getEndDate().toString());
        contentValues.put(DBConstant.tutionClass_col5,tutionClass.getDay());
        contentValues.put(DBConstant.tutionClass_col6,tutionClass.getTime());
        contentValues.put(DBConstant.tutionClass_col7,tutionClass.getFee());


        long result = db.insert("TutionClass", null, contentValues);

        if(result == -1){
            Log.d("MYACTIVITY", "VALUES ARE NOT_INSERTED TO TutionClass");
            return result;
        }
        else {
            Log.d("MYACTIVITY", "VALUES ARE INSERTED TO TutionClass ");
            return result;
        }
    }

    @Override
    public int getClassID() {

        List<Integer> idList = new ArrayList<>();
        int idnext;
        Cursor cursor = db.rawQuery("select " + DBConstant.tutionClass_col1 + " from TutionClass ORDER BY "+DBConstant.tutionClass_col1+" DESC LIMIT 1", null);
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            idnext = 0;
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    idnext = id;
                    Log.d("CLASSID","UUUUUUUUUUUUUUUUUUUUTTTTTTTTTTTTTTTTTTTT>>>>>>>>>>>>>>"+idnext+"<<<<<<<<<<<<<<<<<<UUUUUUUUUUUUUUUUUUU");

                } while (cursor.moveToNext());
            }
            else {
                idnext = 0;
            }
        }

        Log.d("CLASSID","UUUUUUUUUUUUUUUUUUUU>>>>>>>>>>>>>>"+idnext+"<<<<<<<<<<<<<<<<<<UUUUUUUUUUUUUUUUUUU");
        return idnext;
    }

    @Override
    public List<TutionClass> getClassDetails() {
        List<TutionClass> ClassList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from TutionClass", null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY", "No Value");
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    TutionClass t = new TutionClass();

                    t.setClassID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBConstant.tutionClass_col1))));
                    t.setStartDate(cursor.getString(cursor.getColumnIndex(DBConstant.tutionClass_col3)));
                    t.setClassName(cursor.getString(cursor.getColumnIndex(DBConstant.tutionClass_col2)));
                    t.setDay(cursor.getString(cursor.getColumnIndex(DBConstant.tutionClass_col5)));
                    t.setTime(cursor.getString(cursor.getColumnIndex(DBConstant.tutionClass_col6)));

                    ClassList.add(t);

                } while (cursor.moveToNext());
            }
        }
        return ClassList;
    }

    @Override
    public long addPayment(Payment payment) {
        Log.d("MYACTIVITY", "INSERTING TO payment");

        // set values to contentValues
        ContentValues contentValues = new ContentValues();
        Log.d("Payment", ">>>>>>>>>>>>>>>>>>>>>>>>." + payment.getMonthOfPayment() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        contentValues.put(DBConstant.payment_col1, getPaymentID()+1);
        contentValues.put(DBConstant.payment_col2, payment.getSID());
        contentValues.put(DBConstant.payment_col3, payment.getCID());
        contentValues.put(DBConstant.payment_col4, payment.getDoP());
        contentValues.put(DBConstant.payment_col5,payment.getMonthOfPayment());

        long result = db.insert("Payment", null, contentValues);

        if(result == -1){
            Log.d("MYACTIVITY", "VALUES ARE NOT_INSERTED TO Payment");
            return result;
        }
        else {
            Log.d("MYACTIVITY", "VALUES ARE INSERTED TO payment ");
            return result;
        }
    }

    @Override
    public int getPaymentID() {
        List<Integer> idList = new ArrayList<>();
        int idnext;
        Cursor cursor = db.rawQuery("select " + DBConstant.payment_col1 + " from Payment ORDER BY "+DBConstant.payment_col1+" DESC LIMIT 1", null);
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            idnext = 0;
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    idnext = id;
                } while (cursor.moveToNext());
            }
            else {
                idnext=0;
            }
        }
        return idnext;
    }


    public long addExtraClass(ExtraClass exClass) {

        Log.d("MYACTIVITY", "INSERTING TO Tution Class");

        // set values to contentValues
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBConstant.ExtraClass_col1,getExClassID()+1);
        contentValues.put(DBConstant.ExtraClass_col2,exClass.getClassID());
        contentValues.put(DBConstant.ExtraClass_col3,exClass.getDate());
        contentValues.put(DBConstant.ExtraClass_col4,exClass.getTime());
        contentValues.put(DBConstant.ExtraClass_col5,exClass.getClassType());
        contentValues.put(DBConstant.ExtraClass_col6,"Active");


        long result = db.insert("Extra_Class", null, contentValues);

        if(result == -1){
            Log.d("MYACTIVITY", "VALUES ARE NOT_INSERTED TO Extra_Class");
            return result;
        }
        else {
            Log.d("MYACTIVITY", "VALUES ARE INSERTED TO Extra_Class ");
            return result;
        }
    }

    public int getExClassID() {

        int idnext;
        Cursor cursor = db.rawQuery("select " + DBConstant.ExtraClass_col1 + " from Extra_Class ORDER BY "+DBConstant.ExtraClass_col1+" DESC LIMIT 1", null);
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            idnext = 0;
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    idnext = id;
                } while (cursor.moveToNext());
            }
            else {
                idnext=0;
            }
        }
        return idnext;
    }

    @Override
    public TutionClass getClassByID(int id) {

        TutionClass tutionClass = new TutionClass();
        Cursor cursor = db.rawQuery("select * from TutionClass where " + DBConstant.tutionClass_col1 + "=" + String.valueOf(id), null);
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            return null;
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    tutionClass.setClassID(Integer.parseInt(cursor.getString(0)));
                    tutionClass.setClassName(cursor.getString(1));
                    tutionClass.setStartDate(cursor.getString(2));
                    tutionClass.setEndDate(cursor.getString(3));
                    tutionClass.setDay(cursor.getString(4));
                    tutionClass.setTime(cursor.getString(5));
                    tutionClass.setFee(Integer.parseInt(cursor.getString(6)));

                } while (cursor.moveToNext());
            }
        }
        return tutionClass ;
    }

    public long addStudentMarks(ArrayList<String[]> s,int examID){

        int ItemCount = s.size();
        long result = -1;
        for (int i=0;i<ItemCount;i++){

            try {
                // set values to contentValues
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBConstant.markSheet_col1,examID);
                contentValues.put(DBConstant.markSheet_col2, s.get(i)[0]);
                contentValues.put(DBConstant.markSheet_col3,Integer.parseInt(s.get(i)[1]));
                result = db.insert("PerformedAt", null, contentValues);
            }catch (Exception e){
                Log.d("MARK ADDING","Wrong mark");
            }


        }

        return result;

    }
    @Override
    public Long addExams(Exam exam) {

        // set values to contentValues
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBConstant.exam_col1,getExamID()+1);
        contentValues.put(DBConstant.exam_col2,exam.getClassID());
        contentValues.put(DBConstant.exam_col3,exam.getEtype());
        contentValues.put(DBConstant.exam_col4,exam.getDate());
        contentValues.put(DBConstant.exam_col5,exam.getLesson());

        long result = db.insert("Exam", null, contentValues);

        if(result == -1){
            Log.d("MYACTIVITY", "VALUES ARE NOT_INSERTED TO Extra_Class");
            return result;
        }
        else {
            Log.d("MYACTIVITY", "VALUES ARE INSERTED TO Extra_Class ");
            return result;
        }
    }

    public int getExamID() {

        List<Integer> idList = new ArrayList<>();
        int idnext;
        Cursor cursor = db.rawQuery("select " + DBConstant.exam_col1 + " from Exam ORDER BY "+DBConstant.exam_col1+" DESC LIMIT 1", null);
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            idnext = 0;
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    idnext = id;
                } while (cursor.moveToNext());
            }
            else {
                idnext = 0;
            }
        }
        return idnext;
    }

    public List<Exam> getExamList(){

        List<Exam> examList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from Exam", null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY", "No Value");
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    Exam exam = new Exam();

                    exam.setExamID(Integer.parseInt(cursor.getString(0)));
                    exam.setClassID(Integer.parseInt(cursor.getString(1)));
                    exam.setEtype(cursor.getString(2));
                    exam.setDate(cursor.getString(3));
                    exam.setLesson(cursor.getString(4));

                    examList.add(exam);


                } while (cursor.moveToNext());
            }
        }
        return examList;

    }

    public long UpdateClass(TutionClass tutionClass){

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBConstant.tutionClass_col1,tutionClass.getClassID());
        contentValues.put(DBConstant.tutionClass_col2,tutionClass.getClassName());
        contentValues.put(DBConstant.tutionClass_col3,tutionClass.getStartDate());
        contentValues.put(DBConstant.tutionClass_col4,tutionClass.getEndDate());
        contentValues.put(DBConstant.tutionClass_col5, tutionClass.getDay());
        contentValues.put(DBConstant.tutionClass_col6, tutionClass.getTime());
        contentValues.put(DBConstant.tutionClass_col7,tutionClass.getFee());

        long i = db.update("TutionClass", contentValues, DBConstant.tutionClass_col1 + "=" + String.valueOf(tutionClass.getClassID()), null);

        return i;
    }

    @Override
    public List<Exam> getExamListByClassID(int class_id) {


        List<Exam> examList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from Exam where " + DBConstant.exam_col2 + " = " + class_id, null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY", "No Value");
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    Exam exam = new Exam();

                    exam.setExamID(Integer.parseInt(cursor.getString(0)));
                    exam.setClassID(Integer.parseInt(cursor.getString(1)));
                    exam.setEtype(cursor.getString(2));
                    exam.setDate(cursor.getString(3));
                    exam.setLesson(cursor.getString(4));

                    examList.add(exam);


                } while (cursor.moveToNext());
            }
        }
        return examList;
    }

    @Override
    public Long addDailyAttendence(ArrayList<Attendence> attendence_sheets) {

        long result = -1;
        for (int i=0;i<attendence_sheets.size();i++) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConstant.attendenceSheet_col1, getAttendenceID() + 1);
            contentValues.put(DBConstant.attendenceSheet_col2, attendence_sheets.get(i).getS_id());
            contentValues.put(DBConstant.attendenceSheet_col3, attendence_sheets.get(i).getClass_Id());
            contentValues.put(DBConstant.attendenceSheet_col4, attendence_sheets.get(i).getDateOPA());
            contentValues.put(DBConstant.attendenceSheet_col5, attendence_sheets.get(i).isAttendenceState());
            result = db.insert("Attendance_Sheet",null,contentValues);
        }

        return result;

    }

    public int getAttendenceID() {
        List<Integer> idList = new ArrayList<>();
        int idnext;
        Cursor cursor = db.rawQuery("select " + DBConstant.attendenceSheet_col1 + " from  Attendance_Sheet ORDER BY "+DBConstant.attendenceSheet_col1+" DESC LIMIT 1", null);
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            idnext = 0;
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    idnext = id;
                } while (cursor.moveToNext());
            }
            else {
                idnext = 0;
            }
        }
        return idnext;
    }

    @Override
    public int getStartedClassWithinTheDay(String date, int classID) {

        int state = 0;

        Log.d("Starting Class",">>>>>>>>>>>>>>>>>>>>>>.Claaing Class DA.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Cursor cursor = db.rawQuery("select * from StartOfClass where " + DBConstant.StartOfClass_col1 + " = ? and " + DBConstant.StartOfClass_col2 + " =?",new String[]{String.valueOf(classID),date});
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            state = 0;
            return state;
        } else {
            return 1;
        }
    }

    @Override
    public long markStartingOfClass(int classID, String date, String Starttime, String endTime) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBConstant.StartOfClass_col1,classID);
        contentValues.put(DBConstant.StartOfClass_col2,date);
        contentValues.put(DBConstant.StartOfClass_col3,Starttime);
        contentValues.put(DBConstant.StartOfClass_col4, endTime);
        long result = db.insert("StartOfClass", null, contentValues);

        return result;
    }

    @Override
    public List<Student> getStudentListByClasssID(int id) {

        List<Student> NameList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from Student inner join Attend on Student.ID = Attend.S_id where Class_Id = " + String.valueOf(id), null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY", "No Value");
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    Student student = new Student();

                    student.setS_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBConstant.stdTable_col1))));
                    student.setName(cursor.getString(cursor.getColumnIndex(DBConstant.stdTable_col2)));
                    student.setAddress(cursor.getString(cursor.getColumnIndex(DBConstant.stdTable_col4)));
                    student.setDoB(cursor.getString(cursor.getColumnIndex(DBConstant.stdTable_col3)));

                    NameList.add(student);
                } while (cursor.moveToNext());
            }
        }
        return NameList;

    }

    public List<StartOfClass> getTodaysClassList(String date){
        List<StartOfClass> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select *  from  StartOfClass where "+DBConstant.StartOfClass_col2+" = " + date, null);
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {

                    StartOfClass startOfClass = new StartOfClass();
                    startOfClass.setClassID(cursor.getInt(0));
                    startOfClass.setDate(cursor.getString(1));
                    startOfClass.setStart_Time(cursor.getString(2));
                    startOfClass.setEnd_Time(cursor.getString(3));
                    list.add(startOfClass);
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

    public long markFinishingOfTheClass(StartOfClass startOfClass){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstant.StartOfClass_col4,startOfClass.getEnd_Time());

        long i = db.update("StartOfClass", contentValues, DBConstant.StartOfClass_col1 + " = ? AND " + DBConstant.StartOfClass_col2 + " = ? AND " + DBConstant.StartOfClass_col3 + " = ? ", new String[]{String.valueOf(startOfClass.getClassID()), startOfClass.getDate(), startOfClass.getStart_Time()});
        return i;

    }

    @Override
    public List<ExtraClass> getExtraClassByID(int ClassID) {
        List<ExtraClass> list = new ArrayList<>();
        int idnext;
        Cursor cursor = db.rawQuery("select * from Extra_Class where Class_Id =? AND ClassState=? ", new String[]{String.valueOf(ClassID), "Active"});
        if (cursor.getCount() == 0) {
            Log.d("MYACTIVITY", "No Value");
            idnext = 0;
        } else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    ExtraClass extraClass = new ExtraClass();

                    extraClass.setExtraClassID(cursor.getInt(0));
                    extraClass.setClassID(cursor.getInt(1));
                    extraClass.setDate(cursor.getString(2));
                    extraClass.setTime(cursor.getString(3));
                    extraClass.setClassType(cursor.getString(4));
                    extraClass.setClassState(cursor.getString(5));
                    list.add(extraClass);

                } while (cursor.moveToNext());
            }
        }
        return list;
    }


    public long CancelExtraClass(int id){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstant.ExtraClass_col6,"Cancel");
        long i = db.update("Extra_Class", contentValues, DBConstant.ExtraClass_col1 + " = ?" , new String[]{ String.valueOf(id)});
        return i;


    }

    @Override
    public long DeleteClass(int classID) {

        long i = db.delete("TutionClass",DBConstant.tutionClass_col1+ " = "+classID,null);
        return i;

    }




}
