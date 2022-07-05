package com.zrxedu.mhl.service;

import com.zrxedu.mhl.dao.DiningTableDAO;
import com.zrxedu.mhl.dommain.DiningTable;

import java.util.List;

@SuppressWarnings({"all"})
public class DiningTableService {
    DiningTableDAO diningTableDAO = new DiningTableDAO();

    public List<DiningTable> tableStates() {
        String sql = "select id,state from diningtable;";
        return diningTableDAO.queryMulti(sql, DiningTable.class);
    }

    public int reserve(String id, String orderName, String orderTel, String orderTime) {
        String sql = "update diningtable set state=?,orderName=?,orderTel=?,orderTime=? where id=?;";
        return diningTableDAO.up(sql, "已预定", orderName, orderTel, orderTime, id);
    }

    //查找餐桌是否存在
    public DiningTable tableState(String id) {
        String sql = "select * from diningtable where id=?;";
        return diningTableDAO.querySingle(sql, DiningTable.class, id);
    }

    public boolean tableStateEating(int diningTableId) {
        String sql = "update diningtable set state='正在就餐' where id=?;";
        int row = diningTableDAO.up(sql, diningTableId);
        return row > 0;
    }

    //通过餐桌号修改餐桌信息
    public boolean updateTableState(int diningTableId) {
        String sql = "update diningtable set state='空',orderName='',orderTel='',orderTime='2000-01-01 00:00:00' where id=?;";
        int row = diningTableDAO.up(sql, diningTableId);
        return row > 0;
    }
}
