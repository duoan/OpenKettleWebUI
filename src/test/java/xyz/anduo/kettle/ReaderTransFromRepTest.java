package xyz.anduo.kettle;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleEOFException;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class ReaderTransFromRepTest {
  private static String tranName = "第一课作业";// 传输名称

  public static void main(String[] args) {
    Trans trans = null;
    try {
      // 初始化
      KettleEnvironment.init();
      // 资源库元对象
      KettleFileRepositoryMeta repMeta =
          new KettleFileRepositoryMeta("", "", "数据采集", "file:///D:/kettle/data");
      // 文件形式的资源库
      KettleFileRepository rep = new KettleFileRepository();
      rep.init(repMeta);
      // 转换对象
      if (tranName != null && !"".equals(tranName)) {
        TransMeta transMeta = rep.loadTransformation(rep.getTransformationID(tranName, null), null);
        // 转换
        trans = new Trans(transMeta);
        // 执行转换
        trans.execute(null);
        // 等待转换执行结束
        trans.waitUntilFinished();
        // 抛出异常
        if (trans.getErrors() > 0) {
          throw new Exception("传输过程中发生异常");
        }
      } else {
        throw new KettleEOFException("传输名为空!");
      }
    } catch (Exception e) {
      if (trans != null) {
        trans.stopAll();
      }
      e.printStackTrace();
    }
  }

}
