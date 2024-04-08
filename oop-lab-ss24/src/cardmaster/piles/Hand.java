package cardmaster.piles;

import cardmaster.cards.Card;

public class Hand extends Pile{
    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }


    public void removeCard(Card card) {
        cards.remove(card);
    }


    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void reset() {

    }


    public Card getCard(int index) {
        return cards.get(index);
    }


    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public int getCount(){
        return cards.getSize();
    }
}
