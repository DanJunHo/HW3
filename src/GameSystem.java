import java.util.Scanner;

public class GameSystem {
    Player player;
    WeaponStore weaponStore = new WeaponStore();
    PotionStore potionStore = new PotionStore();
    Dungeon[] dungeons = {
            new Dungeon("초보자의 숲", new Monster[]{
                    new Monster("슬라임", 1, 65, 65,14, 2),
                    new Monster("고블린", 3, 95, 95,18, 2),
                    new Monster("늑대", 5, 125, 125,22, 3)
            }, 1),
            new Dungeon("버려진 동굴", new Monster[]{
                    new Monster("박쥐", 6, 140, 140,24, 3),
                    new Monster("오크", 8, 170, 170,28, 4),
                    new Monster("동굴 거인", 10, 270, 270,40, 7)
            }, 5),
            new Dungeon("망자의 묘지", new Monster[]{
                    new Monster("해골 병사", 11, 215, 215,34, 5),
                    new Monster("망령", 13, 245, 245,38, 6),
                    new Monster("리치", 15, 290, 290,42, 8)
            }, 10),
            new Dungeon("불타는 협곡", new Monster[]{
                    new Monster("화염 정령", 16, 290, 290,44, 7),
                    new Monster("용암 골렘", 18, 330, 330,48, 8),
                    new Monster("화염의 수호자", 20, 460, 460,64, 11)
            }, 15),
            new Dungeon("얼어붙은 성채", new Monster[]{
                    new Monster("얼음 늑대", 21, 390, 390,54, 9),
                    new Monster("설인", 23, 420, 420,58, 10),
                    new Monster("서리 드래곤", 25, 510, 510,70, 13)
            }, 20),
            new Dungeon("마왕의 주둔지", new Monster[]{
                    new Monster("암흑 기사", 26, 440, 440,64, 10),
                    new Monster("마족 전사", 28, 470, 470,68, 11),
                    new Monster("마왕의 하수인", 30, 600, 600,83, 15)
            }, 25),
            new Dungeon("마왕의 성", new Monster[]{
                    new Monster("마왕", 35, 850, 850,100, 20)
            }, 30)
    };

    void start(Scanner sc) {
        System.out.println("============= G A M E S T A R T =============");
        System.out.println("게임을 시작하기 전에 설명을 들으시겠습니까?");
        System.out.println("1. 네 \n2. 아니요");
        int choice = sc.nextInt();
        if (choice == 1) {
            explainGame();
            createPlayer(sc);
        }
        else if (choice == 2) {
            createPlayer(sc);
            // villageMenu();
        }
        else {
            inputError();
            start(sc);
        }
    }

    void explainGame() {
        System.out.println("게임을 설명한다.");
    }

    void createPlayer(Scanner sc) {
        String name = buildName(sc);  // 캐릭터 이름 설정
        choiceClss(sc, name);         // 캐릭터 직업 설정
    }

    String buildName(Scanner sc) {
        System.out.println("사용하실 캐릭터의 이름을 정해주세요.");
        String name = sc.next();
        System.out.println("캐릭터의 이름을 " + name + "으로 하시겠습니까?");
        System.out.println("1. 네\n2. 아니요");
        int choice = sc.nextInt();
        if (choice == 1) {
            return name;
        }
        else if (choice == 2) {
            buildName(sc);
        }
        else {
            inputError();
            buildName(sc);
        }
        return name;
    }

    void choiceClss(Scanner sc, String name) {
        System.out.println("직업을 선택해주세요.");
        System.out.println("1. 전사\n2. 궁수\n3. 법사");

        int choice = sc.nextInt();
        if (choice == 1) {
            player = new Player(name, choice);
            System.out.println("전사 직업군을 선택하시겠습니까?");
            System.out.println("1. 네\n2. 아니요");
            int choice2 = sc.nextInt();
            if (choice2 == 1) {
                System.out.println("캐릭터 생성이 완료되었습니다.");
                player.showStat();
                villageMenu(player, sc);
            }
            else if (choice2 == 2) {
                choiceClss(sc, name);
            }
            else {
                inputError();
                choiceClss(sc, name);
            }
        }
        else if (choice == 2) {
            player = new Player(name, choice);
            System.out.println("궁수 직업군을 선택하시겠습니까?");
            System.out.println("1. 네\n2. 아니요");
            int choice2 = sc.nextInt();
            if (choice2 == 1) {
                System.out.println("캐릭터 생성이 완료되었습니다.");
                player.showStat();
                villageMenu(player, sc);
            }
            else if (choice2 == 2) {
                choiceClss(sc, name);
            }
            else {
                inputError();
                choiceClss(sc, name);
            }
        }
        else if (choice == 3) {
            player = new Player(name, choice);
            System.out.println("법사 직업군을 선택하시겠습니까?");
            System.out.println("1. 네\n2. 아니요");
            int choice2 = sc.nextInt();
            if (choice2 == 1) {
                System.out.println("캐릭터 생성이 완료되었습니다.");
                player.showStat();
                villageMenu(player, sc);
            }
            else if (choice2 == 2) {
                choiceClss(sc, name);
            }
            else {
                inputError();
                choiceClss(sc, name);
            }
        }
        else {
            inputError();
            choiceClss(sc, name);
        }
    } // choiceClss끝

    void villageMenu(Player player, Scanner sc) {
        while (true) {
            System.out.println("무엇을 하시겠습니까?");
            System.out.print("1. 스탯창 열기\n2. 인벤토리 열기\n3. 상점 이동\n4. 던전 이동\n5. 여관 이동");
            if (player.hp == 0 || player.hp <= player.maxHp/2) {
                System.out.println("(권장)");
            }
            else {
                System.out.println("");
            }

            int choice = sc.nextInt();
            if (choice == 1) { // 1. 스탯창 열기
                player.showStat();
            }
            else if (choice == 2) { // 2. 인벤토리 열기
                player.inventory.open(player, sc);
            }
            else if (choice == 3) { // 3. 상점 이동
                System.out.println("이동하실 상점을 선택하세요.\n1. 무기상점\n2. 소모품상점\n3. 뒤로가기");
                int choice2 = sc.nextInt();
                if (choice2 == 1) {
                    weaponStore.openStore(player, sc);
                }
                else if (choice2 == 2) {
                    potionStore.openStore(player, sc);
                }
                else {
                    inputError();
                }
            }
            else if (choice == 4) { // 4. 던전 이동
                choiceDungeon(sc);
            }
            else if (choice == 5) { // 5. 여관 이동
                System.out.println("여관으로 이동합니다.");
                player.hp = player.maxHp;   // 플레이어의 체력 회복
                System.out.println("플레이어의 체력이 회복되었습니다.(현재 체력: " + player.hp + ")");
            }
        }
    } // end of villageMenu

    //######################################### 던 전 관 련 메 서 드 ##################################################

    void choiceDungeon(Scanner sc) {
        System.out.println("이동하실 던전을 고르세요");
        for (int i = 1; i < dungeons.length; i++) {
            System.out.println(i +". " + dungeons[i-1].name + " (입장레벨: " + dungeons[i-1].reqLv + ")");
        }
        if (player.level >= 30) {    // 플레이어의 레벨이 30이상일때만 보이기
            System.out.println("7.(BOSS) " + dungeons[6].name + " (입장레벨: 30)");
        }
        int dungeonNum = sc.nextInt();
        int dungeonIndex = dungeonNum - 1;
        if (dungeonNum >= 1 &&  dungeonNum <= 7) {
            if (dungeons[dungeonIndex].reqLv <= player.level) {
                System.out.println(dungeons[dungeonIndex].name + "으로 이동합니다.");
                dungeonMenu(dungeons[dungeonIndex], sc, dungeonIndex);
            }
            else {
                System.out.println("던전 입장에 필요한 레벨이 부족합니다.");
                choiceDungeon(sc);
            }
        }
        else {
            inputError();
            choiceDungeon(sc);
        }
    }

    void dungeonMenu(Dungeon dungeon,  Scanner sc, int choice) {    // 특정 던전 값을 입력받음
        while (true) {
            System.out.println("================= " + dungeons[choice].name + " =================");
            System.out.println("1. 몬스터와 싸우기\n2. 인벤토리 열기\n3. 스탯창 열기\n4. 마을로 이동하기");
            System.out.println("선택: ");
            int choice2 = sc.nextInt();
            if (choice2 == 1) {
                battleMenu(dungeons[choice], sc);
                if (player.hp == 0) {
                    return;
                }
            }
            else if (choice2 == 2) {
                player.inventory.open(player, sc);
            }
            else if (choice2 == 3) {
                player.showStat();
            }
            else if (choice2 == 4) {
                return;
            }
            else {
                inputError();
            }
        }
    }

    void battleMenu(Dungeon dungeon,  Scanner sc) {
        Battle battle = new Battle();
        System.out.println("싸울 몬스터를 선택해주세요");
        for(int i = 0; i < 3 ; i++) {
            System.out.println(i+1 + ". " + dungeon.monster[i].name + "(레벨: " + dungeon.monster[i].level + ")");
        }
        int choice = sc.nextInt();
        if (!(choice >= 1 && choice <= 3)) {
            inputError();
            return;
        }

        Monster battleMonster = dungeon.monster[choice-1];
        if (battleMonster.level > player.level) {
            System.out.println("경고 : 몬스터의 레벨이 플레이어보다 높습니다");
            System.out.println("전투를 진행하시겠습니까? (1. 네 / 2. 아니요)");
            int choice2 = sc.nextInt();
            if (choice2 == 1) {
                battle.start(player, battleMonster, sc);
            }
            else if (choice2 == 2) {
                return;
            }
            else {
                inputError();
                return;
            }
        }
        else {
            battle.start(player, battleMonster, sc);
        }
    }


    //########################################### 반 복 된 문 구 메 서 드 #############################################
    void inputError() {
        System.out.println("입력오류: 올바른 숫자를 입력해주세요.");
    }
}
