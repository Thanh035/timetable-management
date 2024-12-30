package com.example.demo.controler.admin;

import com.example.demo.dto.AdminSubjectDTO;
import com.example.demo.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller(value = "subjectControllerOfAdmin")
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    @RequestMapping(value = "/admin/subject/list", method = RequestMethod.GET)
    public String showListPage(Model model) {
        List<AdminSubjectDTO> adminSubjectDTOList = subjectService.getAllSubjects();
        model.addAttribute("subjects", adminSubjectDTOList);
        return "admin/subject/list";
    }

    @RequestMapping(value = "/admin/subject/edit", method = RequestMethod.GET)
    public String editSubjectPage(@RequestParam(value = "id", required = false) Long id, Model model) {
        AdminSubjectDTO dto = null;
        if (id != null) {
            dto = subjectService.getSubject(id);
        }
        model.addAttribute("subject", dto);
        return "admin/subject/edit";
    }

    @RequestMapping(value = "/admin/subject/add", method = RequestMethod.GET)
    public String addSubjectPage() {
        return "admin/subject/add";
    }

}
