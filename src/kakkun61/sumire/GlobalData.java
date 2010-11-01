package kakkun61.sumire;

import java.io.IOException;
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

    public static Lesson getLesson(int day, int hour) {
        return lessons[day][hour];
    }

    public static void setLesson(int day, int hour, Lesson lesson) {
        lessons[day][hour] = lesson;
        SharedPreferences.Editor edit = prefs.edit();
        if (lessons[day][hour] != null) {
            edit.putString("lessons" + day + hour + "name", lessons[day][hour].name);
            edit.putString("lessons" + day + hour + "teacher", lessons[day][hour].teacher);
            edit.putString("lessons" + day + hour + "room", lessons[day][hour].room);
            edit.putInt("lessons" + day + hour + "series", lessons[day][hour].series);
        }
        edit.commit();
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
        SharedPreferences.Editor edit = prefs.edit();
        for (int i=0; i<7; i++) {
            edit.putBoolean("businessDay" + i, businessDay[i]);
        }
    }

    public static int getShowingDay() {
        return showingDay;
    }

    public static void setShowingDay(int showingDay) {
        GlobalData.showingDay = showingDay;
    }

    public static void setDayLessonsCount(int dayLessonsCount) {
        GlobalData.dayLessonsCount = dayLessonsCount;
        prefs.edit().putInt("dayLessonsCount", dayLessonsCount).commit();
        
    }

    public static int getDayLessonsCount() {
        return dayLessonsCount;
    }

    /**
     * Call after {@link GlobalData#createDefaultSharedPreferences(Context)}.
     */
    public static void loadAllData() {
        dayLessonsCount = prefs.getInt("dayLessonsCount", DEFAULT_DAY_LESSONS_COUNT);
        showingDay = prefs.getInt("showingDay", MONDAY);

        for (int day=0; day<7; day++) {
            if (day == SUNDAY || day == SATURDAY)
                prefs.getBoolean("businessDay" + day, false);
            else
                prefs.getBoolean("businessDay" + day, true);
        }

        lessons = new Lesson[7][];
        for (int day=0; day<7; day++) {
            lessons[day] = new Lesson[dayLessonsCount];
            for (int hour=0; hour<dayLessonsCount; hour++) {
                String name = prefs.getString("lessons" + day + hour + "name", null);
                if (name == null)
                    continue;
                lessons[day][hour] = new Lesson(
                        name, 
                        prefs.getString("lessons" + day + hour + "teacher", null),
                        prefs.getString("lessons" + day + hour + "room", null),
                        prefs.getInt("lessons" + day + hour + "series", 0)
                );
            }
        }
    }
}
