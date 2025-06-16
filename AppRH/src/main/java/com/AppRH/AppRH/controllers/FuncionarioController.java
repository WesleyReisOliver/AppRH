package com.AppRH.AppRH.controllers;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Funcionario;

import jakarta.validation.Valid;

import com.AppRH.AppRH.repository.FuncionarioRepository;

@Controller
public class FuncionarioController {
    
    @Autowired
    private FuncionarioRepository fr;

    //Chama o form de cadastrar funcionario
    @RequestMapping(value = "/cadastrarfuncionario", method = RequestMethod.GET)
    public String form() {

        return "funcionario/form-funcionario";
    }

    //Cadastra funcionario
    @RequestMapping(value = "/cadastrarfuncionario", method = RequestMethod.POST)
    public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {

        if(result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/cadastrarfuncionario";
        }

        fr.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
        return "redirect:/funcionarios";
    }

    //listar funcionario
    @RequestMapping("/funcionarios")
    public ModelAndView listaFuncionarios() {

        ModelAndView mv = new ModelAndView("funcionario/lista-funcionario");
        Iterable<Funcionario> funcionarios = fr.findAll();
        mv.addObject("funcionarios", funcionarios);
        return mv;
    }

    //Deleta funcionario
    @RequestMapping("/deletarFuncionario")
    public String deletarFuncionario(@RequestParam("id") long id) {
        Funcionario funcionario = fr.findById(id);
        fr.delete(funcionario);
        return "redirect:/funcionarios";
    }

    //Metodos que atualizam funcionario
    //form
    @RequestMapping(value = "/editar-funcionario", method = RequestMethod.GET)
    public ModelAndView editarFuncionario(@RequestParam("id") long id) {
        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    //update funcionario
    @RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
    public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result,
        RedirectAttributes attributes) {
        
        fr.save(funcionario);
        attributes.addFlashAttribute("sucess", "Funcionario alterado com sucesso!");

        long idLong = funcionario.getId();
        String id = "" + idLong;
        return "redirect:/funcionarios/" + id;
    }

    // Exibe os dados do funcionário
    @RequestMapping(value = "/funcionarios/{id}", method = RequestMethod.GET)
        public ModelAndView detalhesFuncionario(@PathVariable("id") long id) {
        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/detalhes-funcionario");
        mv.addObject("funcionario", funcionario);
        return mv;
    } 
}