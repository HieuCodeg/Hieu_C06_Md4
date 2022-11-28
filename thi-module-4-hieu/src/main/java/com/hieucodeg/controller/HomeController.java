package com.hieucodeg.controller;

import com.hieucodeg.model.Country;
import com.hieucodeg.model.Province;
import com.hieucodeg.repository.CountryRepository;
import com.hieucodeg.repository.ProvinceRepository;
import com.hieucodeg.service.country.ICountryService;
import com.hieucodeg.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/provinces", ""})
public class HomeController {

    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("province/home");
        List<Province> provinces = provinceService.findAllByDeletedIsFalse();
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/info/{id}")
    public ModelAndView showInfor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("province/info");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView =  new ModelAndView("redirect:/provinces");
            redirectAttributes.addFlashAttribute("error","ID Tỉnh không hợp lệ");
            return modelAndView;
        }
    }

    @GetMapping("/create")
    public ModelAndView showCreatePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("province/create");
        modelAndView.addObject("province", new Province());
        List<Country> countries = countryService.findAll();
        modelAndView.addObject("countries", countries);
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("province/edit");
            modelAndView.addObject("province", province.get());
            List<Country> countries = countryService.findAll();
            modelAndView.addObject("countries", countries);
            return modelAndView;
        } else {
            ModelAndView modelAndView =  new ModelAndView("redirect:/provinces");
            redirectAttributes.addFlashAttribute("error","ID Tỉnh không hợp lệ");
            return modelAndView;
        }
    }
    @GetMapping("/suspend/{id}")
    public ModelAndView showSuspendForm(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("province/suspend");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/provinces");
            redirectAttributes.addFlashAttribute("error", "ID tỉnh không hợp lệ");
            return modelAndView;
        }
    }

    @PostMapping("/suspend/{id}")
    public ModelAndView suspend(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Province> provinceOptional = provinceService.findById(id);

        if (!provinceOptional.isPresent()) {
            modelAndView.setViewName("province/suspend");
        }else {
            Province province = provinceOptional.get();
            province.setDeleted(true);
            provinceService.save(province);
            modelAndView =  new ModelAndView("redirect:/provinces");
            redirectAttributes.addFlashAttribute("message","Xóa thông tin thành công!!!");

        }
        return modelAndView;
    }


    @PostMapping("/create")
    public ModelAndView create(@Validated @ModelAttribute Province province, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasFieldErrors()) {
            List<Country> countries = countryService.findAll();
            modelAndView.addObject("countries", countries);
            modelAndView.setViewName("province/create");
        }else {
            province.setId(0L);
            provinceService.save(province);

            modelAndView =  new ModelAndView("redirect:/provinces");
            redirectAttributes.addFlashAttribute("message","Tạo mới thành công!!!");
        }

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView save(@Validated @ModelAttribute Province customer, BindingResult bindingResult, @PathVariable Long id,RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasFieldErrors()) {
            List<Country> countries = countryService.findAll();
            modelAndView.addObject("countries", countries);
            modelAndView.setViewName("province/edit");
        }else {
            provinceService.save(customer);
            modelAndView =  new ModelAndView("redirect:/provinces");
            redirectAttributes.addFlashAttribute("message","Chỉnh sửa thành công!!!");
        }
        return modelAndView;
    }

}
