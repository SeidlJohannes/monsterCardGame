package at.fhtw.mcg.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {
    @JsonAlias({"cardName"})
    private String cardName;
    @JsonAlias({"type"})
    private String type;
    @JsonAlias({"damage"})
    private int damage;
    @JsonAlias({"element"})
    private String element;
    @JsonAlias({"coins"})
    private int coins;

    private void tradeCard(){};
}
