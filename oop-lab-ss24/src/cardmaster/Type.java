package cardmaster;

public enum Type {
    CHANCE ("Chance");

    private final String name;

    Type(String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}
