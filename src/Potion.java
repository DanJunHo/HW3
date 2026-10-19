public class Potion {
    String name;
    int level;
    int hpUp;
    int gold;
    int reSell = gold / 2;
    int count = 3;  // 한 전투당 사용 가능한 개수

    Potion() { }

    Potion(String name, int level, int hpUp, int gold) {
        this.name = name;
        this.level = level;
        this.hpUp = hpUp;
        this.gold = gold;
    }
}
