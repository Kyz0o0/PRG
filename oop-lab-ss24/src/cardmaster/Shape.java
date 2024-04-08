package cardmaster;

import java.util.Random;

public enum Shape {
    CIRCLE,
    STAR,
    SQUARE;


    public static Shape getRandomShape(){
        Random random = new Random();
        int rnd = random.nextInt(values().length);
        return values()[rnd];
    }
}
