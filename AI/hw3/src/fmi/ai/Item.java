package fmi.ai;

import java.util.Arrays;
import java.util.List;

enum Item {
    MAP(100, 150),
    COMPASS(120, 40),
    MAGIC_WATER(1600, 200),
    MAGIC_BOOTS(700, 160),
    MAGIC_BOW(150, 60),
    FLYING_CARPET(680, 45),
    MAGIC_RING(270, 60),
    MAGIC_GLASSES(385, 48),
    LIFE_ELIXIR(230, 30),
    MAGIC_ANIMAL(520, 10),
    SWORD(1700, 400),
    SHIELD(500, 300),
    TELEPORT(240, 15),
    MAGIC_BERRIES(480, 10),
    MAGIC_UMBRELLA(730, 40),
    FLOWER(420, 70),
    KNIFE(430, 75),
    TENT(220, 80),
    GARLIC(70, 20),
    SILVER_ELIXIR(180, 12),
    MAGIC_HAT(40, 50),
    MAGIC_BEER(300, 10),
    SILVER_ARROWS(900, 20),
    MYSTIC_ORB(2000, 150);

    private final int weight;
    private final int value;

    Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public static List<Item> getAll() {
        return Arrays.asList(Item.values());
    }
}
