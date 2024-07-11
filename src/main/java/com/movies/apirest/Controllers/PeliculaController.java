package com.movies.apirest.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movies.apirest.Repositories.PeliculaRepository;

import jakarta.validation.Valid;

import com.movies.apirest.Entities.Pelicula;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PeliculaController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @GetMapping("/administrador")
    public String adminPelis(Model model) {
        model.addAttribute("NewMovie", new Pelicula());
        return "administrador.html";
    }

    @GetMapping("/admin_list")
    public String listaPelis(Model model) {
        List<Pelicula> listaDB = peliculaRepository.findAll();
        model.addAttribute("pelis", listaDB);
        return "admin_list";
    }

    @PostMapping("/administrador")
    public String almacenarPeli(@ModelAttribute("NewMovie") @Valid Pelicula NewMovie, BindingResult result,
            @RequestParam("file") MultipartFile imagen, RedirectAttributes redirect) {

        if (!imagen.isEmpty()) {
            Path pathImagenes = Paths.get("src/main/resources/static/img/imagen_admin");
            String guardarImagen = pathImagenes.toFile().getAbsolutePath();

            try {
                byte[] tamañoImagen = imagen.getBytes();
                Path rutaCompleta = Paths.get(guardarImagen + "/" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, tamañoImagen);

                NewMovie.setImagen(imagen.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        peliculaRepository.save(NewMovie);
        redirect.addFlashAttribute("msg", "Película guardada exitosamente.");
        return "redirect:/index.html";
    }

    @GetMapping("/admin_list/{id}")
    public String eliminarPeli(@PathVariable Long id){
        peliculaRepository.deleteById(id);
        return "redirect:/admin_list";
    }
    

}
