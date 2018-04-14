/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyElucidationServer.java
 * PACKAGE      :  com.anssy.inter.service.server
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.server;

import com.anssy.inter.base.vo.NewsPageVo;
import com.anssy.inter.base.vo.NewsVo;
import com.anssy.venturebar.activity.vo.PvVo;
import com.anssy.venturebar.base.dao.NewsPolicyDao;
import com.anssy.venturebar.base.entity.NewsEntity;
import com.anssy.webcore.common.BaseConstants;
import com.anssy.webcore.common.DBHelper;
import com.anssy.webcore.common.DateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          资讯_政策解读
 */
@Service("newsPolicyServer")
public class NewsPolicyServer {

    private static Logger logger = Logger.getLogger(NewsPolicyServer.class);

    @Resource
    private NewsPolicyDao policyDao;

    /**
     * 查询政策解读信息
     */
    public List<NewsEntity> findList(NewsVo vo) {
        NewsPageVo pageVo = new NewsPageVo();
        pageVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        pageVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            pageVo.setSearch(vo.getSearch());
        }
        return policyDao.findList(pageVo);
    }

    /**
     * 查询政策的第一条数据
     */
    public Date findBeginUpdate() {
        return policyDao.findBeginUpdate();
    }

    /**
     * 发布政策信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseNews(NewsEntity entity) throws Exception {
        boolean flag = false;
        if (policyDao.insertPolicy(entity) > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 清除全部表数据
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteAllData() throws Exception {
        boolean flag = false;
        if (policyDao.deleteAllData() > 0) {
            if (policyDao.deleteAllData() > 0) {
                flag = true;
            }
        }
        return flag;
    }

    private static boolean isRun = false;

    /**
     * 从创业在线拉取政策数据 (定时)
     */
    public void loadPolcyFormStartupOnline() {

        // 防止重复同步
        if (isRun)return;
        isRun = true;

//        Date update_datetime = findBeginUpdate();

        StringBuffer sqlSb = new StringBuffer("select id,title,tb_instlink,update_time from app_zczx"); // SQL语句
//        if (update_datetime != null) {
//            String updateTime = DateTimeUtil.getFormatTime(update_datetime);
//            sqlSb.append(" where update_time > '")
//                    .append(updateTime)
//                    .append("' order by update_time"); // SQL语句
//            logger.error("\n=============\nupdateTime : " + updateTime + "\n================");
//        }
        db = new DBHelper(sqlSb.toString());//创建DBHelper对象

        try {
            ret = db.pst.executeQuery();//执行语句，得到结果集
            // 清除全部表数据
             deleteAllData();
            while (ret.next()) {
                String id = ret.getString(1);
                String title = ret.getString(2);
                String url = ret.getString(3);
                String date = ret.getString(4);
                logger.error("title : " + title + "\nurl : " + url + "\ndate : " + date);
                if (title != null && url != null && date != null) {
                    NewsEntity entity = new NewsEntity();
                    entity.setTitle(title);
                    entity.setUrl(url);
                    entity.setUpdateTime(DateTimeUtil.getFormatDate(date));
                    releaseNews(entity);
                }
            }
            //显示数据
            ret.close();
            db.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("创业在线数据访问异常啦!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据储存异常啦!");
        } finally {
            db.close();//关闭连接
            isRun = false;
            db = null;
            ret = null;
        }
    }

    DBHelper db = null;
    ResultSet ret = null;
}