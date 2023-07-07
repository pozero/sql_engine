package com.hitsz.service;

import com.hitsz.dao.UpdateDao;
import com.hitsz.pojo.UserDbInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UpdateServiceImpl extends BaseService implements UpdateService {

    @Autowired
    private UpdateDao updateDao;

    // TODO 添加并发逻辑
    @Override
    public int update(String sql, Integer dbId) {
        UserDbInfo dbInfo = getDbInfo(dbId);
        if (dbInfo == null) return -1;

        int updateRow = -1;
        try {
            updateRow = updateDao.doUpdate(sql, dbInfo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return updateRow;
    }
}
