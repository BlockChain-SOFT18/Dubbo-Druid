package buaa.jj.accountservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

public class Log4jErrorPrintStream extends PrintStream {

    private Logger log = LogManager.getLogger("logger");
    private static Log4jErrorPrintStream printStream = new Log4jErrorPrintStream(System.err);

    private Log4jErrorPrintStream(OutputStream out) {
        super(out);
    }

    public static void redirectSystemOut() {
        System.setErr(printStream);
    }

    public void print(boolean b) {
        println(b);
    }

    public void print(char c) {
        println(c);
    }

    public void print(char[] s) {
        println(s);
    }

    public void print(double d) {
        println(d);
    }

    public void print(float f) {
        println(f);
    }

    public void print(int i) {
        println(i);
    }

    public void print(long l) {
        println(l);
    }

    public void print(Object obj) {
        println(obj);
    }

    public void print(String s) {
        println(s);
    }

    public void println(boolean x) {
        log.error(Boolean.valueOf(x));
    }

    public void println(char x) {
        log.error(Character.valueOf(x));
    }

    public void println(char[] x) {
        log.error(x == null ? null : new String(x));
    }

    public void println(double x) {
        log.error(Double.valueOf(x));
    }

    public void println(float x) {
        log.error(Float.valueOf(x));
    }

    public void println(int x) {
        log.error(Integer.valueOf(x));
    }

    public void println(long x) {
        log.error(x);
    }

    public void println(Object x) {
        log.error(x);
    }

    public void println(String x) {
        log.error(x);
    }
}
