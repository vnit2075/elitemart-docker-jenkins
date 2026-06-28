package com.elitemart.controller;

import com.elitemart.dto.RegisterForm;
import com.elitemart.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /* ─── Login ─── */

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null)  model.addAttribute("error",   "Invalid username or password.");
        if (logout != null) model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    /* ─── Register ─── */

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        // Validate form fields
        if (result.hasErrors()) {
            return "register";
        }

        // Check uniqueness
        if (userService.existsByUsername(form.getUsername())) {
            result.rejectValue("username", "duplicate", "Username is already taken.");
            return "register";
        }
        if (userService.existsByEmail(form.getEmail())) {
            result.rejectValue("email", "duplicate", "Email is already registered.");
            return "register";
        }

        userService.registerUser(form);
        redirectAttributes.addFlashAttribute("success", "Account created! Please log in.");
        return "redirect:/login";
    }
}
