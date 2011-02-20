package kakkun61.sumire;

import kakkun61.sumire.util.SumireLog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class GlobalData {
    private static SharedPreferences prefs;
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

    /**
     * 授業開始時刻[n時間目][時/分]
     */
    private static int[][] startTime;

    /**
     * 授業終了時刻[n時間目][時/分]
     */
    private static int[][] endTime;

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

    public static int[] getStartTime(int period) {
        return startTime[period];
    }

    public static void setStartTime(int period, int hour, int minute) {
        startTime[period][0] = hour;
        startTime[period][1] = minute;
        Editor edit = prefs.edit();
        edit.putInt("startTime" + period + 0, hour);
        edit.putInt("startTime" + period + 1, minute);
        edit.commit();
    }

    public static int[] getEndTime(int period) {
        return endTime[period];
    }

    public static void setEndTime(int period, int hour, int minute) {
        endTime[period][0] = hour;
        endTime[period][1] = minute;
        Editor edit = prefs.edit();
        edit.putInt("endTime" + period + 0, hour);
        edit.putInt("endTime" + period + 1, minute);
        edit.commit();
    }

    /**
     * Call after {@link GlobalData#createDefaultSharedPreferences(Context)}.
     */
    public static void loadAllData() {
        lessonsADayCount = prefs.getInt("dayLessonsCount", 5);
        showenDay = prefs.getInt("showingDay", MONDAY);

        lessons = new Lesson[7][];
        for (int day=0; day<7; day++) {
            lessons[day] = new Lesson[lessonsADayCount];
            for (int period=0; period<lessonsADayCount; period++) {
                String name = prefs.getString("lessons" + day + period + "name", null);
                if (name == null)
                    continue;
                lessons[day][period] = new Lesson(
                        name, 
                        prefs.getString("lessons" + day + period + "teacher", null),
                        prefs.getString("lessons" + day + period + "room", null),
                        prefs.getInt("lessons" + day + period + "series", 0)
                );
            }
        }

        businessDay = new boolean[7];
        for (int day = 0; day < businessDay.length; day++) {
            if (SUNDAY < day && day < SATURDAY) {
                businessDay[day] = prefs.getBoolean("businesDay" + day, true);
            } else {
                businessDay[day] = prefs.getBoolean("businesDay" + day, false);
            }
        }

        startTime = new int[lessonsADayCount][2];
        endTime   = new int[lessonsADayCount][2];
        for (int hand = 0; hand < 2; hand++) {
            for (int period = 0; period < lessonsADayCount; period++) {
                startTime[period][hand] = prefs.getInt("startTime" + period + hand, 0);
                endTime[period][hand]   = prefs.getInt("endTime" + period + hand, 0);
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
