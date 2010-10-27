package kakkun61.sumire.util;

import android.util.Log;

public final class SumireLog
{
    public static final int ASSERT  = Log.ASSERT;
    public static final int DEBUG   = Log.DEBUG;
    public static final int ERROR   = Log.ERROR;
    public static final int INFO    = Log.INFO;
    public static final int VERBOSE = Log.VERBOSE;
    public static final int WARN    = Log.WARN;

    private static final String tag = "Sumire";

    private static boolean active = true;

    public static int v( String msg, Throwable tr )
    {
        if( active )
            return Log.v( tag, msg, tr );
        return 0;
    }

    public static int v(String tag, String msg)
    {
        if( active )
            return Log.v( tag, msg );
        return 0;
    }

    public static int d( String msg, Throwable tr )
    {
        if( active )
            return Log.d( tag, msg, tr );
        return 0;
    }

    public static int d( String msg )
    {
        if( active )
            return Log.d( tag, msg );
        return 0;
    }

    public static int i( String msg, Throwable tr )
    {
        if( active )
            return Log.i( tag, msg, tr );
        return 0;
    }

    public static int i( String tag, String msg )
    {
        if( active )
            return Log.i( tag, msg );
        return 0;
    }

    public static int w(String tag, String msg)
    {
        if( active )
            return Log.w( tag, msg );
        return 0;
    }

    public static int w( Throwable tr )
    {
        if( active )
            return Log.w( tag, tr );
        return 0;
    }

    public static int w( String msg, Throwable tr )
    {
        if( active )
            return Log.w( tag, msg, tr );
        return 0;
    }

    public static int e( String msg )
    {
        if( active )
            return Log.e( tag, msg );
        return 0;
    }

    public static int e( String msg, Throwable tr )
    {
        if( active )
            return Log.e( tag, msg, tr );
        return 0;
    }

    public static String getStackTraceString( Throwable tr )
    {
        return Log.getStackTraceString( tr );
    }

    public static boolean isLoggable( int level )
    {
        return Log.isLoggable( tag, level );
    }

    public static int println( int priority, String msg )
    {
        return Log.println( priority, tag, msg );
    }
}
