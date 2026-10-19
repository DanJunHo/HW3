import java.util.Scanner;

public class WeaponStore {
    Weapon[] weapons = {
            new Weapon("강철 검", "전사", 5, 7, 150),
            new Weapon("기사의 검", "전사", 10, 13, 400),
            new Weapon("미스릴 검", "전사", 15, 20, 800),
            new Weapon("용사의 검", "전사", 20, 28, 1400),
            new Weapon("전설의 검", "전사", 25, 37, 2200),
            new Weapon("성검", "전사", 30, 48, 3200),
            new Weapon("사냥꾼의 활", "궁수", 5, 9, 150),
            new Weapon("정밀한 장궁", "궁수", 10, 16, 400),
            new Weapon("미스릴 활", "궁수", 15, 24, 800),
            new Weapon("폭풍의 활", "궁수", 20, 33, 1400),
            new Weapon("영웅의 활", "궁수", 25, 43, 2200),
            new Weapon("신궁의 활", "궁수", 30, 55, 3200),
            new Weapon("마력 지팡이", "법사", 5, 5, 150),
            new Weapon("현자의 지팡이", "법사", 10, 10, 400),
            new Weapon("수정 지팡이", "법사", 15, 16, 800),
            new Weapon("대마법사의 지팡이", "법사", 20, 24, 1400),
            new Weapon("고대의 지팡이", "법사", 25, 34, 2200),
            new Weapon("신의 지팡이", "법사", 30, 48, 3200),
    };

    void openStore(Player player, Scanner sc) {
        System.out.println("=============== 무 기 상 점 ===============");
        for (int i = 0; i < weapons.length; i++) {
            System.out.println(i + 1 + ". " + weapons[i].name + "(" + "공격력: " + weapons[i].attack + " / 사용직업군: " + weapons[i].clss + " / 요구레벨: " + weapons[i].level + " / 구매가: " + weapons[i].gold + ")");
        }
        System.out.println("=========================================");
        System.out.println("1. 구매하기");
        System.out.println("2. 판매하기");
        System.out.println("3. 무기상점 나가기");

        int num = sc.nextInt();
        if (num == 1) {
            buyWeapon(player, sc);
        } else if (num == 2) {
            Inventory inventory = new Inventory();
            sellWeapon(player, sc);
        } else {
            return;
        }
    }

    void buyWeapon(Player player, Scanner sc) { // 플레이어가 무기 번호를 선택하면 그 번호 값을 받아서 무기를 인벤토리에 넣는다.
        System.out.println("구매할 무기의 번호를 입력하세요");

        int itemNum = sc.nextInt();     // 상점의 무기번호
        int itemIndex = itemNum - 1;    // 실제 무기 인덱스

        // 골드부족
        if (player.gold < weapons[itemIndex].gold) {
            System.out.println("보유골드가 부족합니다.");
            return;
        }
        // 인벤토리에 무기 추가
        for (int i = 0; i < player.inventory.slots.length; i++) {
            if (player.inventory.slots[i].isEmpty()) {
                player.inventory.slots[i].setWeapon(weapons[itemIndex]);
                player.gold -= weapons[itemIndex].gold;
                System.out.println(weapons[itemIndex].name + "을(를) 구입하였습니다.");
                return;
            }
        }
        // 인벤토리가 꽉 찬 경우
        System.out.println("인벤토리가 모두 찼습니다.");
    }

    void sellWeapon(Player player, Scanner sc) {
        player.inventory.open(player, sc);
        System.out.println("판매할 무기의 번호를 입력하세요");
        int itemNum = sc.nextInt();
        int itemIndex = itemNum - 1;
        if (itemNum < 1 && itemNum > 30) {
            System.out.println("입력오류: 올바른 숫자를 입력해주세요");
        }
        // 비어있는 경우
        if (player.inventory.slots[itemIndex].isEmpty()) {
            System.out.println("해당 슬롯은 비어있습니다.");
            return;
        }
        else if (player.inventory.slots[itemIndex].isPotion()) {
            System.out.println("무기만 판매할 수 있습니다.");
            return;
        }
        else if (player.inventory.slots[itemIndex].isWeapon()) {
            player.gold = player.gold - player.inventory.slots[itemIndex].weapon.gold;
            System.out.println(player.inventory.slots[itemIndex].weapon.name + "을 판매하였습니다.");
            player.inventory.slots[itemIndex].clear();
            return;
        }


    }
}
