package cardmaster;

import cardmaster.cards.Card;
import cardmaster.collections.AlgoArrayList;

import java.util.Random;

public class Shop {
    AlgoArrayList<Card> shopCards = new AlgoArrayList<>();
    private int prices[];
    int limit;

    public Shop(float credits){
        limit = 5;
        for (int i = 0; i < limit; i++){
            shopCards.add(new Card(Shape.getRandomShape()));
        }
        prices = calculatePrices(credits);

    }



    public Card buy(int i){

        Card temp = shopCards.get(i);
        shopCards.remove(i);
        return temp;

    }



    public int[] calculatePrices(float credits){
        Random rand = new Random();
        int[] prices = new int[shopCards.getSize()];
        float mean = credits/4;
        for (int i = 0; i < prices.length; i++) {
            prices[i] = Math.round(mean * ((rand.nextFloat(0.8f, 1.2f))));
            prices[i] += rand.nextInt(-1, 2);
            prices[i] = prices[i] <= 0 ? 1 : prices[i];
        }
        return prices;
    }

    public boolean isEmpty(){
        return shopCards.isEmpty();
    }

    public int getShopItemCount(){
        return shopCards.getSize();
    }

    public int getPrice(int index){
        return this.prices[index];
    }

    public String getCardDecription(int shopItemIndex) {
        return shopCards.get(shopItemIndex).toString() + " with a cost of " + prices[shopItemIndex] + " credits.";
    }
}
