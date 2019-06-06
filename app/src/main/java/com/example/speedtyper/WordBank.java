package com.example.speedtyper;

public class WordBank {

    private String[] fruitBank = new String[] {
            "apple",
            "grape",
            "pear",
            "pineapple",
            "kiwi",
            "strawberry"
    };

    private String[] animalBank = new String[] {
            "dog",
            "cat",
            "bird",
            "elephant"
    };

    private String[] randomBank = new String[] {
            "table",
            "wallet",
            "sky",
            "locker",
            "train",
            "galaxy",
            "space"
    };

    public String[] getFruitBank() {
        return fruitBank;
    }

    public String[] getAnimalBank() {
        return animalBank;
    }

    public String[] getRandomBank() {
        return randomBank;
    }
}
