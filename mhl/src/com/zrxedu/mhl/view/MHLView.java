package com.zrxedu.mhl.view;

import com.zrxedu.mhl.dommain.*;
import com.zrxedu.mhl.service.BillService;
import com.zrxedu.mhl.service.DiningTableService;
import com.zrxedu.mhl.service.EmployeeService;
import com.zrxedu.mhl.service.MenuService;
import com.zrxedu.mhl.utils.Utility;

import java.util.List;

@SuppressWarnings({"all"})
public class MHLView {
    private boolean loop = true;
    private String Key = "";
    private EmployeeService employeeService = new EmployeeService();
    private DiningTableService diningTableService = new DiningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenuOne();
        System.out.println("退出满汉楼系统");
    }

    //满汉楼主菜单
    public void mainMenuOne() {
        while (loop) {
            System.out.println("==========满汉楼==========");
            System.out.println("\t\t1 登录满汉楼");
            System.out.println("\t\t2 退出满汉楼");
            System.out.print("请输入你的选择: ");
            Key = Utility.readString(1);
            switch (Key) {
                case "1":
                    System.out.print("请输入账号(员工号): ");
                    String empId = Utility.readString(50);
                    System.out.print("请输入密码: ");
                    String pwd = Utility.readString(32);
                    Employee employeeByEmpidAndPwd = employeeService.getEmployeeByEmpidAndPwd(empId, pwd);
                    if (employeeByEmpidAndPwd != null) {
                        System.out.println("=========登录成功(" + employeeByEmpidAndPwd.getName() + ")========");
                        mainMenuTwo();
                    } else {
                        System.out.println("登录失败请重试...");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("没有此选项，请重新输入");
                    break;
            }
        }
    }

    //满汉楼二级菜单
    public void mainMenuTwo() {
        while (loop) {
            System.out.println("\n===========满汉楼二级菜单==========");
            System.out.println("\t\t1 显示餐桌状态");
            System.out.println("\t\t2 预定餐桌");
            System.out.println("\t\t3 显示所以菜品");
            System.out.println("\t\t4 点餐服务");
            System.out.println("\t\t5 查看账单");
            System.out.println("\t\t6 结账");
            System.out.println("\t\t9 退出满汉楼");
            System.out.print("请输入你的选择: ");
            Key = Utility.readString(1);
            switch (Key) {
                case "1":
                    listDiningTable();
                    break;
                case "2":
                    orderMeal();
                    break;
                case "3":
                    listMenu();
                    break;
                case "4":
                    orderingService();
                    break;
                case "5":
                    MenuBill();
                    break;
                case "6":
                    CheckOut();
                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    System.out.println("没有此选项，请重新输入");
                    break;

            }
        }
    }

    //显示所以餐桌状态
    public void listDiningTable() {
        List<DiningTable> diningTables = diningTableService.tableStates();
        System.out.println("\t餐桌号" + "\t\t状态");
        for (DiningTable diningTable : diningTables) {
            System.out.println(diningTable);
        }
    }

    //预定餐桌
    public void orderMeal() {
        System.out.print("请输入你要预订的餐位: ");
        String id = Utility.readString(1);
        DiningTable diningTable = diningTableService.tableState(id);
        if (diningTable == null) {
            System.out.println("\n======该餐桌不存在,无法预定======\n");
            return;
        }
        if (diningTable.getState().equals("空")) {
            System.out.print("请输入你的姓名: ");
            String ordername = Utility.readString(50);
            System.out.print("请输入你的电话号码: ");
            String orderTel = Utility.readString(20);
            System.out.print("请输入预定时间(xxxx-xx-xx xx:xx:xx): ");
            String datetime = Utility.readString(30);
            int row = diningTableService.reserve(id, ordername, orderTel, datetime);
            if (row > 0) {
                System.out.println("\t预定餐位:" + id + "成功,信息如下");
                DiningTable diningTable1 = diningTableService.tableState(id);
                System.out.println("\t餐位\t状态\t\t姓名\t\t电话\t\t时间");
                System.out.println("\t" + diningTable1.getId() + "\t" + diningTable1.getState() + "\t" + diningTable1.getOrderName() + "\t" + diningTable1.getOrderTel() + "\t" + diningTable1.getOrderTime());
            } else {
                System.out.println("输入的信息有误，请重新输入...");
            }
        } else {
            System.out.println("该餐位已有人预定");
        }

    }

    //菜单
    public void listMenu() {
        List<Menu> menus = menuService.displayMenu();
        System.out.println("\n\t\t=================菜单===============");
        System.out.println("\t\t菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }

    //点餐功能
    public void orderingService() {
        System.out.println("\n==========点餐服务==========");
        System.out.print("请选择餐桌号(-1退出): ");
        int diningTableId = Utility.readInt();
        if (diningTableId == -1) {
            System.out.println("==========取消点餐==========");
            return;
        }
        if (diningTableService.tableState(Integer.toString(diningTableId)) == null) {
            System.out.println("==========没有该餐桌==========");
            return;
        }

        System.out.print("请选择菜品编号(-1退出): ");
        int menuId = Utility.readInt();
        if (menuId == -1) {
            System.out.println("==========取消点餐==========");
            return;
        }
        if (menuService.obtainMenu(menuId) == null) {
            System.out.println("==========没有该菜品==========");
            return;
        }

        System.out.print("请选择菜品数量(-1退出): ");
        int nums = Utility.readInt();
        if (nums == -1) {
            System.out.println("==========取消点餐==========");
            return;
        }
        if (billService.orderMenu(menuId, nums, diningTableId)) {
            //id + "\t\t" + billId + "\t\t" + menuId + "\t\t" + nums + "\t\t" + money + "\t\t" + diningTableId + billDate + "\t\t" + start;
            System.out.println("\n==============点餐成功==============");
            System.out.println("订单编号\t订单号\t\t\t\t\t\t\t\t\t菜品编号\t\t菜品数量\t\t总价格\t\t餐桌号\t\t下单时间\t\t\t订单状态");
            List<Bill> bills = billService.listBill(diningTableId);
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        } else {
            System.out.println("\n==============点餐失败==============");
        }
    }

    //查看账单
    public void MenuBill() {
        System.out.println("\n\t\t==============账单==============");
        System.out.println("订单编号\t订单号\t\t\t\t\t\t\t\t\t菜品编号\t\t菜品数量\t\t总价格\t\t餐桌号\t\t下单时间\t\t\t订单状态");
        List<MultiTableBean> bills = billService.listBillss();
        for (MultiTableBean bill : bills) {
            System.out.println(bill);
        }
    }

    //结账
    public void CheckOut() {
        System.out.println("\n\t\t==============结账服务==============");
        System.out.print("请选择要结账的餐桌号(-1退出): ");
        int diningTableId = Utility.readInt();
        if (diningTableId == -1) {
            System.out.println("==============取消结账==============");
            return;
        }
        DiningTable diningTable = diningTableService.tableState(Integer.toString(diningTableId));
        if (diningTable == null) {
            System.out.println("==============没有该餐桌==============");
            return;
        }
        if (!billService.PayBillByDiningTableId(diningTableId)) {
            System.out.println("==============该餐桌没有未结订单==============");
            return;
        }
        System.out.print("结账方式(支付宝/微信/现金)回车退出: ");
        String payment = Utility.readString(20, "");
        if (payment.equals("")) {
            System.out.println("==============取消结账==============");
            return;
        }
        if (!("支付宝".equals(payment) || "微信".equals(payment) || "现金".equals(payment))) {
            System.out.println("==============结账方式有误,请重试==============");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            if (billService.payBill(diningTableId, payment)) {
                System.out.println("==============完成结账==============");
            } else {
                System.out.println("==============结账失败==============");
            }
        } else {
            System.out.println("==============取消结账==============");
            return;
        }
    }
}
