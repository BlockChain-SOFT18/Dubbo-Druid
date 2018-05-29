package buaa.jj.accountservice.log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/text; charset=utf-8");
        OutputStream out = resp.getOutputStream();
        String url = req.getPathInfo();
        if (url == null || (!url.equals("/log") && !url.equals("/dubbo-log") && !url.equals("/mybatis-log"))) {
            url = "/log";
        }
        StringBuilder file = new StringBuilder(url);
        file.deleteCharAt(0);
        FileInputStream in = new FileInputStream(System.getProperty("logPath") + "/" + file + ".log");
        while (in.available() != 0) {
            out.write(in.read());
        }
    }
}
