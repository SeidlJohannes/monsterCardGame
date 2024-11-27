package at.fhtw.mcg.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.ArrayList;

public class Package {
    @JsonAlias({"cards"})
    private ArrayList<Card> cards;
    @JsonAlias({"cost"})
    private int cost;
}
