package com.zrxedu.mhl.service;

import com.zrxedu.mhl.dao.BillDAO;
import com.zrxedu.mhl.dao.MultiTableBeanDAO;
import com.zrxedu.mhl.dommain.Bill;
import com.zrxedu.mhl.dommain.MultiTableBean;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"all"})
public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService = new DiningTableService();
    private MultiTableBeanDAO multiTableBeanDAO = new MultiTableBeanDAO();

    //该方法给点餐模块使用，形成订单
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        String billId = UUID.randomUUID().toString();
        String sql = "insert into bill values(null,?,?,?,?,?,now(),'未结账');";
        int row = billDAO.up(sql, billId, menuId, nums, menuService.obtainMenu(menuId).getPrice() * nums, diningTableId);
        if (row <= 0) {
            return false;
        }
        return diningTableService.tableStateEating(diningTableId);
    }

    public List<Bill> listBill(int diningTableId) {
        String sql = "select * from bill where diningTableId=?;";
        return billDAO.queryMulti(sql, Bill.class, diningTableId);
    }

    //返回全部账单
    public List<Bill> listBills() {
        String sql = "select * from bill;";
        return billDAO.queryMulti(sql, Bill.class);
    }

    public List<MultiTableBean> listBillss() {
        String sql = "select bill.*,name from bill,menu where bill.menuId=menu.id;";
        return multiTableBeanDAO.queryMulti(sql, MultiTableBean.class);
    }

    //通过餐桌号查询是否还有未结账订单
    public boolean PayBillByDiningTableId(int diningTableId) {
        String sql = "select * from bill where diningTableId=? and start='未结账' limit 0,1;";
        Bill bill = billDAO.querySingle(sql, Bill.class, diningTableId);
        return bill != null;
    }

    //通过餐桌号修改订单状态[完成支付]
    public boolean payBill(int diningTableId, String payment) {
        String sql = "update bill set start=? where diningTableId=? and start='未结账';";
        int row = billDAO.up(sql, payment, diningTableId);
        if (row <= 0) {
            return false;
        }
        if (!diningTableService.updateTableState(diningTableId)) {
            return false;
        }
        return true;
    }
}
