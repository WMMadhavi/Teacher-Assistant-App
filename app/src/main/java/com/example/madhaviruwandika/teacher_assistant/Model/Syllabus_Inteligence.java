package com.example.madhaviruwandika.teacher_assistant.Model;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Madhavi Ruwandika on 5/22/2016.
 */
public class Syllabus_Inteligence {

    private static Syllabus_Inteligence syllabus_inteligence;
    private SyllabusTree syllabusTree;
    List<Lesson> SortedLessonListAccordingToTime;

    private Syllabus_Inteligence(){
        this.syllabusTree = new SyllabusTree();
    }

    public static Syllabus_Inteligence getInstance(){
        if(syllabus_inteligence == null){
            synchronized(Syllabus_Inteligence.class){
                if(syllabus_inteligence == null){
                    syllabus_inteligence = new Syllabus_Inteligence();
                }
            }
        }
        return syllabus_inteligence;
    }

    public String getCommentOnWork(Lesson lesson){
        String comment = "";
        // generate key value which will be used to generate comment
        if(lesson.getAmountCovered()==1 && lesson.getTotaltimeSupposedToSpend()==lesson.getAmountTimeSpent()){
            comment = "equal";
        }
        else if(lesson.getAmountCovered()==1 && lesson.getTotaltimeSupposedToSpend()>lesson.getAmountTimeSpent()){
            comment = "low";
        }
        else if(lesson.getAmountCovered()==1 && lesson.getTotaltimeSupposedToSpend()<lesson.getAmountTimeSpent()){
            comment = "high";
        }
        else if(lesson.getAmountCovered()!= 1 && lesson.getTotaltimeSupposedToSpend() > (lesson.getAmountTimeSpent()/lesson.getAmountCovered())){
            comment = "low";
        }
        else if (lesson.getAmountCovered()!= 1 && lesson.getTotaltimeSupposedToSpend() < (lesson.getAmountTimeSpent()/lesson.getAmountCovered())) {
            comment = "high";
        }
        else if (lesson.getAmountCovered()!= 1 && lesson.getTotaltimeSupposedToSpend() == (lesson.getAmountTimeSpent()/lesson.getAmountCovered())){
            comment = "equal";
        }

        return comment;
    }

    public String getCommentComparedToOverallSyllabus(List<LessonUnit> units,List<Lesson> lessons,TutionClass tutionClass,int extraClasseTime){
        String comment = "";
        int remainingClassTime = 0;
        int remainingTimeFromSyl = 0;
        // assign syllabus to tree
        syllabusTree.fillTree(units,lessons);
        //get syllabus lesson list sorted according to amount covered and time limit
        SortedLessonListAccordingToTime = syllabusTree.getSortedLessonListAccordingToTime();
        // format Date
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        // format and get timeperiod
        String time = tutionClass.getTime();
        String[] timeperiod = new String[2];
        timeperiod = time.split("-");
        int timePeriodForDay = getTimePeriod(timeperiod[0],timeperiod[1]);

        try {

            int NoOFDays = getWorkingDaysBetweenTwoDates(dateFormat.parse(dateFormat.format(cal.getTime())),dateFormat.parse(tutionClass.getEndDate()),tutionClass.getDay());
            remainingClassTime = (timePeriodForDay*NoOFDays)+ extraClasseTime;

        }catch (Exception e){
            Log.d("MYACTIVITY","Invalied Date Format");
        }
        remainingTimeFromSyl = syllabusTree.getRequiredTimeToCoverRemaining();

        if(remainingClassTime == remainingTimeFromSyl){
            comment = "0nShedule";
        }
        else if(remainingClassTime > remainingTimeFromSyl)
        {
           comment = "OnSchedule";
        }
        else {
            comment = "behindTheSchedule";
        }

        return comment;
    }

    public String analyzeComment(String c2,String c1){
        String comment = "";

        if(c1 == "behindTheSchedule" && c2 == "equal"){
            comment = "Although you covered the lesson within the allocated time you in behind the schedule. Please try to cover lesson as soon as posible.If you have time you can Shedule extra class.";
        }
        else if(c1 == "behindTheSchedule" && c2 == "high"){
            comment = "You take more time to cover the lessons than sheduled.Please manege your time and do a good job.If you have time you can Shedule extra class.";
        }
        else if(c1 == "behindTheSchedule" && c2 == "low"){
            comment = "You coved the todays lesson in less time than sheduled.Since you are running behind the schedule it is good.But pay attention whether childern understood the lesson.";
        }
        else if(c1 == "behindTheSchedule" && c2 == ""){
            comment = "you are running behind the schedule.pay attention on it.";
        }

        else if(c1 == "OnSchedule" && c2 == "equal"){
            comment = "Great job. You are on schedule.";
        }
        else if(c1 == "OnSchedule" && c2 == "high"){
            comment = "You are on schedule.But you took more time than the schedule.";
        }
        else if(c1 == "OnSchedule" && c2 == "low"){
            if(SortedLessonListAccordingToTime != null) {
                Lesson lesson = SortedLessonListAccordingToTime.get(SortedLessonListAccordingToTime.size()-1);
                comment = "You are on schedule.You took less time to cover the lesson today.So you can plan on few Revision classes Or cou can spend more time on "+lesson.getLesson()+"which supposed to take more time as in schedule.";
            }
            else {
                comment = "You are on schedule.You took less time to cover the lesson today.So you can plan on few Revision classes";
            }
        }
        else if(c1 == "OnSchedule" && c2 == ""){
            comment = "You are on schedule.Good work.";
        }
        return  comment;
    }

    public String[] giveComment(List<LessonUnit> units,List<Lesson> lessons,Lesson lesson,TutionClass tutionClass,int extraClasseTime){

        String[] overallComment = new String[2];

        if(lesson == null) {

            overallComment[0] = "";
            overallComment[1] = analyzeComment(overallComment[0], getCommentComparedToOverallSyllabus(units, lessons, tutionClass, extraClasseTime));
            Log.d("MY",">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+overallComment[1]+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        }
        else {
            overallComment[0] = getCommentOnWork(lesson);
            overallComment[1] = analyzeComment(overallComment[0],getCommentComparedToOverallSyllabus(units, lessons, tutionClass, extraClasseTime));

        }
        return overallComment ;
    }

    public int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate,String day) {

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        Log.d("MY", ">>>>>>>>>" + startDate + ">>>>>>>>>>>>");
        Log.d("MY", ">>>>>>>>>" + endDate + ">>>>>>>>>>>>");
        int ClassDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }


        int Day = findDay(day);

        do {

            if (startCal.get(Calendar.DAY_OF_WEEK) == Day) {
                ClassDays+=1;
            }
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            //Log.d("MY", ">>>>>>>>>" + startCal.getTime() + ">>>>>>>>>>>>");
        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()); //excluding end date

        return ClassDays;
    }

     public  int findDay(String day){

        if(day == "Monday"){
            return Calendar.MONDAY;
        }else if(day == "Tuesday"){
            return Calendar.TUESDAY;
        }else if(day == "Wednessday"){
            return Calendar.WEDNESDAY;
        }else if (day == "Thursday"){
            return Calendar.THURSDAY;
        }else if (day == "Friday"){
            return Calendar.FRIDAY;
        }else if(day == "Saturday"){
            return Calendar.SATURDAY;
        }else  if(day == "Sunday"){
            return Calendar.SUNDAY;
        }else {
            return -1;
        }


    }

    public int getTimePeriod(String StartTime, String EndTime){


        if(StartTime.length()==6){
            StartTime = '0'+StartTime;
        }
        if(EndTime.length() == 6){
            EndTime = '0'+EndTime;
        }
        String formatStart_24 = "";
        String formatEnd_24 = "";

        long diff = 0;
        if(StartTime.substring(5).equals("am")){
            formatStart_24 = StartTime.substring(0,5);
            if(Integer.parseInt(formatStart_24.substring(0,2))>12){
                formatStart_24 = "";
            }
        }
        else if(StartTime.substring(5).equals("pm") ){
            int x = Integer.parseInt(StartTime.substring(0,2));
            x += 12;
            formatStart_24 = x+StartTime.substring(2,5);

        }
        if(EndTime.substring(5).equals("am")){
            formatEnd_24 = EndTime.substring(0,3);//12.23am
            if(Integer.parseInt(formatEnd_24.substring(0,2))>12){
                formatEnd_24 = "";
            }
        }

        else if(EndTime.substring(5).equals("pm") ){
            int x = Integer.parseInt(EndTime.substring(0, 2));
            x += 12;
            formatEnd_24 = x+EndTime.substring(2,5);

        }


        SimpleDateFormat format = new SimpleDateFormat("HH.mm");
        Date d1 = null;
        Date d2 = null;
        long diffMinutes = 0;
        long diffHours = 0;

        try {
            d1 = format.parse(formatStart_24);
            d2 = format.parse(formatEnd_24);
            //in milliseconds

            diff = d2.getTime() - d1.getTime();
            if(diff>0) {
                diffMinutes = diff / (60 * 1000) % 60;
                diffHours = diff / (60 * 60 * 1000) % 24;

                diffMinutes += diffMinutes+ (diffHours*60);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int)(long)diffMinutes ;
    }

}
