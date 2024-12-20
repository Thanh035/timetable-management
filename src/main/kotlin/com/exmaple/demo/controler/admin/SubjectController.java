package com.exmaple.demo.controler.admin;

import com.exmaple.demo.domain.dto.SubjectDTO;
import com.exmaple.demo.service.ISubjectService;
import jakarta.servlet.http.HttpServletRequest;
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
        List<SubjectDTO> subjectDTOList = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjectDTOList);
        return "admin/subject/list";
    }

    @RequestMapping(value = "/admin/subject/edit", method = RequestMethod.GET)
    public String editSubjectPage(@RequestParam(value = "id", required = false) Long id, Model model, HttpServletRequest request) {
        SubjectDTO dto = null;
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
