package com.movies.apirest.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.movies.apirest.Entities.Usuario;
import com.movies.apirest.Repositories.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RegistroController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formulario-registro";
    }

    @PostMapping("/guardar")
    public ModelAndView guardarUsuario(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("formulario-registro");
            return modelAndView;
        }

        usuarioRepository.save(usuario);
        modelAndView.setViewName("registro-exitoso");
        modelAndView.addObject("nombre", usuario.getNombre());
        return modelAndView;
    }
}

