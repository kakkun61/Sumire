package kakkun61.sumire;

import kakkun61.sumire.util.SumireLog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GlobalData {
    private static SharedPreferences prefs;
    public static final int DEFAULT_DAY_LESSONS_COUNT = 5;
    private static int lessonsADayCount;
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
     * 授業を表す配列。7×{@link #lessonsADayCount} の大きさ。
     */
    private static Lesson[][] lessons;
    /**
     * 表示している曜日
     */
    private static int showenDay;

    static {
        // 仮
        lessonsADayCount = DEFAULT_DAY_LESSONS_COUNT;
        businessDay = new boolean[] { true, true, true, true, true, true, false };
        lessons = new Lesson[7][];
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

    public static void putLesson(int day, int hour, Lesson lesson) {
        lessons[day][hour] = lesson;
        SharedPreferences.Editor edit = prefs.edit();
        if (lesson != null) {
            edit.putString("lessons" + day + hour + "name", lesson.name);
            edit.putString("lessons" + day + hour + "teacher", lesson.teacher);
            edit.putString("lessons" + day + hour + "room", lesson.room);
            edit.putInt("lessons" + day + hour + "series", lesson.series);
        } else {
            edit.remove("lessons" + day + hour + "name");
            edit.remove("lessons" + day + hour + "teacher");
            edit.remove("lessons" + day + hour + "room");
            edit.remove("lessons" + day + hour + "series");
        }
        edit.commit();
        SumireLog.d("save: {(" + day + ", " + hour + "), " + lesson + "}");
    }

    /**
     * 日・月・火…の順
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

    public static int getShowenDay() {
        return showenDay;
    }

    public static void setShowenDay(int showingDay) {
        GlobalData.showenDay = showingDay;
    }

    public static void setLessonsADayCount(int dayLessonsCount) {
        GlobalData.lessonsADayCount = dayLessonsCount;
        prefs.edit().putInt("dayLessonsCount", dayLessonsCount).commit();
        
    }

    public static int getLessonsADayCount() {
        return lessonsADayCount;
    }

    /**
     * Call after {@link GlobalData#createDefaultSharedPreferences(Context)}.
     */
    public static void loadAllData() {
        lessonsADayCount = prefs.getInt("dayLessonsCount", DEFAULT_DAY_LESSONS_COUNT);
        showenDay = prefs.getInt("showingDay", MONDAY);

        // XXX ?? 2011.02.13
//        for (int day=0; day<7; day++) {
//            if (day == SUNDAY || day == SATURDAY)
//                prefs.getBoolean("businessDay" + day, false);
//            else
//                prefs.getBoolean("businessDay" + day, true);
//        }

        lessons = new Lesson[7][];
        for (int day=0; day<7; day++) {
            lessons[day] = new Lesson[lessonsADayCount];
            for (int hour=0; hour<lessonsADayCount; hour++) {
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

    public static class Debug {
        private Debug(){}

        public static void printLessons() {
            SumireLog.d("Lessons: {");
            for (int day=0; day<7; day++) {
                StringBuilder sb = new StringBuilder();
                switch (day) {
                case SUNDAY:
                    sb.append("\tSunday");
                    break;
                case MONDAY:
                    sb.append("\tMonday");
                    break;
                case TUESDAY:
                    sb.append("\tTuesday");
                    break;
                case WEDNESDAY:
                    sb.append("\tWednesday");
                    break;
                case THURSDAY:
                    sb.append("\tThursday");
                    break;
                case FRIDAY:
                    sb.append("\tFriday");
                    break;
                case SATURDAY:
                    sb.append("\tSaturday");
                    break;
                }
                if (day == showenDay)
                    sb.append(" *");
                sb.append(" {");
                SumireLog.d(sb.toString());
                for (int hour=0; hour<lessonsADayCount; hour++) {
                    SumireLog.d("\t\t" + lessons[day][hour]);
                }
                SumireLog.d("\t}");
            }
            SumireLog.d("}");
        }
    }
}
