package game.Model;
/**
 * This class inerited by two other classes (BasicEnemy / Boss)
 * Attribute lifePoints - life point of an enemy
 * @author (Paul)
 * @version (v1.0 - 5/2/2022)
 */
public abstract class Enemy
{
    //lifePoints Of Enemy Attribute
    protected int lifePoints;
    protected String name;
    protected int damage;

    public int getLifePoints(){
        return this.lifePoints;
    }
    
    public int getDamage(){
        return this.damage;
    }
    
    public String getName(){
        return this.name;
    }

    /**
     * This method provide damage on Enemies
     * @param h - hero who attack on Enemy
     */
    public void damage(Hero h){
        lifePoints -= h.getWeaponDamage();
    }

    @Override 
    public String toString(){
        return "[ "+this.name+", "+this.damage+", "+this.lifePoints+" ]"; 
    }
}
