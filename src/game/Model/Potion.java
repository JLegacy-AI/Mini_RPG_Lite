package game.Model;
public class Potion implements Consumable
{
    private int manaPoints;
    
    public Potion(Builder b){
        this.manaPoints = b.manaPoints;
    }
    
    public void increaseEffectiveness(){
        this.manaPoints += 1;
    }
    
    @Override
    public int consume(){
        return this.manaPoints;
    }
    
    public static class Builder{
        public int manaPoints;
        
        public Builder(int manaPoints){
            this.manaPoints = manaPoints;
        }
        
        public Potion build(){
            return new Potion(this);
        }
    }
}
