package com.example.LolApi;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ChampionService {

	private final RestTemplate restTemplate;

	public ChampionService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Map<String, String> getRandomChampion() {
		String version = "15.6.1"; // Usa la última versión del Data Dragon
		String url = "https://ddragon.leagueoflegends.com/cdn/" + version + "/data/es_ES/champion.json";

		Map<String, Object> response = restTemplate.getForObject(url, Map.class);
		Map<String, Object> data = (Map<String, Object>) response.get("data");

		List<String> championKeys = List.copyOf(data.keySet());
		Random random = new Random();
		String championName = championKeys.get(random.nextInt(championKeys.size()));

		String championDetailsUrl = "https://ddragon.leagueoflegends.com/cdn/" + version + "/data/es_ES/champion/" + championName + ".json";
		System.out.println(championDetailsUrl);
		Map<String, Object> championDetailsResponse = restTemplate.getForObject(championDetailsUrl, Map.class);
		Map<String, Object> championData = (Map<String, Object>) championDetailsResponse.get("data");
		Map<String, Object> championInfo = (Map<String, Object>) championData.get(championName);

		List<Map<String, Object>> skins = (List<Map<String, Object>>) championInfo.get("skins");
		List<Map<String, Object>> spells = (List<Map<String, Object>>) championInfo.get("spells");

		String lore = (String) championInfo.get("blurb");
		String loremodified = lore.replace(championName, "XXX");

		Map<String, Object> randomSpell = spells.get(random.nextInt(spells.size()));
		String spellName = (String) randomSpell.get("name");
		String spellImage = (String) ((Map<String, Object>) randomSpell.get("image")).get("full");



		String spellImageUrl = "https://ddragon.leagueoflegends.com/cdn/" + version + "/img/spell/" + spellImage; //imagen del spell


		String splashArtUrl = null;
		String skinName = null;
		while (splashArtUrl == null) {

			Map<String, Object> randomSkin = skins.get(random.nextInt(skins.size()));
			int skinNumber = (int) randomSkin.get("num");

			splashArtUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + championName + "_" + skinNumber + ".jpg";

			skinName = (String) randomSkin.get("name");
		}

		return Map.of(
				"name", championName,
				"skinName", skinName,
				"splashArt", splashArtUrl,
				"spellName", spellName,
				"spellImage", spellImageUrl,
				"lore", loremodified
		);
	}
}