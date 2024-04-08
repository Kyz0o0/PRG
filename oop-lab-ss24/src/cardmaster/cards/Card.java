package cardmaster.cards;

import cardmaster.Shape;
import cardmaster.Type;

/**
 * <h1>Punkte</h1> 0.5 Punkte für jeden nicht leeren Ablagestapel und 0.5 Punkte
 * für jede einzigartige Form.
 * <p>
 * Beispiele:
 * 
 * <pre>
 *   -
 *   - Stern < gelegt
 *   -
 * </pre>
 * 
 * 0.5 für nicht leere Stapel + 0.5 für die Form Stern
 * 
 * <pre>
 * -Kreis - Stern < gelegt - Kreis
 * </pre>
 * 
 * 1.5 für nicht leere Stapel + 1.0 für die Formen Kreis und Stern.
 */
public class Card {

	private Shape shape;
	private Type type;

	public Card(Shape shape) {
		this.shape = shape;
		this.type = Type.CHANCE;
	}

	/**
	 * Gibt die Form der Karte zurück.
	 */
	public Shape getShape() {
		return this.shape;
	}

	/**
	 * Gibt den Namen der Karte zurück. z.B.: Chance
	 */
	public String getName() {
		return this.type.toString();
	}

	@Override
	public String toString(){
		return String.format("Card with shape %s and type %s", shape.toString(), type.toString());
	}
}
