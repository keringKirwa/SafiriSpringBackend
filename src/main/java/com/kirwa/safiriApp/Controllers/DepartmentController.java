package com.kirwa.safiriApp.Controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@Slf4j
public class DepartmentController {
    @GetMapping("/")
    public String saveDepartment() {
        log.info("Inside saveDepartment method of DepartmentController");

        return "Home page of the Departments Application";

    }


}