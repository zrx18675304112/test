package com.zrxedu.mhl.service;

import com.zrxedu.mhl.dao.MenuDAO;
import com.zrxedu.mhl.dommain.Menu;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    public List<Menu> displayMenu() {
        String sql = "select * from menu;";
        return menuDAO.queryMulti(sql, Menu.class);
    }

    public Menu obtainMenu(int menuId) {
        String sql = "select * from menu where id=?;";
        return menuDAO.querySingle(sql, Menu.class, menuId);
    }
}
