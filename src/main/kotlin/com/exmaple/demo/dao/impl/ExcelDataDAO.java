package com.exmaple.demo.dao.impl;

import com.exmaple.demo.domain.model.Exceldata;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ExcelDataDAO {

    void insertExcelData(Exceldata exceldata);

    void updateExcelData(Exceldata update);
    Optional<Exceldata> selectExcelDataById(Long excelDataId);

    List<Exceldata> findAllBySortId();

}
