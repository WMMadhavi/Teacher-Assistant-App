package com.example.madhaviruwandika.teacher_assistant.Activity.SyllabusTrackerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madhaviruwandika.teacher_assistant.R;

import com.example.madhaviruwandika.teacher_assistant.Activity.SyllabusTrackerActivity.layout.AddWorkFragment;
import com.example.madhaviruwandika.teacher_assistant.Activity.SyllabusTrackerActivity.layout.SeeCommentFragment;

public class MyWorkActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    Bundle myBundle;
    int classID;
    private int UnitID = 0;
    private int lessonCoveredToday = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_my_work);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);

        //set home button and back arrow to toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tableLayout = (TabLayout) findViewById(R.id.tabs);
        tableLayout.setupWithViewPager(mViewPager);


        myBundle = getIntent().getExtras();
        classID = myBundle.getInt("ClassID");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class_manege, menu);
        return true;
    }

    public int getLessonCoveredToday() {
        return lessonCoveredToday;
    }

    public void setLessonCoveredToday(int lessonCoveredToday) {
        this.lessonCoveredToday = lessonCoveredToday;
    }

    public int getUnitID() {
        return UnitID;
    }

    public void setUnitID(int unitID) {
         UnitID = unitID;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.content_send_message, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle bundle = new Bundle();
            AddWorkFragment addWorkFragment = new AddWorkFragment();
            SeeCommentFragment seeCommentFragment = new SeeCommentFragment();

            switch (position) {

                case 0:

                    bundle.putInt("ClassID", classID);
                    bundle.putString("ClassName", myBundle.getString("ClassName"));
                    addWorkFragment.setArguments(bundle);
                    return addWorkFragment;

                case 1:
                        bundle.putInt("ClassID", classID);
                        bundle.putString("ClassName", myBundle.getString("ClassName"));
                        seeCommentFragment.setArguments(bundle);
                        return  seeCommentFragment;


                default:

                    bundle.putInt("ClassID", classID);
                    bundle.putString("ClassName", myBundle.getString("ClassName"));
                    addWorkFragment.setArguments(bundle);
                    return addWorkFragment;

            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add Work" ;
                case 1:
                    return "See Comment";
                default:
                    return "Add Comment";
            }
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MyProgressActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }




}
