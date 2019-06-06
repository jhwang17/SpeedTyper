package com.example.speedtyper;

public class WordBank {

    private String[] easyBank = new String[] {
            "apple", "grape", "pear", "kiwi", "dog", "cat", "bird", "table", "wallet", "sky", "locker",
            "train", "galaxy",
            "space"
    };

    private String[] mediumBank = new String[] {
            "elephant", "expunge", "pineapple", "strawberry", "imagine", "religion", "deceit", "bulbasaur",
            "ivysaur", "caterpie", "pidgey", "poliwag", "vulpix", "diglett", "rattata"
    };

    private String[] hardBank = new String[] {
            "abject", "abnegation", "anachronistic", "antediluvian", "camaraderie", "circumlocution",
            "utilitarian", "vicissitude", "zephyr", "tirade", "paradigm", "ostensible", "obstreperous",
            "ubiquitous", "multifarious", "grandiloquent", "intransigent", "legerdemain",
            "mendacious"
    };

    public String[] getEasyBank() {
        return easyBank;
    }

    public String[] getMediumBank() {
        return mediumBank;
    }

    public String[] getHardBank() {
        return hardBank;
    }
}
