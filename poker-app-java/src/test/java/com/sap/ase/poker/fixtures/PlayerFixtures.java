package com.sap.ase.poker.fixtures;

import com.sap.ase.poker.model.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerFixtures {
    public static Player AL_CAPONE() {
        return new Player("al-capone", "Al Capone", 100);
    }

    public static Player PAT_GARRETT() {
        return new Player("pat-garrett", "Pat Garrett", 100);
    }

    public static Player ALISON_GARDENER() {
        return new Player("alison-gardener", "Alison Gardener", 100);
    }

    public static List<Player> PLAYER_LIST_OF_2() {
        return Arrays.asList(PlayerFixtures.AL_CAPONE(), PlayerFixtures.PAT_GARRETT());
    }

    public static List<Player> PLAYER_LIST_OF_3() {
        return Arrays.asList(
                PlayerFixtures.AL_CAPONE(), PlayerFixtures.PAT_GARRETT(), PlayerFixtures.ALISON_GARDENER());
    }
}
