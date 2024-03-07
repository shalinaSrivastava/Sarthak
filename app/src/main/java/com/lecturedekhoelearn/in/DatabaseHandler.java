package com.lecturedekhoelearn.in;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lecturedekhoelearn.in.model.DailyQuizModelDetails;
import com.lecturedekhoelearn.in.model.ShowBookMarkModelDetails;
import com.lecturedekhoelearn.in.model.TestJDetail;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION = 2;

    private static final String DATABSE_NAME = "Test_paper";
    private static final String TABLE_MYTEST = "mytest";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_QID = "q_id";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_QSTN = "q_desc";
    private static final String KEY_QSTN_TYPE = "ques_type";

    private static final String KEY_OPTN1 = "ans1";
    private static final String KEY_OPTN2 = "ans2";
    private static final String KEY_OPTN3 = "ans3";
    private static final String KEY_OPTN4 = "ans4";
    private static final String KEY_SOLUTION = "solution";
    private static final String KEY_IMG_STATUS = "imgstatus";
    private static final String KEY_IMG_PATH = "img_path";

    private static final String KEY_TRUE_ANS = "true_ans";
    private static final String KEY_TRUE_ANS1 = "true_ans1";
    private static final String KEY_TRUE_ANS2 = "true_ans2";
    private static final String KEY_TRUE_ANS3 = "true_ans3";
    private static final String KEY_TRUE_ANS4 = "true_ans4";

    private static final String KEY_USER_ANS = "user_ans";

    private static final String KEY_USER_ANS1 = "user_ans1";
    private static final String KEY_USER_ANS2 = "user_ans2";
    private static final String KEY_USER_ANS3 = "user_ans3";
    private static final String KEY_USER_ANS4 = "user_ans4";

    private static final String KEY_TIME = "user_time";
    private static final String KEY_FQID = "fq_id";

    public DatabaseHandler(Context context) {
        super(context, DATABSE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MYTEST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_QID + " TEXT," + KEY_COURSE_ID + " TEXT," + KEY_QSTN + " TEXT," + KEY_OPTN1 + " TEXT,"
                + KEY_OPTN2 + " TEXT," + KEY_OPTN3 + " TEXT," + KEY_OPTN4 + " TEXT," + KEY_SOLUTION + " Text," + KEY_USER_ANS + " TEXT," + KEY_USER_ANS1 + " TEXT," + KEY_USER_ANS2 + " TEXT," + KEY_USER_ANS3 + " TEXT," + KEY_USER_ANS4 + " TEXT," + KEY_IMG_STATUS + " TEXT,"
                + KEY_IMG_PATH + " TEXT," + KEY_FQID + " TEXT," + KEY_TIME + " TEXT,"
                + KEY_TRUE_ANS + " TEXT,"
                + KEY_QSTN_TYPE + " TEXT,"
                + KEY_TRUE_ANS1 + " TEXT,"
                + KEY_TRUE_ANS2 + " TEXT,"
                + KEY_TRUE_ANS3 + " TEXT,"
                + KEY_TRUE_ANS4 + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE mytest ADD COLUMN " + KEY_FQID + "TEXT");
        }

    }

    //ADdING NEW QUESTION
    public void addQuestion(DailyQuizModelDetails testPaperData, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QID, i + 1);
        System.out.println("qId" + i + 1);
        values.put(KEY_COURSE_ID, testPaperData.getSubject_id());
        System.out.println("courseId" + testPaperData.getSubject_id());
        values.put(KEY_QSTN, testPaperData.getQuestion());
        System.out.println("qstn" + testPaperData.getQuestion());

        values.put(KEY_OPTN1, testPaperData.getA());
        System.out.println("optn1" + testPaperData.getA());
        values.put(KEY_OPTN2, testPaperData.getB());
        System.out.println("optn2" + testPaperData.getB());
        values.put(KEY_OPTN3, testPaperData.getC());
        System.out.println("optn3" + testPaperData.getC());
        values.put(KEY_OPTN4, testPaperData.getD());
        System.out.println("optn4" + testPaperData.getD());

        values.put(KEY_TRUE_ANS, testPaperData.getAnswer());
        System.out.println("Answer" + testPaperData.getAnswer());

        values.put(KEY_TRUE_ANS1, testPaperData.getAnswer_a());
        System.out.println("ans1" + testPaperData.getAnswer_a());

        values.put(KEY_TRUE_ANS2, testPaperData.getAnswer_b());
        System.out.println("ans2" + testPaperData.getAnswer_b());

        values.put(KEY_TRUE_ANS3, testPaperData.getAnswer_c());
        System.out.println("ans3" + testPaperData.getAnswer_c());

        values.put(KEY_TRUE_ANS4, testPaperData.getAnswer_d());
        System.out.println("ans4" + testPaperData.getAnswer_d());

        values.put(KEY_FQID, testPaperData.getId());
        System.out.println("qid" + testPaperData.getId());

        //insert row
        db.insert(TABLE_MYTEST, null, values);
        db.close();

    }


    public void resetDatabse() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_MYTEST);
        db.execSQL("delete from sqlite_sequence where  name = 'mytest'");

    }


    public List<DailyQuizModelDetails> getAllQuestions() {
        List<DailyQuizModelDetails> questionsList = new ArrayList<>();
        String selectQuerry = " SELECT * fROM " + TABLE_MYTEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                DailyQuizModelDetails questions1 = new DailyQuizModelDetails();
                questions1.setId(cursor.getString(cursor.getColumnIndex(KEY_QID)));
                questions1.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QSTN)));
                questions1.setA(cursor.getString(cursor.getColumnIndex(KEY_OPTN1)));
                questions1.setB(cursor.getString(cursor.getColumnIndex(KEY_OPTN2)));
                questions1.setC(cursor.getString(cursor.getColumnIndex(KEY_OPTN3)));
                questions1.setD(cursor.getString(cursor.getColumnIndex(KEY_OPTN4)));
                questions1.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS)));
                questions1.setAnswer_a(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS1)));
                questions1.setAnswer_b(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS2)));
                questions1.setAnswer_c(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS3)));
                questions1.setAnswer_d(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS4)));
                questions1.setId(cursor.getString(cursor.getColumnIndex(KEY_FQID)));
                questionsList.add(questions1);
            } while (cursor.moveToNext());
        }
        return questionsList;
    }

    public long updateAnswerAssignemnt(String qid, String useranswer1, String useranswer2, String useranswer3, String useranswer4) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ANS1, useranswer1);
        values.put(KEY_USER_ANS2, useranswer2);
        values.put(KEY_USER_ANS3, useranswer3);
        values.put(KEY_USER_ANS4, useranswer4);
        long ab = db.update(TABLE_MYTEST, values, KEY_QID + " = ?",
                new String[]{String.valueOf(qid)});
        if (ab > 0)
            Log.e("update", qid + "");
        else
            Log.e("notupdate", ab + "");
        // updating row
        return ab;
    }


    //ADdING NEW QUESTION
    public void addQuestion(TestJDetail testPaperData, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QID, i + 1);
        System.out.println("qId" + i + 1);
        values.put(KEY_COURSE_ID, testPaperData.getSubject_id());
        System.out.println("courseId" + testPaperData.getSubject_id());
        values.put(KEY_QSTN, testPaperData.getQuestion());
        System.out.println("qstn" + testPaperData.getQuestion());

        values.put(KEY_OPTN1, testPaperData.getA());
        System.out.println("optn1" + testPaperData.getA());
        values.put(KEY_OPTN2, testPaperData.getB());
        System.out.println("optn2" + testPaperData.getB());
        values.put(KEY_OPTN3, testPaperData.getC());
        System.out.println("optn3" + testPaperData.getC());
        values.put(KEY_OPTN4, testPaperData.getD());
        System.out.println("optn4" + testPaperData.getD());

        values.put(KEY_TRUE_ANS1, testPaperData.getAnswer_a());
        System.out.println("ans1" + testPaperData.getAnswer_a());

        values.put(KEY_TRUE_ANS2, testPaperData.getAnswer_b());
        System.out.println("ans2" + testPaperData.getAnswer_b());

        values.put(KEY_TRUE_ANS3, testPaperData.getAnswer_c());
        System.out.println("ans3" + testPaperData.getAnswer_c());

        values.put(KEY_TRUE_ANS4, testPaperData.getAnswer_d());
        System.out.println("ans4" + testPaperData.getAnswer_d());

        values.put(KEY_FQID, testPaperData.getId());
        System.out.println("qid" + testPaperData.getId());
        //insert row
        db.insert(TABLE_MYTEST, null, values);
        db.close();

    }







    public List<TestJDetail> getAllTestQuestions() {
        List<TestJDetail> questionsList = new ArrayList<>();
        String selectQuerry = " SELECT * fROM " + TABLE_MYTEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                TestJDetail questions1 = new TestJDetail();
                questions1.setId(cursor.getString(cursor.getColumnIndex(KEY_QID)));
                questions1.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QSTN)));

                questions1.setA(cursor.getString(cursor.getColumnIndex(KEY_OPTN1)));
                questions1.setB(cursor.getString(cursor.getColumnIndex(KEY_OPTN2)));
                questions1.setC(cursor.getString(cursor.getColumnIndex(KEY_OPTN3)));
                questions1.setD(cursor.getString(cursor.getColumnIndex(KEY_OPTN4)));

                //  questions1.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS)));
                questions1.setAnswer_a(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS1)));
                questions1.setAnswer_b(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS2)));
                questions1.setAnswer_c(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS3)));
                questions1.setAnswer_d(cursor.getString(cursor.getColumnIndex(KEY_TRUE_ANS4)));

                questions1.setId(cursor.getString(cursor.getColumnIndex(KEY_FQID)));
                questionsList.add(questions1);
            } while (cursor.moveToNext());


        }
        return questionsList;
    }



    public List<ShowBookMarkModelDetails> getAllBookMark() {
        List<ShowBookMarkModelDetails> questionsList = new ArrayList<>();
        String selectQuerry = " SELECT * fROM " + TABLE_MYTEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                ShowBookMarkModelDetails questions1 = new ShowBookMarkModelDetails();
                questions1.setId(cursor.getString(cursor.getColumnIndex(KEY_QID)));
                questions1.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QSTN)));

                questions1.setA(cursor.getString(cursor.getColumnIndex(KEY_OPTN1)));
                questions1.setB(cursor.getString(cursor.getColumnIndex(KEY_OPTN2)));
                questions1.setC(cursor.getString(cursor.getColumnIndex(KEY_OPTN3)));
                questions1.setD(cursor.getString(cursor.getColumnIndex(KEY_OPTN4)));


                questions1.setId(cursor.getString(cursor.getColumnIndex(KEY_FQID)));
                questionsList.add(questions1);
            } while (cursor.moveToNext());


        }
        return questionsList;
    }

    //ADdING NEW QUESTION
    public void addBookQuestion(ShowBookMarkModelDetails testPaperData, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QID, i + 1);
        System.out.println("qId" + i + 1);
        values.put(KEY_COURSE_ID, testPaperData.getSubject_id());
        System.out.println("courseId" + testPaperData.getSubject_id());
        values.put(KEY_QSTN, testPaperData.getQuestion());
        System.out.println("qstn" + testPaperData.getQuestion());

        values.put(KEY_OPTN1, testPaperData.getA());
        System.out.println("optn1" + testPaperData.getA());
        values.put(KEY_OPTN2, testPaperData.getB());
        System.out.println("optn2" + testPaperData.getB());
        values.put(KEY_OPTN3, testPaperData.getC());
        System.out.println("optn3" + testPaperData.getC());
        values.put(KEY_OPTN4, testPaperData.getD());
        System.out.println("optn4" + testPaperData.getD());

        values.put(KEY_FQID, testPaperData.getId());
        System.out.println("qid" + testPaperData.getId());
        //insert row
        db.insert(TABLE_MYTEST, null, values);
        db.close();

    }


}