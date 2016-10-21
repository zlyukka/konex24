package com.konex.servapp.controller;

import com.konex.servapp.entity.Card;
import com.konex.servapp.entity.CardType;
import com.konex.servapp.entity.User;
import com.konex.servapp.service.*;
import com.konex.servapp.validator.UserValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kneimad on 28.09.2016.
 */
@Controller
public class UserController {

    @Autowired
    private CardTypeServices cardTypeServices;

    @Autowired
    private CardServices cardServices;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    //@Autowired
    //private AccessDecisionManager accessDecisionManager;

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("registrationForm", new User());
  ;        System.out.println("=============> REGISTRATION GET");
        return "registration";
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("registrationForm") User registrationForm, BindingResult bindingResult, Model model, String error) {

        userValidator.validate(registrationForm, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("++++++++++++++++++ VALIDATION ERROR +++++++++++++++++    " + bindingResult);
            List<ObjectError> errorList = bindingResult.getAllErrors();

            error="";
            for (ObjectError err : errorList) {
                System.out.println("Field: " + ((FieldError)err).getField());
                System.out.println("DefaultMessage: " + ((FieldError)err).getDefaultMessage());
                error = error + ((FieldError)err).getDefaultMessage() + "\n\r";
            }

            model.addAttribute("error", error);
            return "registration";
        }

        userService.save(registrationForm);

        System.out.println("=============> REGISTRATION POST =========== \n" + registrationForm.getUsername() + "\n" + registrationForm.getPassword() + "\n" + registrationForm.getConfirmPassword() + "\n" + registrationForm.getUserPIB() + "\n");
        System.out.println("=============> REGISTRATION POST =========== save(registrationForm)");
        securityService.autoLogin(registrationForm.getUsername(), registrationForm.getPassword());
        System.out.println("=============> REGISTRATION POST =========== autoLogin(user)");

        System.out.println("=============> REGISTRATION POST");
        return "redirect:/hello";
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
            System.out.println("=============> LOGIN GET ==== ERROR");
        }

        if (logout != null) {
            model.addAttribute("message", "Вихід успішний");
            System.out.println("=============> LOGIN GET ==== LOGOUT");
        }
        System.out.println("=============> LOGIN GET");
        return "login";
    }

    @RequestMapping("/")
    public String index(Model model) {

        // якщо користувач вже увійшов, то перекинути його з реєстрації на домашню сторінку
        if(!User.isAnonymous()) {
            return "redirect:/hello";
        }

//        model.addAttribute("form", new RegistrationForm());
        return "login";
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
    public String hello(Model model, Principal user) {
//        System.out.println("getRoles: " + user.getRoles() + "\tgetAuthorities: " + user.getAuthorities());
//        model.addAttribute("userRoles", user.getRoles());
//        System.out.println("getAuthorities: " + user.getAuthorities());
//        model.addAttribute("getAuthorities", user.getAuthorities());
//        System.out.println("getAuthorities: " + userService.findByUsername(user.getName()));
        model.addAttribute("userData", userService.findByUsername(user.getName()));
        System.out.println("=============> HELLO GET");
        return "hello";
    }

//    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model, Map<String, Object> map) {
//        map.put("user", new User());

        map.put("userList", userService.listUsers());

        System.out.println("=============> ADMIN GET");
        //System.err.println(accessDecisionManager);
        return "admin";
    }

//    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{user.id}", method = RequestMethod.GET)
    public String delete(@PathVariable("user.id") Long id) {

        userService.delete(id);

        System.out.println("=============> DELETE GET");
        //System.err.println(accessDecisionManager);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public ModelAndView access(Principal user) {
        ModelAndView model = new ModelAndView();
        if (user != null) {
//            model.addObject("errorMsg", user.getName() + ", у вас немає доступу до цієї сторінки");
            model.addObject("errorMsg", user.getName() + " у вас немає доступу до цієї сторінки");
        } else {
            model.addObject("errorMsg", "У вас немає доступу до цієї сторінки");
        }
        model.setViewName("/access");
        System.out.println("=============> ACCESS DENIED GET");
        return model;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("userEditForm") User edUser,
                           @RequestParam(value = "userId", required = false) Long uId,
                           @RequestParam(value = "newBirthday", required = false) String birthday,
                           @RequestParam(value = "referer", required = false) String ref,
                           Model model, HttpServletRequest request, Principal currentUser){

        System.out.println("=============> EDIT USER " + request.getHeader("referer"));

        if (uId == null){ uId = (userService.findByUsername(currentUser.getName())).getId(); }
//        if (uId == null){ uId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(); }

        if (request.getHeader("referer").contains("/editUser")){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date date = dateFormat.parse(birthday);
                edUser.setBirthday(date);
            }catch(ParseException e) {
                e.printStackTrace();
            }

            userService.editUser(uId, edUser);

            return "redirect:"+ref.substring(ref.lastIndexOf('/'));
        }

        User user = userService.findById(uId);
        model.addAttribute("user", user);
        model.addAttribute("ref", request.getHeader("referer"));
        return "editUser";
    }

    //** Управление типами карт
    @RequestMapping(value = "/getCardsType", method = RequestMethod.GET)
    public String getCardsType(Model model, Principal currentUser){
        System.out.println("===================getCardType");
        model.addAttribute("cardsType",cardTypeServices.getCardType());
        return "cardsType";
    }


    @RequestMapping(value = "/addCardType", method = RequestMethod.GET)
    public String addCardType(Model model){
        System.out.println("===================Start cardType add");
        return "addCardType";
    }

    @RequestMapping(value = "/addCardType", method = RequestMethod.POST)
    public String addCardType(@ModelAttribute("cardForm") CardType cardType,
                         Model model, Principal currentUser){
        System.out.println(cardType);
        cardTypeServices.addCardType(cardType);
        return "redirect:/getCardsType";
    }

    @RequestMapping(value = "/cardsTypeEdit/{cardType_id}", method = RequestMethod.GET)
    public String cardTypeEdit(@PathVariable("cardType_id") Long id,
                              Model model){
        System.out.println("===================Start cardType edit");
        System.out.println("id="+id);
        model.addAttribute("cardType", cardTypeServices.getCardTypeById(id));
        return "cardsTypeEdit";
    }

    @RequestMapping(value = "/cardsTypeEdit", method = RequestMethod.POST)
    public String cardTypeEditSave(@ModelAttribute("cardTypeForm") CardType cardType,
                               Model model){
        System.out.println("===================Start cardType editSave");
        cardTypeServices.editCardType(cardType);
        return "redirect:/getCardsType";
    }


    //** Управление картами шаблон
    @RequestMapping(value = "/cards", method = RequestMethod.GET)
    public String getUserCard(Model model, Map<String, Object> map, Principal currentUser) {
        map.put("cardList", cardServices.getCardsByUser(userService.findByUsername(currentUser.getName())));
        map.put("cardsTypeList",cardTypeServices.getCardType());
        System.out.println("=============> CARDS GET");
        return "card";
    }

    @RequestMapping(value = "/card/add", method = RequestMethod.POST)
    public String addUserType(@ModelAttribute("card") Card card,
                              @RequestParam(value = "deactivateDate", required = false) String deactivate,
                              @RequestParam(value = "activateDate", required = false) String activate,
                              String error, Model model, Principal currentUser){
        System.out.println("card: " + card.toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if(deactivate.length()>6) {
                Date date = dateFormat.parse(deactivate);
                card.setDeactivate(date);
            }
            if(activate.length()>6) {
                Date date = dateFormat.parse(activate);
                card.setActivate(date);
            }
        }catch(ParseException e){
            e.printStackTrace();
        }
        if (card.getId() == null) {
            System.out.println("======> addCard");
            try {
                card.setUser(userService.findByUsername(currentUser.getName()));
                cardServices.addCard(card);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                System.err.println("==== ERROR =====> addCard: " + e);
//                model.addAttribute("error", "Invalid user's type.");
                return "redirect:/cards?error=ERROR! Invalid card's (Check if current cards num is not duplicated)";
            }
        } else {
            User user=userService.findByUsername(currentUser.getName());
            if(cardServices.getCardById(card.getId()).getUser().getId()==user.getId()){
                System.out.println("======> editCard");
            }else{
                System.out.println("======> Can not edit");
                return "redirect:/cards?error=ERROR! Can not edit this card "+cardServices.getCardById(card.getId());
            }
        }
        System.out.println("=============> ADD CARD POST");
//        return "redirect:/usertype?error=ПОМИЛКА! Невірний тип користувача";
        return "redirect:/cards";
    }

    @RequestMapping("cardEdit/{card.id}")
    public String editUserType(@PathVariable("card.id") Long id, Model model, Principal currentUser){
        User user=userService.findByUsername(currentUser.getName());
        if(cardServices.getCardById(id).getUser().equals(user)){
            System.out.println("======> editCard");
        }else{
            System.out.println("======> Can not edit");
            return "redirect:/cards?error=ERROR! Can not edit this card";
        }
        model.addAttribute("card", cardServices.getCardById(id));
        model.addAttribute("cardList", cardServices.getCardsByUser(userService.findByUsername(currentUser.getName())));
        model.addAttribute("cardsTypeList", cardTypeServices.getCardType());
        System.out.println("=============> EDIT CARD");
        return "card";
    }

    @RequestMapping(value = "/cardDelete/{card.id}", method = RequestMethod.GET)
    public String cardDelete(@PathVariable("card.id") Long id,
                             Model model, Principal currentUser){
        if(cardServices.getCardById(id).getUser().getId()==userService.findByUsername(currentUser.getName()).getId()){
            System.out.println("======> delete");
            try{
            cardServices.deleteCard(id);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                System.err.println("==== ERROR =====> deleteUserType: " + e);
                return "redirect:/cards?error=ERROR! Current user's type is active (Delete all users with this type before)";
            }
        }else{
            System.out.println("======> Can not delete");
            return "redirect:/cards?error=ERROR! Can not delete this card "+cardServices.getCardById(id);
        }

        return "redirect:/cards";
    }

    //**Карты админа
    @RequestMapping(value = "/admin/cards", method = RequestMethod.GET)
    public String adminUserCard(Model model, Map<String, Object> map, Principal currentUser) {
        map.put("cardList", cardServices.getCards());
        map.put("cardsTypeList",cardTypeServices.getCardType());
        System.out.println("=============> CARDS GET");
        return "card";
    }



    //** Управление картами
//    @RequestMapping(value = "/myCards", method = RequestMethod.GET)
//    public String getMyCards(Model model, Principal currentUser){
//        System.out.println("===================My cards");
//        User user=userService.findByUsername(currentUser.getName());
//        model.addAttribute("cards", cardServices.getCardsByUser(userService.findByUsername(currentUser.getName())));
//        return "myCards";
//    }
//
//    @RequestMapping(value = "/adminCards", method = RequestMethod.GET)
//    public String getCards(Model model, Principal currentUser){
//        System.out.println("===================My cards");
//        User user=userService.findByUsername(currentUser.getName());
//        model.addAttribute("cardList", cardServices.getCards());
//        return "myCards";
//    }
//
//    @RequestMapping(value = "/addCard", method = RequestMethod.GET)
//    public String addCad(Model model,HttpServletRequest request){
//        System.out.println("===================Finish card add "+request.getHeader("referer"));
//        model.addAttribute("cardsType",cardTypeServices.getCardType());
//        model.addAttribute("ref",request.getHeader("referer"));
//        return "addCard";
//    }
//
//    @RequestMapping(value = "/addCard", method = RequestMethod.POST)
//    public String addCad(@ModelAttribute("cardForm") Card myCard,
//                         @RequestParam(value = "referer", required = false) String ref,
//                         Model model, Principal currentUser){
//        myCard.setUser(userService.findByUsername(currentUser.getName()));
//        System.out.println(myCard);
//        cardServices.addCard(myCard);
//        if (ref.contains("/adminCards")){
//            return "redirect:/adminCards";
//        }
//        return "redirect:/myCards";
//    }
//
//    @RequestMapping(value = "/cardEdit/{card.id}", method = RequestMethod.GET)
//    public String cardEdit(@PathVariable("card.id") Long id,
//                           Model model, HttpServletRequest request){
//        System.out.println("===================Start card edit ");
//        model.addAttribute("card",cardServices.getCardById(id));
//        model.addAttribute("ref",request.getHeader("referer"));
//        model.addAttribute("cardsType",cardTypeServices.getCardType());
//        return "cardEdit";
//    }
//
//    @RequestMapping(value = "/cardEdit", method = RequestMethod.POST)
//    public String cardEdit(@ModelAttribute("cardForm") Card myCard,
//                           @RequestParam(value = "referer", required = false) String ref,
//                           @RequestParam(value = "deactivateDate", required = false) String deactivate,
//                           @RequestParam(value = "userId", required = false) Long userId,
//                           Model model, Principal currentUser){
//        User user=userService.findByUsername(currentUser.getName());
//        if(user.getAuthorities().toString().contains("ROLE_ADMIN") && userId!=null){
//            myCard.setUser(userService.findById(userId));
//            System.out.println("=================this ADMIN edit card ");
//        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        try{
//            if(deactivate.length()>6) {
//                Date date = dateFormat.parse(deactivate);
//                myCard.setDeactivate(date);
//            }
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//        System.out.println("===================Finish card edit "+myCard+" Date lengh="+deactivate.length());
//
//        cardServices.editCard(myCard, currentUser);
//
//        if (ref.contains("/adminCards")){
//            return "redirect:/adminCards";
//        }
//        return "redirect:/myCards";
//    }
//
//    @RequestMapping(value = "/cardDelete/{card.id}", method = RequestMethod.GET)
//    public String cardDelete(@PathVariable("card.id") Long id,
//                             Model model, HttpServletRequest request){
//        cardServices.deleteCard(id);
//        if (request.getHeader("referer").contains("/adminCards")){
//            return "redirect:/adminCards";
//        }
//        return "redirect:/myCards";
//    }
}
