package com.example.tuan_14_block_2;

import com.example.tuan_14_block_2.service.ExDAO;

public class Test {
    public static void main(String[] args) {
        ExDAO exDAO = new ExDAO();
        System.out.println(exDAO.getById(1));
    }
}
