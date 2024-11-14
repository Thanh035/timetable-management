package com.exmaple.demo.service;

import com.exmaple.demo.domain.model.Exceldata;
import com.exmaple.demo.utils.WriteExcelDataUtil;
import com.exmaple.demo.dao.impl.ExcelDataDAO;
import com.exmaple.demo.utils.ReadExcelDataUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ExcelDataService {
    @Autowired
    private ExcelDataDAO excelDataDAO;

    public void importExcel(MultipartFile file) throws IOException {

        List<Exceldata> listExcelData = ReadExcelDataUtil.readExcel(file.getOriginalFilename(), file.getInputStream());
        listExcelData.forEach(item -> {
            excelDataDAO.selectExcelDataById(item.getId()).ifPresentOrElse(
                    existingData -> excelDataDAO.updateExcelData(item), // Nếu tồn tại
                    () -> excelDataDAO.insertExcelData(item)                 // Nếu không tồn tại
            );
        });
    }

    public void exportExcelData(HttpServletResponse response) throws IOException {
        List<Exceldata> exceldataList = excelDataDAO.findAllBySortId();
        WriteExcelDataUtil.writeExcel(exceldataList, response);
    }


}

