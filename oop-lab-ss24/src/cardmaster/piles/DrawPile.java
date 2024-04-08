package cardmaster.piles;

import cardmaster.cards.Card;

import java.util.Arrays;

public class DrawPile extends Pile {


    @Override
    public boolean isEmpty() {
        return cards.getSize() == 0;
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getTopCard() {
        return cards.pop();
    }

    @Override
    public int getSize() {
        return cards.getSize();
    }

    public void shuffle() {
        /*int size = cards.getSize();
        Random random = new Random();
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            cards.swap(i, j);
        }*/
        cards.shuffle();
    }


    @Override
    public void reset() {

    }

    @Override
    public String toString(){
        return Arrays.toString(cards.toArray());
    }


}
