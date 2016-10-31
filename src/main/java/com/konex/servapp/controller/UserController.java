package com.konex.servapp.controller;

import com.konex.servapp.entity.Card;
import com.konex.servapp.entity.CardType;
import com.konex.servapp.entity.User;
import com.konex.servapp.entity.reference.Goods;
import com.konex.servapp.entity.reference.GoodsRemnants;
import com.konex.servapp.service.*;
import com.konex.servapp.service.reference.GoodsRemnantsServices;
import com.konex.servapp.service.reference.GoodsServicea;
import com.konex.servapp.validator.UserValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

/**
 * Created by kneimad on 28.09.2016.
 */
@Controller
public class UserController {

    @Autowired
    private GoodsRemnantsServices goodsRemnantsServices;

    @Autowired
    private GoodsServicea goodsServicea;

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

    private Card cardError;
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

        if (request.getHeader("referer").contains("/editUser")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                System.out.println("До");
                Date date = dateFormat.parse(birthday);
                System.out.println("После");
                edUser.setBirthday(date);
                userService.editUser(uId, edUser);
                return "redirect:" + ref.substring(ref.lastIndexOf('/'));
            } catch (ParseException e) {
                e.printStackTrace();
                userService.editUser(uId, edUser);
                return "redirect:" + ref.substring(ref.lastIndexOf('/'));
            } catch (NullPointerException e){
                //e.printStackTrace();
                System.out.println("Нехуй тікать два раза редактировать");
            }
        }

        User user = userService.findById(uId);
        model.addAttribute("user", user);
        if(!request.getHeader("referer").contains("editUser")) {
            model.addAttribute("ref", request.getHeader("referer"));
        }else{
            model.addAttribute("ref","/");
        }
        return "editUser";
    }

    //** Управление картами шаблон
    @RequestMapping(value = "/cards", method = RequestMethod.GET)
    public String getUserCard(Model model, Map<String, Object> map, Principal currentUser) {
        if (cardError!=null) {
            map.put("card", cardError);
            cardError=null;
        }
        map.put("cardList", cardServices.getCardsByUser(userService.findByUsername(currentUser.getName())));
        map.put("cardsTypeList",cardTypeServices.getCardType());
        System.out.println("=============> CARDS GET");
        return "card";
    }

    @RequestMapping(value = "/card/add", method = RequestMethod.POST)
    public String addCard(@ModelAttribute("card") Card card,
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
                cardError=card;
                model.addAttribute("error", "Invalid user's type.");
                return "redirect:/cards?error=ERROR! Invalid card's (Check if current cards num is not duplicated)";
            }
        } else {
            User user=userService.findByUsername(currentUser.getName());
            if(cardServices.getCardById(card.getId()).getUser().getId()==user.getId()){
                card.setUser(user);
                try {
                    cardServices.editCard(card);
                }catch (org.springframework.dao.DataIntegrityViolationException e) {
                    System.err.println("==== ERROR =====> addCard: " + e);
                    cardError=card;
                    model.addAttribute("error", "Invalid user's type.");
                    return "redirect:/cards?error=ERROR! Invalid card's (Check if current cards num is not duplicated)";
                }
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
    public String editCard(@PathVariable("card.id") Long id, Model model, Principal currentUser){
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
    public String adminCard(Model model, Map<String, Object> map, Principal currentUser) {
        if (cardError!=null) {
            map.put("card", cardError);
            cardError=null;
        }
        map.put("cardList", cardServices.getCards());
        map.put("cardsTypeList",cardTypeServices.getCardType());
        System.out.println("=============> CARDS GET");
        return "adminCard";
    }

    @RequestMapping(value = "/admin/card/add", method = RequestMethod.POST)
    public String adminAddCard(@ModelAttribute("card") Card card,
                              @RequestParam(value = "deactivateDate", required = false) String deactivate,
                              @RequestParam(value = "activateDate", required = false) String activate,
                              @RequestParam(value = "userId", required = false) Long userId,
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
                cardServices.addCard(card);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                System.err.println("==== ERROR =====> addCard: " + e);
                cardError=card;
//              model.addAttribute("error", "Invalid user's type.");
                return "redirect:/admin/cards?error=ERROR! Invalid card's (Check if current cards num is not duplicated)";
            }
        } else {
            if(userId!=null) {
                //System.out.println(userId);
                card.setUser(userService.findById(userId));
            }
            cardServices.editCard(card);
            System.out.println("======> editCard");
        }
        return "redirect:/admin/cards";
    }

    @RequestMapping("admin/cardEdit/{card.id}")
    public String adminEditCard(@PathVariable("card.id") Long id, Model model, Principal currentUser){
        User user=userService.findByUsername(currentUser.getName());
        System.out.println("======> editCard");
        model.addAttribute("card", cardServices.getCardById(id));
        model.addAttribute("cardList", cardServices.getCards());
        model.addAttribute("cardsTypeList", cardTypeServices.getCardType());
        System.out.println("=============> EDIT CARD");
        return "adminCard";
    }

    @RequestMapping(value = "/admin/cardDelete/{card.id}", method = RequestMethod.GET)
    public String adminCardDelete(@PathVariable("card.id") Long id,
                             Model model, Principal currentUser){
        System.out.println("======> delete");
        try{
            cardServices.deleteCard(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            System.err.println("==== ERROR =====> deleteUserType: " + e);
            return "redirect:admin/cards?error=ERROR! Current card is us";
        }


        return "redirect:/admin/cards";
    }


    @RequestMapping(value = "/admin/getUserByTel", method = RequestMethod.POST)
    @ResponseBody
    public Map getUserByTel(@RequestParam("tel") String tel) {
        User user=userService.finUserByMobile(tel);
        System.out.println(user);
        Map us=new HashMap();
        us.put("tel",user.getMobile());
        us.put("fio",user.getUserPIB());
        us.put("id",user.getId());
        return us;
    }

    //**Управление типами карт
    @RequestMapping(value = "/admin/cardsType", method = RequestMethod.GET)
    public String cardsType(Model model){
        model.addAttribute("cardTypeList",cardTypeServices.getCardType());
        return "adminCardType";
    }

    @RequestMapping(value = "/admin/cardType/add", method = RequestMethod.POST)
    public String cardsTypeAdd(@ModelAttribute("cardType") CardType cardType,
                                Model model){
        if(cardType.getId()==null){
            try {
                cardTypeServices.addCardType(cardType);
            }catch (org.springframework.dao.DataIntegrityViolationException e) {
                System.err.println("==== ERROR =====> addCardType: " + e);
                return "redirect:/admin/cardsType?error=ERROR! Invalid card'Type's (The card should not be empty)";
            }
            System.out.println("======>add cardType "+cardType);
        }else{
            System.out.println("======>edit cardType "+cardType);
            try{
                cardTypeServices.editCardType(cardType);
            }catch (org.springframework.dao.DataIntegrityViolationException e) {
                System.err.println("==== ERROR =====> addCardType: " + e);
            return "redirect:/admin/cardsType?error=ERROR! Invalid card'Type's (The card should not be empty)";
            }
        }
        model.addAttribute("cardTypeList",cardTypeServices.getCardType());
        return "redirect:/admin/cardsType";
    }
    @RequestMapping(value = "/admin/cardTypeEdit/{cardType.id}", method = RequestMethod.GET)
    public String adminEditCardType(@PathVariable("cardType.id") Long id, Model model){
        model.addAttribute("cardType", cardTypeServices.getCardTypeById(id));
        model.addAttribute("cardTypeList",cardTypeServices.getCardType());
        return "adminCardType";
    }
    @RequestMapping(value = "/admin/cardTypeDelete/{cardType.id}", method = RequestMethod.GET)
    public String adminCardTypeDelete(@PathVariable("cardType.id") Long id, Model model){
        try{
            cardTypeServices.deleteCardType(id);
            System.out.println("======>edit cardType "+id);
        }catch (org.springframework.dao.DataIntegrityViolationException e) {
            System.err.println("==== ERROR =====> addCardType: " + e);
            return "redirect:/admin/cardsType?error=ERROR! Invalid card'Type's (There are cards relating to this type of)";
        }
        return "redirect:/admin/cardsType";
    }
    @RequestMapping(value = "/goods/{good.name}", method = RequestMethod.GET)
    public String getGoodByPartName(@PathVariable("good.name") String goodName, Model model){
        System.out.println(" Товар имя которого начинается на "+goodName);
        List<Long> tochList=new ArrayList<Long>();
        tochList.add(1L);
        tochList.add(2L);
       for(GoodsRemnants goodsRemnants : goodsRemnantsServices.gatRemnantsByPartName(goodName, tochList)){
            System.out.println("Товар-"+goodsRemnants.getGoods()+"  Аптека-"+goodsRemnants.getTradePoint()+" Колич-"+goodsRemnants.getCount());
        }
        return "redirect:/";
    }
}


