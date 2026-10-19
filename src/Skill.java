import java.util.Scanner;

public class Skill {
    String name;                    // 스킬이름
    int level;
    boolean availability;   // 사용가능 여부
    int attack;     // 스킬 데미지

    Skill(String name, int level) {
        this.name = name;
        this.level = level;
    }

    void showSkill(Player player, Battle battle) {
        for (int i = 0; i < player.skill.length; i++) {
            if (player.skill[i].availability == false) {
                System.out.println(i + 1 + ". " + player.skill[i].name + " (습득레벨: " + player.skill[i].level + ")");
            } else {
                // 만약 전투 중 30레벨 스킬 사용했다면 이미 쓴 스킬이라고 띄우게하기.
                System.out.println(i+1 + ". " + player.skill[i].name + " (사용가능)");
            }
        }
    }

    boolean skillA(Player player, Monster monster, Battle battle) {
        // 전사 스킬
        if (player.clssNum == 1) {
            attack = (150*player.attack)/100;
            int damage = attack - monster.armor;
            if (damage <= 0) {
                damage = 0;
            }

            if (battle.turn - battle.skillTurnA >= 4) { // 스킬 턴 계산
                System.out.println(player.skill[0].name + " 발동!");
                monster.hp = monster.hp - damage;
                if (monster.hp <= 0) {
                    monster.hp = 0;
                }
                System.out.println(monster.name + "에게 " + damage + " 의 피해를 입혔습니다. (몬스터 남은 체력: " + monster.hp + ")");
                battle.skillTurnA = battle.turn;
                return false;
            } else {
                int skillCool = battle.turn - battle.skillTurnA;
                if (skillCool >= 4) {
                    skillCool = 4;
                }
                int coolTime = 4 - skillCool;
                System.out.println("스킬 쿨타임이 남았습니다. (남은 쿨타임: " + coolTime + ")");
                return true;
            }
        }
        // 궁수 스킬
        else if (player.clssNum == 2) {
            attack = (180*player.attack)/100;
            int damage = attack - monster.armor;
            if (damage <= 0) {
                damage = 0;
            }

            if (battle.turn - battle.skillTurnA >= 3) { // 스킬 턴 계산
                System.out.println(player.skill[0].name + " 발동!");
                monster.hp = monster.hp - damage;
                if (monster.hp <= 0) {
                    monster.hp = 0;
                }
                System.out.println(monster.name + "에게 " + damage + " 의 피해를 입혔습니다. (몬스터 남은 체력: " + monster.hp + ")");
                battle.skillTurnA = battle.turn;
                return false;
            } else {
                int skillCool = battle.turn - battle.skillTurnA;
                if (skillCool >= 3) {
                    skillCool = 3;
                }
                int coolTime = 3 - skillCool;
                System.out.println("스킬 쿨타임이 남았습니다. (남은 쿨타임: " + coolTime + ")");
                return true;
            }
        }
        // 법사 스킬
        else {
            System.out.println("해당스킬은 액티브스킬이 아닙니다.");
            return false;
        }
    }
    // 스킬B - 전사의 각성 : 습득 시 영구적으로 공격력 + 20 / 공격 시 입힌 피해의 20%만큼 체력회복
    boolean skillB(Player player, Monster monster, Battle battle) {
        if (player.clssNum == 1) {
            System.out.println("해당스킬은 액티브스킬이 아닙니다.");
            return true;
        }
        else if (player.clssNum == 2) {
            System.out.println("해당스킬은 액티브스킬이 아닙니다.");
            return true;
        }
        else {
            System.out.println("해당 스킬은 액티브스킬이 아닙니다.");
            return false;
        }
        }
    // 스킬C - 파멸의 일격 : 공격력의 400%로 피해를 입힘 / 전투당 1회만 사용가능
    boolean skillC(Player player, Monster monster, Battle battle) {
        if (battle.skillC_count == true) {
            // 전사, 궁수, 법사의 데미지 나누기
            if (player.clssNum == 1 || player.clssNum == 2) {
                attack = 4 * player.attack;
            }
            else {
                attack = 9 * player.attack;
            }

            int damage = attack - monster.armor;
            if (damage <= 0) {
                damage = 0;
            }
            System.out.println(player.skill[2].name + " 발동!");
            monster.hp = monster.hp - damage;
            if (monster.hp <= 0) {
                monster.hp = 0;
            }
            System.out.println(monster.name + "에게 " + damage + " 의 피해를 입혔습니다. (몬스터 남은 체력: " + monster.hp + ")");
            battle.skillC_count = false;
            return false;
        } else {
            System.out.println("해당 스킬은 전투당 한번 사용 가능합니다.");
            return true;
        }
    }

    }
