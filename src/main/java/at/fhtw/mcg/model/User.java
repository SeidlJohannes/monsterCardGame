package at.fhtw.mcg.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @JsonAlias({"id"})
    private Integer id;
    @JsonAlias({"username"})
    private String username;
    @JsonAlias({"password"})
    private String password;
    @JsonAlias({"coins"})
    private Integer coins;
    @JsonAlias({"stack"})
    private ArrayList<Card> stack; //after buying 4 packages contains 20 cards
    @JsonAlias({"deck"})
    private ArrayList<Card> deck; //consists of 4 cards picked from stack
    @JsonAlias({"token"})
    private String token;

    private void buildDeck(){};
    private void buyPackage(){};
    private void battle(){};
}
