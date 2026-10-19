public class Monster {
    String name;
    int level;
    int hp;
    int maxHp;
    int attack;
    int armor;
    int expReward;
    int goldReward;

    Monster(String name, int level, int hp, int maxHp, int attack, int  armor) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attack = attack;
        this.armor = armor;
        this.expReward = level * 30;
        this.goldReward = level * 10;
    }
}
