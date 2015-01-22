package xyz.anduo.kettle.servlet;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {


  /**
   */
  private static final long serialVersionUID = -2934459860540454213L;
  
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ServletConfig config = getServletConfig();
    // 通过ServletConfig对象获取配置参数:dir
    String transDirPath = config.getInitParameter("trans_dir_path");
    // 读取文件夹kettle的trans文件
    File dir = new File(transDirPath);
    if (!dir.exists()) {
      dir.mkdir();
    }
    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.contains(".ktr");
      }
    };
    File[] transFiles = dir.listFiles(filter);
    request.setAttribute("transFiles", transFiles);
    request.setAttribute("dir", transDirPath);
    request.getRequestDispatcher("/index.jsp").forward(request, response);
  }



  /**
   * Initialization of the servlet. <br>
   *
   * @throws ServletException if an error occurs
   */
  public void init() throws ServletException {

  }

}
