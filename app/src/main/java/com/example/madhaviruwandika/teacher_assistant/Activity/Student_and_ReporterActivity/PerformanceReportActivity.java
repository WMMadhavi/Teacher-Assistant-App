package com.example.madhaviruwandika.teacher_assistant.Activity.Student_and_ReporterActivity;

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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madhaviruwandika.teacher_assistant.Controller.ClassController;
import com.example.madhaviruwandika.teacher_assistant.Controller.CommunicationController;
import com.example.madhaviruwandika.teacher_assistant.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

public class PerformanceReportActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    Bundle myBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_performance_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);

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
            View rootView = inflater.inflate(R.layout.content_performance_report, container, false);
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
            Bundle bundle = getIntent().getExtras();
            bundle.putInt("StudentID",myBundle.getInt("StudentID"));
            bundle.putString("name", myBundle.getString("name"));
            bundle.putInt(" ",myBundle.getInt("ClassID"));

            PerfomanceFragment perfomanceFragment = new PerfomanceFragment();
            AttendenceAndPamentFragment attendenceAndPamentFragment = new AttendenceAndPamentFragment();
            Perfomance_2_Fragment perfomance_2_fragment = new Perfomance_2_Fragment();

            switch (position) {
                case 0:
                    perfomanceFragment.setArguments(bundle);
                    return perfomanceFragment;

                case 1:
                    perfomance_2_fragment.setArguments(bundle);
                    return perfomance_2_fragment;

                case 2:
                    attendenceAndPamentFragment.setArguments(bundle);
                    return attendenceAndPamentFragment;

                default:
                    perfomanceFragment.setArguments(bundle);
                    return perfomanceFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Inclass Performance" ;
                case 1:
                    return "Inclass Performance 2" ;
                case 2:
                    return "Payment and Attendence";
                default:
                    return "Inclass Performance";
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reporter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.sendReport:

                CommunicationController communicationController = new CommunicationController(this);
                ClassController classController = new ClassController(this);
                PerfomanceFragment perfomanceFragment = (PerfomanceFragment)mSectionsPagerAdapter.instantiateItem(mViewPager,0);
                RelativeLayout barChart = perfomanceFragment.chartContainer;
                Perfomance_2_Fragment perfomance_2_fragment = (Perfomance_2_Fragment)mSectionsPagerAdapter.instantiateItem(mViewPager,1);
                RelativeLayout lineChart = perfomance_2_fragment.chartContainer;
                AttendenceAndPamentFragment attendenceAndPamentFragment = (AttendenceAndPamentFragment)mSectionsPagerAdapter.instantiateItem(mViewPager,2);
                TableLayout tableLayout = attendenceAndPamentFragment.logsTableLayout;
                Intent emailIntent = communicationController.sendEmailWithAttacthment(classController.getTutionClassByID(myBundle.getInt("ClassID")).get("ClassName"), myBundle.getInt("ClassID"),myBundle.getInt("StudentID"), "", PerformanceReportActivity.this,barChart,lineChart);
                Intent choser = Intent.createChooser(emailIntent, "......Sending Email.....");
                PerformanceReportActivity.this.startActivity(choser);
                break;
            case android.R.id.home:
                Intent myIntent = new Intent(getApplicationContext(), SeePerfomanceActivity.class);
                startActivityForResult(myIntent, 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }


        return true;


    }



}
