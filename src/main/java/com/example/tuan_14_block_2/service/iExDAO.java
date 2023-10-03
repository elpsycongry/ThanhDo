package com.example.tuan_14_block_2.service;

import com.example.tuan_14_block_2.model.ExObj;

import java.util.List;

public interface iExDAO {
    public List<ExObj> getAllObj();
    public ExObj getById(int id);
    public void addObj(ExObj Obj);
    public void delete(int id);
    void addObjTransaction(ExObj obj, List<Integer> permissions);
}
