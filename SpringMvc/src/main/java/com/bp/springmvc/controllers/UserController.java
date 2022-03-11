package com.bp.springmvc.controllers;
import com.bp.springmvc.beans.User;
import com.bp.springmvc.beans.WalletEntry;
import com.bp.springmvc.dao.UserDao;
import com.bp.springmvc.dao.WalletEntryDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    UserDao dao; 
    
    @Autowired
    WalletEntryDao walletEntryDao;
    User loggedUser;
    
    @RequestMapping("/registerForm")
    public String showRegisterForm(Model m) {
        m.addAttribute("command", new User());
        return "registerForm";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String insertUser(@ModelAttribute("user") User user) {
        dao.save(user);
        return "redirect:/loginForm";
    }
        
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user) {
        dao.update(user);
        return "redirect:/loginForm";
    }
    
    @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    public String showLoginForm(Model m) {
        m.addAttribute("command", new User());
        return "loginForm";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginAttempt(Model m, @ModelAttribute("user") User user) {
        m.addAttribute("command", user);
        UserDao.LoginAttemptWrapper wrapper = dao.loginAttempt(user);
        if(wrapper.attempt){
            this.loggedUser = wrapper.u;
            return "redirect:/userPanel"; 
        }else{
            return "redirect:/errorPage";
        }
    }
    
    
    @RequestMapping(value = "/userPanel", method = RequestMethod.GET)
    public String showUserPanel(Model m) {
        return "userPanel";
    }
    
    @RequestMapping(value = "/walletOverwiev", method = RequestMethod.GET)
    public String showWalletOverwiev(Model m) {
        m.addAttribute("list", walletEntryDao.getAllWalletEntries(this.loggedUser));
        return "walletOverwiev";
    }
    
    @RequestMapping(value = "/showWalletCredentials/{id}", method = RequestMethod.GET)
    public String showWalletEntryCredentials(@PathVariable int id, Model m) {
        m.addAttribute("list", walletEntryDao.getWalletEntryCredentials(this.loggedUser, id));
        return "showWalletCredentials";
    }
    
    @RequestMapping(value = "/editForm", method = RequestMethod.GET)
    public String showEditForm(Model m) {
        m.addAttribute("command", this.loggedUser);
        return "editForm";
    }
    
    @RequestMapping(value = "/addWalletEntryForm", method = RequestMethod.GET)
    public String addWalletEntry(Model m) {
        m.addAttribute("command", new WalletEntry());
        return "addWalletEntryForm";
    }
    
    @RequestMapping(value = "/addWalletEntry", method = RequestMethod.POST)
    public String addWalletEntry(Model m,  @ModelAttribute("walletEntry") WalletEntry walletEntry){
        walletEntryDao.insertWalletEntry(walletEntry, this.loggedUser);
        return "redirect:/walletOverwiev";
    }
    
    @RequestMapping(value = "/errorPage", method = RequestMethod.GET)
    public String showErrorPage(Model m) {
        return "errorPage";
    }
}