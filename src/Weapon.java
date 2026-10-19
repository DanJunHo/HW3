public class Weapon {
    String name;
    int level;
    String clss;
    int attack;
    int gold;
    int reSell = gold / 2;

    Weapon() {
    }
    Weapon(String name, String clss, int level, int attack, int gold) {
        this.name = name;
        this.clss = clss;
        this.level = level;
        this.attack = attack;
        this.gold = gold;
    }
}
