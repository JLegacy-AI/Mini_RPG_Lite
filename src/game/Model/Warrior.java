package game.Model;

import java.util.ArrayList;
import java.util.List;

public class Warrior extends Hero {
    public Warrior(Builder b) {
        this.name = b.name;
        this.armor = b.armor;
        this.lifePoints = b.lifePoints;
        this.weaponDamage = b.weaponDamage;
        this.lembas = b.lembas;
        this.potions = b.potions;
        loadFood();
    }

    public int getManaCost() {
        return -1;
    }

    public int getManaPoints() {
        return -1;
    }

    @Override
    public void attack(Enemy e) {
        this.lifePoints -= e.getDamage();
    }

    @Override
    public void defend(Enemy e) {
        int extraEffect = this.armor - e.getDamage();
        if (extraEffect < 0) {
            this.lifePoints += extraEffect;
        }
    }

    @Override
    public void useConsumable(Consumable con) {
        this.lifePoints += con.consume();
    }

    public static class Builder {
        public int lifePoints;
        public int armor;
        public String name;
        public int weaponDamage;
        public List<Potion> potions;
        public List<Food> lembas;

        public Builder(int lifePoints) {
            this.lifePoints = lifePoints;
            potions = new ArrayList<>();
            lembas = new ArrayList<>();
        }

        public Builder setArmor(int armor) {
            this.armor = armor;
            return this;
        }

        public Builder setName(String n) {
            this.name = n;
            return this;
        }

        public Builder setWeaponDamage(int wd) {
            this.weaponDamage = wd;
            return this;
        }

        public Warrior build() {
            return new Warrior(this);
        }
    }
}
