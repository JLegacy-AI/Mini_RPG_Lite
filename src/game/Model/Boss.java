package game.Model;
/**
 * @author (Paul)
 * @version (v1.0 - 5/2/2022)
 */
public class Boss extends Enemy
{
    public Boss(Builder b){
        this.lifePoints = b.lifePoints;
        this.name = b.name;
        this.damage = b.damage;
    }
    
    /**
     * This class helps in building Boss Class
     * this class has all those attributes which outer class has
     * @author  (Paul)
     * @version (v1.0 - 5/2/2022)
     */
    public static class Builder{

        public int lifePoints;
        public int damage;
        public String name;
        
        public Builder(int lp){
            this.lifePoints = lp;
        }
        
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        
        public Builder setDamage(int damage){
            this.damage = damage;
            return this;
        }
        
        public Boss build(){
            return new Boss(this);
        }
    }
}
