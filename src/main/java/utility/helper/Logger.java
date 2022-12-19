package utility.helper;

import org.slf4j.LoggerFactory;

public final class Logger {

    public Logger(String className) {
        if(className.isEmpty()){
            syntheticClassName = "UnknownClassName";
        }
        else {
            syntheticClassName =className;
        }
        slf4jLogger = LoggerFactory.getLogger(syntheticClassName);
    }
    private static org.slf4j.Logger slf4jLogger;
    private static String syntheticClassName;

    public static void trace(String message, Exception exception) {
        logStuff(message, LogLevel.TRACE, exception);
    }

    public static void debug(String message, Exception exception) {
        logStuff(message, LogLevel.DEBUG, exception);
    }

    public static void info(String message, Exception exception) {
        logStuff(message, LogLevel.INFO, exception);
    }

    public static void warn(String message, Exception exception) {
        logStuff(message, LogLevel.WARN, exception);
    }

    public static void error(String message, Exception exception) {
        logStuff(message, LogLevel.ERROR, exception);
    }

    public static void trace(String message) {
        logStuff(message, LogLevel.TRACE, null);
    }

    public static void debug(String message) {
        logStuff(message, LogLevel.DEBUG, null);
    }

    public static void info(String message) {
        logStuff(message, LogLevel.INFO, null);
    }

    public static void warn(String message) {
        logStuff(message, LogLevel.WARN, null);
    }

    public static void error(String message) {
        logStuff(message, LogLevel.ERROR, null);
    }

    private static void printToConsole(String message) {
        System.out.print(message);
    }

    private static void logStuff(String message, LogLevel logLevel, Exception exception) {
        String consoleMessage = getLogMessage(message, logLevel, exception);
        switch (logLevel){
            case ERROR :slf4jLogger.error(message);
            break;
            case TRACE :slf4jLogger.trace(message);
                break;
            case WARN :slf4jLogger.warn(message);
                break;
            case INFO :slf4jLogger.info(message);
                break;
            case DEBUG :slf4jLogger.debug(message);
        }

        printToConsole(consoleMessage);
    }

    private static String getLogMessage(String message, LogLevel logLevel, Exception exception) {
        return "["+logLevel+"]"+ "{"+MiscHelpers.currentDateTime("MM/dd/yyyy HH:mm:ss")+"} from "+ "'"+syntheticClassName+"' class. "+message+ " + appendExceptionInfo("+ exception+")  "+"\n";
    }

    private String appendExceptionInfo(Exception exception) {
        if (exception == null) {
            return "";
        } else {
            return "Exception message: "+"{"+exception.getMessage()+"}"+"\n Exception stack trace: "+"{"+exception.getStackTrace().toString()+"}";
        }
    }
}
