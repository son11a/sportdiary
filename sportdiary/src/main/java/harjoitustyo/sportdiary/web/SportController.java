package harjoitustyo.sportdiary.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import harjoitustyo.sportdiary.domain.CategoryRepository;
import harjoitustyo.sportdiary.domain.Category;
import harjoitustyo.sportdiary.domain.Sport;
import harjoitustyo.sportdiary.domain.SportRepository;
import harjoitustyo.sportdiary.domain.User;
import harjoitustyo.sportdiary.domain.UserRepository;


@Controller
public class SportController {


 @Autowired
    private UserRepository userRepository;

@Autowired
private SportRepository sportRepository;

@Autowired 
private CategoryRepository categoryRepository;

    @GetMapping("/userlist")
   public String userList(Model model, Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            model.addAttribute("users", userRepository.findAll());
        } else {
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            model.addAttribute("users", java.util.List.of(user));
        }

        return "userlist";
    }

@GetMapping("/sportlist/{id}")
public String getSportList(@PathVariable("id") Long userid, Model model) {

    model.addAttribute("sports", sportRepository.findByUserId(userid));
   model.addAttribute("userid", userid);
    return "sportlist";
}

@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
public String deleteSport(@PathVariable("id") Long id, Model model) {
	Sport sport = sportRepository.findById(id).orElseThrow();
    Long userId = sport.getUser().getId();
    sportRepository.delete(sport);
    return "redirect:/sportlist/" + userId;
}

@GetMapping("/addsport/{id}")
public String addSport(@PathVariable("id") Long userid, Model model) {

    Sport sport = new Sport();
    sport.setUser(userRepository.findById(userid).get());

    model.addAttribute("sport", sport);
    model.addAttribute("categories", categoryRepository.findAll());

    return "addsport";
}

@PostMapping("/savesport")
public String saveSport(Sport sport) {

    sportRepository.save(sport);

    return "redirect:/sportlist/" + sport.getUser().getId();
}

   @GetMapping("/login")
    public String login() {
        return "login"; 
    }
}
