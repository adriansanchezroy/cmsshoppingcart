package com.asr.cmsshoppingcart.controllers;

import java.util.List;

import javax.validation.Valid;

import com.asr.cmsshoppingcart.models.PageRepository;
import com.asr.cmsshoppingcart.models.data.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/pages") // request needs to start with path component
public class AdminPagesController {

    @Autowired // pageRepo injects automatically
    private PageRepository pageRepo;

    @GetMapping
    public String index(Model model) {

        List<Page> pages = pageRepo.findAll();

        model.addAttribute("pages", pages);

        return "admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Page page) {
        return "/admin/pages/add";
    }

    // Provides functionality to add new pages
    @PostMapping("/add")
    public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/pages/add";
        }
        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-")
                : page.getSlug().toLowerCase().replace(" ", "-"); // Removes blank spaces

        Page slugExists = pageRepo.findBySlug(slug); // verifies if slug already exists

        // Added page slug exists messages prompt
        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);

        } else {
            page.setSlug(slug);
            page.setSorting(100);

            pageRepo.save(page);
        }

        return "redirect:/admin/pages/add";
    }

    // Directs to edit page for each id
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        Page page = pageRepo.getById(id);

        model.addAttribute("page", page);

        return "admin/pages/edit";

    }

    // Provides functionality to edit pages
    @PostMapping("/edit")
    public String edit(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/pages/edit";
        }
        redirectAttributes.addFlashAttribute("message", "Page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") // Removes blank spaces
                : page.getSlug().toLowerCase().replace(" ", "-");

        Page slugExists = pageRepo.findBySlugAndId(slug, page.getId()); // verifies if slug already exists

        // If edited slug exists, messages are prompted
        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);

        } else {
            page.setSlug(slug);
            pageRepo.save(page);
        }

        return "redirect:/admin/pages/edit/" + page.getId();
    }

    // Redirects to delete page for each id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        
        pageRepo.deleteById(id); //jpaRepo

        redirectAttributes.addFlashAttribute("message", "Page deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/pages";

    }

}
