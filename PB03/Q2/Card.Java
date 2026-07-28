import java.util.Objects;
public class Card{
    private String rank;
    private String suit;
    public Card(String rank, String suit){
        this.rank = rank;
        this.suit = suit;
    }
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    @Override
    public boolean equals(Object object){
        if (this==object) {
            return true;
        }
        if (object==null || getClass()!=object.getClass()){
            return false;
        }
        Card card = (Card) object;
        return rank.equals(card.rank) && suit.equals(card.suit);
    }
    @Override
    public int hashCode(){
        return Objects.hash(rank, suit);
    }
}