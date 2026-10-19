import java.util.Scanner;

public class Battle {
    int turn;
    int skillTurnA;
    boolean skillC_count = true;    // 궁극기 한턴에 한번 사용 가능

    void start(Player player, Monster monster, Scanner sc) {
        System.out.println(monster.name + "과(와)의 전투가 시작되었습니다.");
        turn = 1;
        player.potionCount = 4;

        if (player.clssNum == 1) {
            skillTurnA = -3;
        }
        else if (player.clssNum == 2) {
            skillTurnA = -2;
        }

        if (player.clssNum == 3 && player.skill[1].availability) {
            System.out.println(player.skill[1].name + " 에 의해 몬스터의 방어력이 감소합니다!");
            monster.armor = monster.armor / 2;
        }

        while (true) {
            // 턴 시작 전 플레이어와 몬스터 상태 확인 (원래있던자리)
            if (player.hp == 0) {
                System.out.println(player.name + " 이(가) " + monster.name + " 에 의해 사망하였습니다.");
                player.deathPenalty();
                return;
            }
            if (monster.hp == 0) {
                player.win(monster);
                monster.hp = monster.maxHp;
                return;
            }

            System.out.println("================= " + turn + "턴 =================");
            // 플레이어 턴

            boolean success = true;
            while (success) {   // success 가 false인 경우. 즉, 성공한 경우 반복이 멈춤
                System.out.println("1. 일반공격\n2. 스킬공격\n3. 물약사용\n4. 후퇴하기");
                int choice = sc.nextInt();

                if (choice == 1) { // 1) 일반공격
                    player.attack(monster);
                    success = false;
                } else if (choice == 2) { // 2) 스킬공격
                    success = skillAttack(player, monster, sc);
                } else if (choice == 3) { // 3) 물약사용
                    success = player.usePotion(true, sc);
                } else if (choice == 4) { // 4) 후퇴
                    return;
                } else {
                    System.out.println("입력오류: 올바른 숫자를 입력해주세요");
                }
            }
            // 몬스터 턴
            if (monster.hp != 0) {
                player.takeDamage(monster);
            }
            turn = turn + 1;
        }
    }

    boolean skillAttack(Player player, Monster monster, Scanner sc) {
        boolean success = true;
        while (success) {
            player.skill[0].showSkill(player, this);
            System.out.println("사용하실 스킬을 입력해주세요. (뒤로가기: 4)");
            int skillNum = sc.nextInt();
            int skillIndex = skillNum - 1;
            if (skillNum == 1) {
                success = player.skill[0].skillA(player, monster, this);
                return success;
            }
            if (skillNum == 2) {
                success = player.skill[1].skillB(player, monster, this);
                return success;
            } else if (skillNum == 3) {
                success = player.skill[2].skillC(player, monster, this);
                return success;

            } else if (skillNum == 4) {
                return true;
            }
        }
        return success;
    }
}
