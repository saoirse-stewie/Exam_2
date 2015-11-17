package com.example.Exam_2;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class MyActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    Button b1;
    Button b2;
    TextView t;
    EditText e;
    SQLiteDatabase db;


    String sd = "data/data/com.example.Exam_2";
    String mydb = sd + "/mydb.db";
    String TABLE_NAME = "employee";
    String col1 = "first_name";
    String col2 = "middle_initial";
    String col3 = "last_name";
    String col4 = "SSN";
    String col5 = "DOB";
    String col6 = "Address";
    String col7 = "Sex";
    String col8 = "Salary";
    String col9 = "Super_SSN";
    String col10 = "DNO";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b1 = (Button) findViewById(R.id.btn1);
        b2 = (Button) findViewById(R.id.btn2);
        t = (TextView) findViewById(R.id.editText);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                Integer xmlResFile = R.xml.employee;
                new backgroundAsncTask().execute(xmlResFile);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2.setEnabled(false);


                String sd = "data/data/com.example.Exam_2";
                String mydb = sd + "/mydb.db";

                db = SQLiteDatabase.openDatabase(mydb, null, SQLiteDatabase.CREATE_IF_NECESSARY);

                String s = "SELECT * from " + TABLE_NAME;

                Cursor c = db.rawQuery(s, null);
                t.append("\n-showTable: " + TABLE_NAME + showCursor(c));



                db.execSQL("update employee set Salary = 2");

            }
        });
    }

    public class backgroundAsncTask extends AsyncTask<Integer, Void, String> {

        ProgressDialog dialog = new ProgressDialog(MyActivity.this);

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String[] items = result.split("\n");
            setListAdapter(new ArrayAdapter<String>(MyActivity.this, android.R.layout.simple_list_item_1, items));
            System.out.println(result);

            String[] cols = {col1, col2, col3, col4, col5, col6, col7, col8, col9, col10};

            db = SQLiteDatabase.openDatabase(mydb, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            db.execSQL("drop table " + TABLE_NAME + ";");
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + col1 + " TEXT ," + col2 + " TEXT ," + col3 + " TEXT ," + col4 + " TEXT ," +
                    col5 + " TEXT ," + col6 + " TEXT ," + col7 + " TEXT ," + col8 + " TEXT ," + col9 + " TEXT ,"
                    + col10 + " TEXT );");


            ContentValues[] cv = new ContentValues[items.length];


            for (int rows = 0; rows < items.length; rows++) {
                for(int columns=0;columns<cols.length;columns++) {
                String temp = items[rows];

                ContentValues c = new ContentValues();
                    ContentValues c2 = new ContentValues();
                    ContentValues c3 = new ContentValues();

                    c.put(cols[columns], temp);
                    c2.put(cols[columns], temp);
                    cv[rows] = c;

                 }

            }
            if (db != null) {
                for (int i = 0; i < items.length; i++) {
                    db.insert(TABLE_NAME, null, cv[i]);
                }
            }







            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected String doInBackground(Integer... params) {
            int xmlResFile = params[0];
            StringBuffer sb = new StringBuffer();
            XmlResourceParser xr = getResources().getXml(xmlResFile);
            int eventType = 0;
            try {
                eventType = xr.getEventType();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            while (eventType != xr.END_DOCUMENT) {
                if (eventType == xr.START_TAG) {
                    try {
                        if (xr.getName().equals("FNAME")) {

                            sb.append("\n" + xr.getName() + " " + xr.nextText());

                        } else if (xr.getName().equals("MINIT")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());
                        } else if (xr.getName().equals("LNAME")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());
                        } else if (xr.getName().equals("SSN")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());
                        } else if (xr.getName().equals("BDATE")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());

                        } else if (xr.getName().equals("ADDRESS")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());

                        } else if (xr.getName().equals("SEX")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());

                        } else if (xr.getName().equals("SALARY")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());

                        } else if (xr.getName().equals("SUPERSSN")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());

                        } else if (xr.getName().equals("DNO")) {
                            sb.append("\n" + xr.getName() + " " + xr.nextText());

                        }


                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    eventType = xr.next();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return sb.toString();
        }


    }
    private String showCursor( Cursor cursor) {
        // show SCHEMA (column names & types)
        cursor.moveToPosition(-1); //reset cursor's top
        String cursorData = "\nCursor: [";

        try {
            // get column names
            String[] colName = cursor.getColumnNames();
            for(int i=0; i<colName.length; i++){
                String dataType = getColumnType(cursor, i);
                cursorData += colName[i] + dataType;

                if (i<colName.length-1){
                    cursorData+= ", ";
                }
            }
        } catch (Exception e) {
        }
        cursorData += "]";

        // now get the rows
        cursor.moveToPosition(-1); //reset cursor's top
        while (cursor.moveToNext()) {
            String cursorRow = "\n[";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                cursorRow += cursor.getString(i);
                if (i<cursor.getColumnCount()-1)
                    cursorRow +=  ", ";
            }
            cursorData += cursorRow + "]";
        }
        return cursorData + "\n";
    }
    private String getColumnType(Cursor cursor, int i) {
        try {
            //peek at a row holding valid data
            cursor.moveToFirst();
            int result = cursor.getType(i);
            String[] types = {":NULL", ":INT", ":FLOAT", ":STR", ":BLOB", ":UNK" };
            //backtrack - reset cursor's top
            cursor.moveToPosition(-1);
            return types[result];
        } catch (Exception e) {
            return " ";
        }
    }


}






