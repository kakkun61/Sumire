package kakkun61.sumire;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class GlobalData {
    private static SharedPreferences prefs;
    public static final int DEFAULT_DAY_LESSONS_COUNT = 5;
    private static int dayLessonsCount;
    public static final int SUNDAY    = 0;
    public static final int MONDAY    = 1;
    public static final int TUESDAY   = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY  = 4;
    public static final int FRIDAY    = 5;
    public static final int SATURDAY  = 6;
    /**
     * どの曜日に授業があるかを表す。日・月・火…の順。
     */
    private static boolean[] businessDay;
    /**
     * 授業を表す配列。7×{@link #dayLessonsCount} の大きさ。
     */
    private static Lesson[][] lessons;
    /**
     * 表示している曜日
     */
    private static int showingDay;

    static {
        dayLessonsCount = DEFAULT_DAY_LESSONS_COUNT;
        businessDay = new boolean[] { true, true, true, true, true, true, false };
        lessons = new Lesson[7][];
        lessons[0] = new Lesson[] {
                new Lesson("制御工学", "真田", "B11 324", 1),
                null,
                new Lesson("パワーエレクトロニクス", "森本", "B11 324", 1),
                null,
                null };
        showingDay = MONDAY;
    }

    /**
     * インスタンスは作らせない
     */
    private GlobalData() {}

    public static void createDefaultSharedPreferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 
     * @param dayLessonsCount must be >= 1
     */
    public static void setDayLessonsCount(int dayLessonsCount) {
    	GlobalData.dayLessonsCount = dayLessonsCount;
    }

    public static int getDayLessonsCount() {
        return dayLessonsCount;
    }

    public static Lesson[][] getLessons() {
        return lessons;
    }

    public static void setLessons(Lesson[][] lessons) {
        GlobalData.lessons = lessons;
    }

    /**
     * 日・月・火の順
     * @return 
     */
    public static boolean[] getBusinessDay() {
        return businessDay;
    }

    /**
     * 日・月・火の順
     * @param businessDay
     */
    public static void setBusinessDay(boolean[] businessDay) {
        GlobalData.businessDay = businessDay;
    }

    public static int getShowingDay() {
        return showingDay;
    }

    public static void setShowingDay(int showingDay) {
        GlobalData.showingDay = showingDay;
    }
}
