/* пример: счетчик посещений или "кликов": FirstServlet.java */

package test.com;

import java.io.*;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstServlet extends HttpServlet {

  // счетчик подключений к сервлету
  private volatile int count;
  
  public void init() throws ServletException {
    super.init();
    count = 0;
  }
  
  public void doGet( HttpServletRequest req, HttpServletResponse res )
      throws ServletException, IOException {
    performTask(req, res);
  }

  public void doPost( HttpServletRequest req, HttpServletResponse res )
      throws ServletException, IOException {
    performTask(req, res);
  }

  public void performTask(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String title = "First Servlet";
    PrintWriter out = res.getWriter();
    res.setContentType("text/html; charset=Cp1251");
    out.println("<!DOCTYPE HTML><html lang=\"en\"><head><title>"
                 + title + "</title>"
                 + "</head><body><h3>This page is generated "
                 + "by FirstServlet<h3><h4>This is its "
                 + ++count
                 + " -th execution</h4></body></body>");
    out.close();
  }
}

