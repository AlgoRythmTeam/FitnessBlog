package softuniBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import softuniBlog.bindingModel.UserInfoBindingModel;
import softuniBlog.entity.User;
import softuniBlog.entity.UserInfo;
import softuniBlog.repository.*;

@Controller
public class UserInfoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;


    @GetMapping("/userInfo/dataInput")
    @PreAuthorize("isAuthenticated()")
    public String dataInput(Model model) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User entityUser = this.userRepository.findByEmail(principal.getUsername());

        model.addAttribute("user", entityUser);

        UserInfo userInfo=entityUser.getUserData();

        if (userInfo!=null){

            model.addAttribute("userInfo", userInfo);

            model.addAttribute("view", "userInfo/dataInputExist");
        } else {
            model.addAttribute("view", "userInfo/dataInput");
        }

        return "base-layout";

    }

    @PostMapping("/userInfo/dataInput")
    @PreAuthorize("isAuthenticated()")
    public String dataInputProcess(Model model, UserInfoBindingModel userInfoBindingModel) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User entityUser = this.userRepository.findByEmail(principal.getUsername());

        boolean sex = userInfoBindingModel.isSex();

        UserInfo userInfo=entityUser.getUserData();

        if (userInfo!=null){

            this.userInfoRepository.delete(userInfo);
        }

        UserInfo userInfoEntity = new UserInfo(
                userInfoBindingModel.getMass(),
                userInfoBindingModel.getHeight(),
                userInfoBindingModel.getAge(),
                sex,
                entityUser);

        this.userInfoRepository.saveAndFlush(userInfoEntity);

        return "redirect:/profile";
    }

}
