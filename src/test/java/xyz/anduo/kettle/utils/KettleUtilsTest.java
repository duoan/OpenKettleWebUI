package xyz.anduo.kettle.utils;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.trans.Trans;

public class KettleUtilsTest {

  private KettleFileRepository rep;

  @Before
  public void testInitFileRepository() {
    String dir = "D:/kettle/data";
    try {
      rep = KettleUtils.initFileRepository(dir);
    } catch (KettleException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testExecuteTrans() {
    try {
      Trans trans = KettleUtils.executeTrans("第一课作业", rep, false);
      while (trans.isRunning()) {
        System.out.println(trans.getStatus());
        Thread.sleep(1000);
      }
      System.err.println(trans.getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
