package com.example.tuan_14_block_2;

import com.example.tuan_14_block_2.model.ExObj;
import com.example.tuan_14_block_2.service.ExDAO;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ExDAO exDAO = new ExDAO();
        ExObj exObj = new ExObj("tung",13);
        ArrayList<Integer> permissions = new ArrayList<>();
        permissions.add(1);
        permissions.add(3);
        permissions.add(4);
        exDAO.addObjTransaction(exObj,permissions);
    }
}
