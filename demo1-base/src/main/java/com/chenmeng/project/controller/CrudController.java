package com.chenmeng.project.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crud")
public class CrudController {

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id) {
        return "Get by ID: " + id;
    }

    @PostMapping
    public String create(@RequestBody String data) {
        return "Created: " + data;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody String data) {
        return "Updated ID: " + id + " with data: " + data;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return "Deleted ID: " + id;
    }
}