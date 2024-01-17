package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("list", employeeRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/add")
    public String createForm() {
        return "/employee/create";
    }

    @PostMapping("/add")
    public String create(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("item", employeeRepository.findById(id).get());
        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@PathVariable Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(Employee employee) {
        employeeRepository.delete(employee);
        return "redirect:/employees";
    }

    @GetMapping("/searchName")
    public ModelAndView search(String name) {
        ModelAndView modelAndView = new ModelAndView("employee/list");
        List<Employee> searchResults = employeeRepository.findAllByNameContaining(name);
        modelAndView.addObject("list", searchResults);
        return modelAndView;
    }

    @GetMapping("/searchSalary")
    public ModelAndView search(double lowSalary , double highSalary) {
        ModelAndView modelAndView = new ModelAndView("employee/list");
        List<Employee> searchResults = employeeRepository.findAllBySalaryBetween(lowSalary,highSalary);
        modelAndView.addObject("list", searchResults);
        return modelAndView;
    }

    @GetMapping("/sort")
    public ModelAndView sort() {
        ModelAndView modelAndView = new ModelAndView("employee/list");
        List<Employee> sortResults = employeeRepository.findAllByOrderBySalary();
        modelAndView.addObject("list", sortResults);
        return modelAndView;
    }

    @GetMapping("/page")
    public ModelAndView page(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("employee/list");
        Page<Employee> sortResults = employeeRepository.findAllBy(pageable);
        modelAndView.addObject("list", sortResults);
        return modelAndView;
    }

    @GetMapping("/employeess")
    public String getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        Page<Employee> employeePage = employeeService.findAllEmployees(page, size);
        model.addAttribute("employeePage", employeePage);
        return "employeess";
    }

}
