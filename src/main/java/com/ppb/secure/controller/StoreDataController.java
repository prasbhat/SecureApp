package com.ppb.secure.controller;

import com.ppb.secure.component.GenerateDecryptionPassword;
import com.ppb.secure.component.GenerateEncryptionPassword;
import com.ppb.secure.component.GenerateRandomKey;
import com.ppb.secure.model.Categories;
import com.ppb.secure.model.LoginUser;
import com.ppb.secure.model.PersonalInfo;
import com.ppb.secure.repository.CategoriesRepository;
import com.ppb.secure.repository.LoginUserRepository;
import com.ppb.secure.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreDataController {

    @Autowired
    PersonalInfoRepository personalInfoRepository;

    @Autowired
    LoginUserRepository loginUserRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    GenerateEncryptionPassword generateEncryptionPassword;

    @Autowired
    GenerateDecryptionPassword generateDecryptionPassword;

    @Autowired
    GenerateRandomKey generateRandomKey;

    private ArrayList<String> getCategoriesList() {
        ArrayList<String> appCatList = new ArrayList<>();
        List<Categories> categoriesList = (List<Categories>) categoriesRepository.findAll();
        for(Categories categories : categoriesList) {
            appCatList.add(categories.getCategoryName());
        }

        return appCatList;
    }

//    @Value("${random_key}")
//    public void setKeyGen(String tempKey) {
//        keyGen = tempKey;
//    }

    private String getKeyGen(HttpServletRequest request) {

        LoginUser loggedInUser = (LoginUser) request.getSession().getAttribute("loggedInUser");
        return loggedInUser.getKeyGen();
    }

    private String getLoggedInUser(HttpServletRequest request) {
        LoginUser loggedInUser = (LoginUser) request.getSession().getAttribute("loggedInUser");
        return loggedInUser.getUsername();
    }


    @GetMapping(value = "/")
    private ModelAndView login() {
        return new ModelAndView("login", "userObj", new LoginUser());
    }

    @GetMapping(value = "login")
    private ModelAndView getHome(HttpServletRequest request){
        LoginUser loggedInUser = (LoginUser) request.getSession().getAttribute("loggedInUser");
        if(null != loggedInUser){
            return new ModelAndView("home");
        }
        return login();
    }

    @PostMapping(value = "login")
    private ModelAndView home(@ModelAttribute LoginUser loginUser, HttpServletRequest request) {
        LoginUser loginUser1 = loginUserRepository.findByUsername(loginUser.getUsername());
        ModelAndView mav = new ModelAndView();
        if (null == loginUser1 || !loginUser1.getPassword().equals(generateRandomKey.generatePasswordHash(
                loginUser.getPassword()))) {
            mav.addObject("loginError", "Invalid Credentials");
            mav.addObject("userObj", new LoginUser());
            mav.setViewName("login");
            return mav;
        }

        request.getSession().setAttribute("loggedInUser", loginUser1);
        return new ModelAndView("home");
    }

    @GetMapping(value = "register")
    private ModelAndView registerUser() {
        return new ModelAndView("createUser", "userObj",
                new LoginUser());
    }

    @PostMapping(value = "createUser")
    private ModelAndView createUser(@ModelAttribute(value = "userObj") LoginUser loginUser) {
        LoginUser loginUser1 = loginUserRepository.findByUsername(loginUser.getUsername());
        if (null != loginUser1) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("loginError", "Username exists");
            mav.addObject("userObj", new LoginUser());
            mav.setViewName("createUser");
            return mav;
        }

        loginUser.setKeyGen(generateRandomKey.generateRandomKeys());
        loginUser.setPassword(generateRandomKey.generatePasswordHash(loginUser.getPassword()));
        loginUserRepository.save(loginUser);

        return new ModelAndView("login", "userObj", new LoginUser());
    }

    @GetMapping(value = "create")
    private ModelAndView getCreateView() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("appCatList", getCategoriesList());
        mav.addObject("personalInfoObj", new PersonalInfo());
        mav.setViewName("createView");
        return mav;
    }

    @PostMapping(value = "create")
    private ModelAndView collectTheData(@ModelAttribute PersonalInfo personalInfo,
                                       HttpServletRequest request) {

        String loggedInUser = getLoggedInUser(request);
        if(null != loggedInUser) {
            String keyGen = getKeyGen(request);
            String encryptedPassword = generateEncryptionPassword.generateEncryptedPassword(keyGen,
                    personalInfo.getPassword());
            personalInfo.setPassword(encryptedPassword);

            String encryptedUsername = generateEncryptionPassword.generateEncryptedPassword(keyGen,
                    personalInfo.getUsername());
            personalInfo.setUsername(encryptedUsername);

            personalInfo.setShortName(personalInfo.getShortName().toUpperCase());
            personalInfo.setCreatedBy(loggedInUser);
            personalInfo.setCreationDate(new Timestamp(System.currentTimeMillis()));

            Categories categories = categoriesRepository.findByCategoryName(personalInfo.getApplicationType());
            categories.setAppCount(categories.getAppCount() + 1);
            personalInfo.setCategories(categories);

            personalInfoRepository.save(personalInfo);
            return new ModelAndView("home");
        }

        return login();
    }

    @GetMapping(value = "retrieve")
    private ModelAndView getDetails() {
        return new ModelAndView("viewDetails", "personalInfoObj",
                new PersonalInfo());
    }

    @PostMapping(value = "retrieve")
    private ModelAndView retrieveDetails(@ModelAttribute PersonalInfo personalInfo,
                                        HttpServletRequest request) {

        List<PersonalInfo> personalInfoList;
        List<PersonalInfo> finalList = new ArrayList<>();
        String keyGen = getKeyGen(request);

        personalInfoList = personalInfoRepository.findByShortNameContaining(personalInfo.getShortName().toUpperCase());

        for (PersonalInfo personalInfo1 : personalInfoList) {
            String decryptPass = generateDecryptionPassword.generatedecryptionPassword(keyGen, personalInfo1.getPassword());
            String decryptUser = generateDecryptionPassword.generatedecryptionPassword(keyGen, personalInfo1.getUsername());

            personalInfo1.setPassword(decryptPass);
            personalInfo1.setUsername(decryptUser);

            finalList.add(personalInfo1);
        }
        List<Categories> categoriesList = (List<Categories>) categoriesRepository.findAll();

//        System.out.println("Category based Applications");
//        System.out.println("===========================");
//
//        for(Categories categories: categoriesList) {
//            if(categories.getAppCount() > 0){
//                for(PersonalInfo personalInfo1: finalList) {
//                    System.out.println(personalInfo1.getCategories().getCategoryName());
//                    System.out.println("------------------------");
//                    if(categories.getSystemCategoriesId() == personalInfo1.getCategories().getSystemCategoriesId()) {
//
//                        System.out.println(personalInfo1.getShortName());
//                    }
//                }
//                System.out.println("------------------------");
//            }
//        }
//        System.out.println("===========================");

        ModelAndView mav = new ModelAndView("detailsView");
        mav.addObject("personalInfoObj", finalList);
        mav.addObject("categoriesList", categoriesList);

        return mav;
    }

    @GetMapping(value = "delete/{id}")
    private ModelAndView deleteDetails(@PathVariable int id, HttpServletRequest request) {
        if(null != getLoggedInUser(request)) {
            personalInfoRepository.delete(id);
            return getDetails();
        }
        return login();
    }

    @GetMapping(value = "addCategory")
    private ModelAndView addCategoryHome(HttpServletRequest request){
        LoginUser loggedInUser = (LoginUser) request.getSession().getAttribute("loggedInUser");
        if(null != loggedInUser){
            ModelAndView mav = new ModelAndView("category");
            mav.addObject("categoryObj", new Categories());
            mav.addObject("categoriesListObj", categoriesRepository.findAll());
            return mav;
        }
        return login();
    }

    @PostMapping(value = "addCategory")
    private ModelAndView addCategory(@ModelAttribute Categories categories, HttpServletRequest request){
        categoriesRepository.save(categories);
        return addCategoryHome(request);
    }

    @GetMapping(value = "deleteCategory/{id}")
    public ModelAndView deleteCategory(@PathVariable int id, HttpServletRequest request) {
        if(null != getLoggedInUser(request)) {
            categoriesRepository.delete(id);
            return addCategoryHome(request);
        }
        return login();
    }


}
