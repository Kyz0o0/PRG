package cardmaster.piles;

import cardmaster.cards.Card;
import cardmaster.collections.AlgoArrayList;

public class DiscardPile extends Pile{



    public boolean isEmpty(){
        return cards.isEmpty();
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public int getSize() {
        return cards.getSize();
    }

    @Override
    public void reset() {
        cards = new AlgoArrayList<>();
    }

    public Card[] getCards(){
        Card[] help = new Card[cards.getSize()];
        System.arraycopy(cards.toArray(),0 , help, 0, cards.getSize());
        return help;
    }


}
