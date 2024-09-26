package com.example.myapp.api

import com.example.myapp.service.ExcelDataService
import jakarta.servlet.http.HttpServletResponse
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/v1/exceldata")
@AllArgsConstructor
class ExcelDataAPI {

    @Autowired
    lateinit var excelDataService: ExcelDataService

    @PostMapping("/import")
    fun importExcelFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        require(!file.isEmpty) { "File cannot be empty" }
        return try {
            excelDataService.importExcel(file)
            ResponseEntity("Import successly", HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("Error khi import file: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/export")
    fun exportExcelFile(response: HttpServletResponse) {
        response.contentType = "application/octet-stream"
        val headerKey = "Content-Disposition"
        val headerValue = "attachment;filename=exceldata.xls"
        response.setHeader(headerKey, headerValue)
        excelDataService.exportExcelData(response)
    }

}