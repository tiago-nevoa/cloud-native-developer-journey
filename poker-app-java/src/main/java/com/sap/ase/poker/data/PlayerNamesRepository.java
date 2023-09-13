package com.sap.ase.poker.data;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerNamesRepository {
    private final Map<String, String> playerIdToName = new HashMap<>();

    public PlayerNamesRepository() {
        playerIdToName.put("al-capone", "Al Capone");
        playerIdToName.put("pat-garret", "Pat Garret");
        playerIdToName.put("wyatt-earp", "Wyatt Earp");
        playerIdToName.put("doc-holiday", "Doc Holiday");
        playerIdToName.put("wild-bill", "Wild Bill");
        playerIdToName.put("stu-ungar", "Stu Ungar");
        playerIdToName.put("kitty-leroy", "Kitty Leroy");
        playerIdToName.put("poker-alice", "Poker Alice");
        playerIdToName.put("madame-moustache", "Madame Moustache");
    }

    public String getNameForId(String id) {
        return playerIdToName.getOrDefault(id, "Unknown");
    }
}
