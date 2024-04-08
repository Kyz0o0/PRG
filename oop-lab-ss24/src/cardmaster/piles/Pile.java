package cardmaster.piles;
import cardmaster.cards.Card;
import cardmaster.collections.AlgoArrayList;



public abstract class Pile {

    public AlgoArrayList<Card> cards = new AlgoArrayList<>();
    public abstract boolean isEmpty();
    public abstract void addCard(Card card);
    public abstract int getSize();
    public abstract void reset();

}
