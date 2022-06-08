package game.Model;

import java.util.List;

public interface GameFactory {
    boolean lose();

    boolean win();

    List<Hero> getHeroList();

    List<Enemy> getEnemyList();

    String attack();

    String defend();

    String consumeFood();

    String consumePotion();

    String increaseArmor();

    String increaseWeaponDamage();

    String increaseFoodAndPotionEffectiveness();

    String increasePotionAndFoodNumber();

    String increaseArrows();

    String decreaseManaCost();

    String newCombat();
}
