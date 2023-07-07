package com.hitsz.controller;

import com.alibaba.fastjson.JSONArray;
import com.hitsz.constant.OperateConstant;
import com.hitsz.pojo.SqlPair;
import com.hitsz.service.QueryService;
import com.hitsz.service.UpdateService;
import com.hitsz.service.UrlSqlInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用控制器，拦截所有用户调用api接口的请求
 */
@RestController
@Slf4j
public class ApiController {

    @Autowired
    private UrlSqlInfoService urlSqlInfoService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private UpdateService updateService;

    /**
     * 用户调用任意api会定位到该方法，执行相应查询/更新逻辑
     * @param request 前端发过来的http请求
     * @param operate 包含在路径中的字符串，用于表示是生成csv/xlsx文件，或是将查询到的数据返回给前端
     * @return 返回给前端的数据
     */
    @RequestMapping("/{operate}/api/**")
    public Object execute(HttpServletRequest request, @PathVariable String operate) {
        long begin = System.currentTimeMillis();

        // 前端发过来一个api接口的调用，解析其url、method
        String url = request.getRequestURL().toString().split("api")[1];
        String method = request.getMethod();
        log.info("接收到请求 url: {} 请求类型：{}", url, method);

        // 根据url、method，查询对应的sql语句和数据库id
        SqlPair sqlPair = urlSqlInfoService.getSql(url, method);
        String sql;
        Integer dbId;
        if (sqlPair == null || (sql = sqlPair.getUserSql()) == null || (dbId = sqlPair.getDbId()) == null) {
            return "根据url和method查询sql出现异常！";
        }

        long end;
        // 执行sql
        if (!operate.equals(OperateConstant.UPDATE)) { // 查询数据或者导出文件
            JSONArray res = queryService.query(sql, dbId, operate);
            if (res == null) {
                return "查询结果为空!";
            }
            end = System.currentTimeMillis();
            log.info("api调用成功！耗时：{}毫秒", (end - begin));

            // TODO 将结果渲染到前端页面

            return res;
        } else {
            int updateRow = updateService.update(sql, dbId);
            if (updateRow < 0) {
                return "更新过程出现异常！" + updateRow;
            }
            return "api调用成功！更新行数：" + updateRow;
        }
    }

}
