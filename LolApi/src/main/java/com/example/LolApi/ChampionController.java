package com.example.LolApi;

import com.example.LolApi.ChampionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ChampionController {

    private final ChampionService championService;

    public ChampionController(ChampionService championService) {
        this.championService = championService;
    }

    @GetMapping("/random-champion")
    public String getRandomChampion(Model model) {
        Map<String, String> champion = championService.getRandomChampion();
        model.addAttribute("championName", champion.get("name")); // Se usará para la validación
        model.addAttribute("splashArt", champion.get("splashArt"));
        return "random-champion";
    }

    @GetMapping("/check-answer")
    public String checkAnswer(@RequestParam String guess, @RequestParam String correctName, Model model) {
        boolean isCorrect = guess.equalsIgnoreCase(correctName);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("correctName", correctName);
        return "result";
    }
}
