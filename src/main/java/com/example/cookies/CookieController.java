package com.example.cookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CookieController {

    // Mostrar formulario de login
    @GetMapping("/")
    public String loginForm() {
        return "login";
    }

    // Procesar login y crear cookie
    @PostMapping("/login")
    public String login(
            @RequestParam String usuario,
            HttpServletResponse response) {

        Cookie cookie = new Cookie("usuario", usuario);
        cookie.setMaxAge(-1); // cookie de sesión
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/perfil";
    }

    // Mostrar perfil leyendo la cookie
    @GetMapping("/perfil")
    public String perfil(HttpServletRequest request, Model model) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("usuario")) {
                    model.addAttribute("usuario", c.getValue());
                    return "perfil";
                }
            }
        }

        return "redirect:/";
    }

    // Logout (eliminar cookie)
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("usuario", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}