package cardmaster;

import cardmaster.cards.Card;
import cardmaster.collections.AlgoArrayList;
import cardmaster.piles.DiscardPile;
import cardmaster.piles.DrawPile;
import cardmaster.piles.Hand;

/**
 * Klasse für die Durchführung eines Spiels. Ein neues Spiel beginnt in Runde 1
 * im Modus {@link Mode#SHOPPING}. Nun muss mindestens eine Karte gekauft
 * werden. Dafür können die Methode für den Shopping-Modus (siehe unten)
 * verwendet werden. Der Nutzer dieser Klasse muss {@link #endShopping()}
 * aufrufen, um in den Playing-Modus ({@link Mode#PLAYING}) zu wechseln. Nun
 * werden die Methoden für den Playing-Modus verwendet, um wiederholt eine Karte
 * aus der Hand zu spielen ({@link #play(Card, int)}). Beim Wechseln in den
 * Playing-Modus und nach Legen einer Karte, wird die Hand wieder aufgefüllt,
 * sofern möglich. Wurde die letzte Karte aus der Hand gelegt, wird entweder,
 * falls das die letzte Runde war, in den End-Modus ({@link Mode#END})
 * gewechselt oder zurück in den Shopping-Modus. Beim Starten des Shopping-Modus
 * wird der Shop mit komplett zufälligen neuen Gegenständen gefüllt. Am Anfang
 * passen fünf Gegenstände in den Shop.
 */
public class Game {

	float credits;
	int roundsLeft;
	Mode mode;
	Hand hand = new Hand();
	DrawPile drawPile = new DrawPile();
	DiscardPile[] discardPiles = {new DiscardPile(), new DiscardPile(), new DiscardPile()};
	Shop shop;



	/**
	 * Erzeugt ein neues Spiel.
	 *
	 * @param maxRounds Anzahl der Runden. Muss mindestens {@code 1} sein.
	 */
	public Game(int maxRounds) {
		assert maxRounds >= 1 : "maxRounds must be at least 1";
		this.credits = 10;
		this.mode = Mode.SHOPPING;
		this.roundsLeft = maxRounds;
		this.shop = new Shop(credits);
	}

	// Allgemein verwendbare Methoden.

	/**
	 * Gibt den aktuellen Punktestand zurück.
	 */
	public double getCredits() {
		return this.credits;

	}

	/**
	 * Gibt den aktuellen Modus zurück.
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * Gibt die Anzahl an Ablagestapel zurück.
	 */
	public int getStacksCount() {
		if(discardPiles != null){
			return discardPiles.length;
		}
		return 0;
	}

	// Methoden für den Shopping-Modus

	/**
	 * Gibt zurück, ob der Shop leer gekauft wurde.
	 */
	public boolean isShopEmpty() {
		return shop.isEmpty();
	}

	/**
	 * Gibt die Anzahl der noch im Shop verfügbaren Gegenstände an.
	 */
	public int getShopItemCount() {
		return shop.getShopItemCount();
	}

	/**
	 * Gibt eine benutzerfreundliche Darstellung für die Konsole des Gegenstands mit
	 * index {@code shopItemIndex} zurück.
	 *
	 * @param shopItemIndex index aus dem Intervall
	 *                      {@code [0, this.getShopItemCount())}.
	 */
	public String getShopItemDescription(int shopItemIndex) {
		return shop.getCardDecription(shopItemIndex);
	}

	/**
	 * Gibt den Preis des Gegenstands mit index {@code shopItemIndex} zurück.
	 *
	 * @param shopItemIndex index aus dem Intervall
	 *                      {@code [0, this.getShopItemCount())}.
	 */
	public int getShopItemPrice(int shopItemIndex) {
		return shop.getPrice(shopItemIndex);
	}

	/**
	 * Versucht den Gegenstand an index {@code shopItemIndex} zu kaufen und entfernt
	 * den Gegenstand bei Erfolg aus dem Sortiment.
	 * <p>
	 * Bei erfolgreichem Kauf können sich die Indexe der Gegenstände ändern. Alle
	 * vorherigen Rückgaben von {@link #getShopItemCount} und
	 * {@link #getShopItemDescription(int)} sind nicht mehr gültig.
	 *
	 * @param shopItemIndex index aus dem Intervall
	 *                      {@code [0, this.getShopItemCount())}.
	 * @return Ob der Gegenstand gekauft wurde. Bei einem zu geringen Punktestand
	 *         hingegen {@code false}.
	 */
	public boolean buy(int shopItemIndex) {
		if(credits >= getShopItemPrice(shopItemIndex)){
			drawPile.addCard(shop.buy(shopItemIndex));
			credits -= getShopItemPrice(shopItemIndex);
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Gibt zurück, ob auf dem Nachziehstapel keine Karte liegt.
	 * <p>
	 * Dies ist am Anfang des Spiels der Fall. Da diese Methode nur im Modus
	 * {@link Mode#SHOPPING} verwendet werden darf, ist die Rückgabe nach dem ersten
	 * Kartenkauf immer {@code true}.
	 */
	public boolean isDrawPileEmpty() {
		return drawPile.isEmpty();
	}

	/**
	 * Beendet die Shop-Interaktion und wechselt in den Playing-Modus. Wurde noch
	 * nie eine Karte gekauft, passiert nichts, da mindestens eine Karte fürs
	 * Spielen notwendig ist.
	 */
	public void endShopping() {
		if(!isDrawPileEmpty()) {
			changeToPlay();
		}
	}

	private void changeToPlay() {
		this.mode = Mode.PLAYING;
		System.out.println(drawPile.toString());
		drawPile.shuffle();
		System.out.println(drawPile.toString());
		for (int i = 0; i < 4; i++) {
			if (!isDrawPileEmpty()){
				hand.addCard(drawPile.cards.pop());
			}
		}
	}

	// Methoden für den Playing-Modus

	/**
	 * Gibt zurück, wie viele Karten aktuell auf der Hand gehalten werden.
	 */
	public int getHandCardsCount() {
		return hand.getCount();
	}

	/**
	 * Gibt die Handkarte mit index {@code handCardIndex} zurück.
	 *
	 * @param handCardIndex index aus dem Intervall
	 *                      {@code [0, this.getHandCardsCount())}.
	 */
	public Card getHandCard(int handCardIndex) {
		return hand.getCard(handCardIndex);
	}

	/**
	 * Legt die Handkarte auf den Ablagestapel mit index {@code stackIndex}. Stellen
	 * Sie mit assert sicher, dass die Karte auch wirklich auf der Hand ist.
	 * <p>
	 * Die Indexe der Karten kann sich hierbei ändern. Vor allem wird eine Karte -
	 * wenn möglich - nachgezogen. Dadurch sind alle vorherigen Rückgaben von
	 * {@link #getHandCardsCount()} und {@link #getHandCard(int)} nicht mehr gültig.
	 *
	 * @param card       die zu legende Handkarte.
	 * @param stackIndex index aus dem Intervall {@code [0, this.getStacksCount())}.
	 */
	public void play(Card card, int stackIndex) {

		assert stackIndex >= 0 && stackIndex < getStacksCount() : "Invalid stack index";
		assert hand.contains(card) : "Card not in hand";
		discardPiles[stackIndex].addCard(card);
		hand.removeCard(card);

		credits += calculateCredits();

		//check if draw pile not empty
		if(!isDrawPileEmpty()){
			hand.addCard(drawPile.cards.pop());
		}

		if(hand.isEmpty()){
			if(roundsLeft > 1){
				changeToShopping();

			}
			else {
				changeToEnd();
			}
		}

	}

	private void changeToEnd() {
		mode = Mode.END;
	}

	private void changeToShopping() {

		for(DiscardPile dp : discardPiles){
			 for (Card c : dp.cards){
			 	drawPile.addCard(c);
			 }
			dp.reset();
		}
		roundsLeft--;
		mode = Mode.SHOPPING;
		this.shop = new Shop(credits);
	}

	private float calculateCredits() {
		float c = 0;
		for(DiscardPile pile : discardPiles){
			if(!pile.isEmpty()){
				c += 0.5f;
			}
		}

		//count number unique symboles
		Shape[] shapes = getTopShapes();
		for (int i = 0; i < shapes.length; i++) {
			for (int j = 0; j < shapes.length; j++) {
				if(i != j){
					if(shapes[i] == shapes[j]){
						shapes[j] = null;
					}
				}
			}
		}

		for (Shape shape : shapes){
			if(shape != null){
				c += 0.5f;
			}
		}



		return c;
	}

	/**
	 * Liefert die Form der auf den Ablagestapeln liegenden Karten. An Index
	 * {@code i} ist die Form für den Stapel mit index {@code i} oder {@code null}.
	 * {@code I} ist aus de Intervall {@code [0, this.getStacksCount())}.
	 */
	public Shape[] getTopShapes() {
		AlgoArrayList<Shape> topShapes = new AlgoArrayList<Shape>();
		for (DiscardPile pile : discardPiles ){
			if(!pile.isEmpty()) {
				topShapes.add(pile.cards.get(pile.cards.getSize() - 1 ).getShape());
			}
		}
		Shape[] shapes = new Shape[topShapes.getSize()];
		System.arraycopy(topShapes.toArray(), 0, shapes, 0, topShapes.getSize());
		return shapes;


	}

	public enum Mode {
		SHOPPING, PLAYING, END;
	}
}
