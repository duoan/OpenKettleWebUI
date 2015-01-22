package xyz.anduo.kettle.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.anduo.kettle.utils.KettleUtils;

public class KettleServlet extends HttpServlet {

  private static final long serialVersionUID = 6804316487925782884L;

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset=UTF-8");
    PrintWriter out = resp.getWriter();
    try {
      processTrans(req, out);
      req.setAttribute("flag", "s");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("flag", "f");
    }
    req.getRequestDispatcher("/").forward(req, resp);
  }

  /**
   * 
   * @Title: executeKettleTrans
   * @Description: kettle执行处理trans
   * @param dir
   * @param transname
   * @return void
   * @throws Exception
   */
  private void processTrans(HttpServletRequest req, final PrintWriter out) throws Exception {
    String transname = req.getParameter("transname");
    transname = transname.substring(0, transname.indexOf("."));
    String dir = req.getParameter("dir");
    KettleUtils.executeTrans(transname, KettleUtils.initFileRepository(dir));
  }

}
