package xyz.anduo.kettle.utils;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleEOFException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * 
 * @ClassName: KettleUtils
 * @Description: Kettle工具类
 * @author anduo
 * @date 2015年1月22日 上午10:27:14
 *
 */
public class KettleUtils {

  /**
   * @title initKettleFileRepository
   * @description 初始化一个kettle文件资源库
   * @param dir
   * @return KettleFileRepository
   * @throws KettleException
   */
  public static KettleFileRepository initFileRepository(String dir) throws KettleException {
    KettleFileRepository repository = null;
    // 初始化
    KettleEnvironment.init();
    // 资源库元对象
    KettleFileRepositoryMeta repMeta =
        new KettleFileRepositoryMeta("", "", "数据采集", "file:///" + dir);
    // 文件形式的资源库
    repository = new KettleFileRepository();
    repository.init(repMeta);
    return repository;
  }


  public static KettleDatabaseRepository initDatabaseRepository(DbRepParams params)
      throws KettleException {
    KettleDatabaseRepository repository = null;
    // 初始化
    KettleEnvironment.init();
    DatabaseMeta databaseMeta =
        new DatabaseMeta(params.getName(), params.getType(), params.getAccess(), params.getHost(),
            params.getDb(), params.getPort(), params.getUser(), params.getPass());
    KettleDatabaseRepositoryMeta repositoryMeta = new KettleDatabaseRepositoryMeta();
    repositoryMeta.setConnection(databaseMeta);
    repository = new KettleDatabaseRepository();
    repository.init(repositoryMeta);
    repository.connect(params.getUsername(), params.getPassword());

    RepositoryDirectoryInterface dir = new RepositoryDirectory();
    dir.setObjectId(repository.getRootDirectoryID());
    return repository;

  }

  public static Trans executeTrans(String transname, Object rep) throws Exception {
    return executeTrans(transname, rep, true);
  }

  /**
   * @Title: executeTrans
   * @Description: 执行转换
   * @param transname
   * @param rep
   * @throws Exception
   */
  public static Trans executeTrans(String transname, Object rep, boolean isWaitUntilFinished)
      throws Exception {
    // 转换对象
    Trans trans = null;
    if (transname != null && !"".equals(transname)) {
      TransMeta transMeta = null;
      if (rep instanceof KettleFileRepository) {
        KettleFileRepository repository = (KettleFileRepository) rep;
        transMeta =
            repository.loadTransformation(repository.getTransformationID(transname, null), null);
      } else if (rep instanceof KettleDatabaseRepository) {
        KettleDatabaseRepository repository = (KettleDatabaseRepository) rep;
        RepositoryDirectoryInterface dir = new RepositoryDirectory();
        dir.setObjectId(repository.getRootDirectoryID());
        transMeta =
            repository.loadTransformation(repository.getTransformationID(transname, dir), null);
      }
      // 转换
      trans = new Trans(transMeta);
      // 执行转换
      trans.execute(null);

      // 等待转换执行结束
      if (isWaitUntilFinished) {
        trans.waitUntilFinished();
      }
      // 抛出异常
      if (trans.getErrors() > 0) {
        trans.stopAll();
        throw new Exception("传输过程中发生异常");
      }
    } else {
      throw new KettleEOFException("传输名为空!");
    }
    return trans;
  }


}
