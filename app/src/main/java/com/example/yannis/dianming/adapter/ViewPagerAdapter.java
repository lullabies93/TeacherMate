package com.example.yannis.dianming.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.Course2;
import com.example.yannis.dianming.fragments.CourseFragment;
import com.example.yannis.dianming.fragments.SignDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannis on 2017/1/10.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> labels;
    public List<Course> courses;

    private FragmentManager fragmentManager;

    private ArrayList<Course2> mondayCourses, tuesdayCourses, wednesdayCourses, thursdayCourse,
            fridayCourse, saturdayCourse, sundayCourse;

    public ViewPagerAdapter(FragmentManager fm, List<String> labels, List<Course> courses) {
        super(fm);
        this.labels = labels;
        this.courses = courses;
        fragmentManager = fm;

        mondayCourses = new ArrayList<>();
        tuesdayCourses = new ArrayList<>();
        wednesdayCourses = new ArrayList<>();
        thursdayCourse = new ArrayList<>();
        fridayCourse = new ArrayList<>();
        saturdayCourse = new ArrayList<>();
        sundayCourse = new ArrayList<>();

        analyzeData(courses);
    }

    private void analyzeData(List<Course> courses) {
        Course2 course2 = null;
        for (Course course : courses) { //course_name,course_id,weekday,section,classroom
            List<String> weekdays = course.getWeekday();
            List<String> sections = course.getSection();
            List<String> classroom = course.getClassroom();
            for (int w = 0; w < weekdays.size(); w++) {
                int day = Integer.parseInt(weekdays.get(w));
                course2 = new Course2(sections.get(w), classroom.get(w), day, course.getCourse_id(), course
                        .getCourse_name());
                switch (day) {
                    case 0:

                        mondayCourses.add(course2);
                        break;
                    case 1:
                        tuesdayCourses.add(course2);
                        break;
                    case 2:
                        wednesdayCourses.add(course2);
                        break;
                    case 3:
                        thursdayCourse.add(course2);
                        break;
                    case 4:
                        fridayCourse.add(course2);
                        break;
                    case 5:
                        saturdayCourse.add(course2);
                        break;
                    case 6:
                        sundayCourse.add(course2);
                        break;
                }

            }
        }
    }

    @Override
    public BaseFragment getItem(int position) {

        CourseFragment courseFragment = new CourseFragment();
        courseFragment.setLabel(labels.get(position).split("@")[0]);
        courseFragment.setWeekday(Integer.parseInt(labels.get(position).split("@")[1]));
        switch (courseFragment.getWeekday()) {
            case 0:
                courseFragment.setCourses(mondayCourses);
                break;
            case 1:
                courseFragment.setCourses(tuesdayCourses);
                break;
            case 2:
                courseFragment.setCourses(wednesdayCourses);
                break;
            case 3:
                courseFragment.setCourses(thursdayCourse);
                break;
            case 4:
                courseFragment.setCourses(fridayCourse);
                break;
            case 5:
                courseFragment.setCourses(saturdayCourse);
                break;
            case 6:
                courseFragment.setCourses(sundayCourse);
                break;
        }
        return courseFragment;

    }

    @Override
    public int getCount() {
        return labels.size();
    }

    public String getPageTitle(int position) {

        return labels.get(position).split("@")[0];

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = getItem(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }
}
