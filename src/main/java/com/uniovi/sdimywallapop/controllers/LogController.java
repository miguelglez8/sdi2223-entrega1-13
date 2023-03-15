package com.uniovi.sdimywallapop.controllers;

import com.uniovi.sdimywallapop.services.LogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LogController {

    @Autowired
    private LogServices logServices;

    @RequestMapping("/log/list")
    public String getListado(Model model) {
        model.addAttribute("logList", logServices.getLogs());
        return "log/list";
    }

    @RequestMapping("/log/list/update")
    public String updateList(Model model){
        model.addAttribute("logList", logServices.getLogs());
        return "log/list :: tableLogs";
    }

}
