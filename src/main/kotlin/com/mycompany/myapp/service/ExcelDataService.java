package com.mycompany.myapp.service;

import com.mycompany.myapp.dao.impl.ExcelDataDAO;
import com.mycompany.myapp.domain.model.Exceldata;
import com.mycompany.myapp.utils.ReadExcelDataUtil;
import com.mycompany.myapp.utils.WriteExcelDataUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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

