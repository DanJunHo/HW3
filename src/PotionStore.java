import java.util.Scanner;

public class PotionStore {
    Potion[] potion = {
            new Potion("소형 회복 포션", 1,50, 30),
            new Potion("중형 회복 포션", 10,130, 90),
            new Potion("대형 회복 포션", 20,300, 220)
    };

    void openStore(Player player, Scanner sc) {
        System.out.println("=============== 소 모 품 상 점 ===============");
        for(int i = 0; i < potion.length; i++) {
            System.out.println(i+1 + ". " + potion[i].name + "(hp 회복량: " + potion[i].hpUp + " / 사용가능 레벨: " + potion[i].level + " / 구매가: " + potion[i].gold + ")");
        }
        System.out.println("===========================================");
        System.out.println("1. 구매");
        System.out.println("2. 판매");
        System.out.println("3. 소모품 상점 나가기");
        int choice = sc.nextInt();
        if(choice == 1) {
            buyPotion(player, sc);
        }
        else if(choice == 2) {
            Inventory inventory = new Inventory();
            sellPotion(player, sc);
        }
        else if(choice == 3) {
            return;
        }
    }

    void buyPotion(Player player, Scanner sc) { // 구매할 포션의 번호를 입력받아 구매한다.
        System.out.println("구매할 포션을 선택하세요.");
        int itemNum =  sc.nextInt();
        if (itemNum < 1 || itemNum > 3) {
            System.out.println("입력오류: 올바른 숫자를 입력하세요");
            buyPotion(player, sc);
        }
        int itemIndex = itemNum - 1;
        System.out.println("구매할 포션의 개수를 선택하세요");
        int count = sc.nextInt();
        if (count <= 0) {
            System.out.println("1 이상의 숫자를 입력하세요.");
            return;
        }
        if (potion[itemIndex].gold * count > player.gold) { // 1. 구입할 포션의 번호와 개수를 묻는다. 플레이어의 보유골드가 부족하면 오류문구를 띄운다.
            System.out.println("보유골드가 부족합니다.");
            return;
        }
        // 비어있는 인벤토리가 있는 경우
        for(int i = 0; i < player.inventory.slots.length; i++) {
            if (player.inventory.slots[i].isEmpty()) {
                if (player.inventory.slots[i].isPotion() && player.inventory.slots[i].potion.name == potion[itemIndex].name) {    // 해당 슬롯이 같은 아이템이라면 개수만 증가
                    player.inventory.slots[i].count = player.inventory.slots[i].count + count;
                    player.gold = player.gold - potion[itemIndex].gold * count;
                    System.out.println("포션 구입을 완료하였습니다.");
                    return;
                }
                else if (player.inventory.slots[i].isEmpty()) { // 해당 슬롯이 비어있다면 포션을 추가
                    player.inventory.slots[i].setPotion(potion[itemIndex], count);
                    player.gold = player.gold - potion[itemIndex].gold * count;
                    System.out.println("포션 구입을 완료하였습니다.");
                    return;
                }
            }
        }
        // 비어있는 인벤토리가 없는 경우
        System.out.println("인벤토리가 모두 찼습니다.");
    }

    void sellPotion(Player player, Scanner sc) {
        player.inventory.open(player);
        System.out.println("판매할 포션을 입력하세요");
        int itemNum =  sc.nextInt();
        int itemIndex = itemNum - 1;

        if (player.inventory.slots[itemIndex].isEmpty()) {
            System.out.println("해당 슬롯은 비어있습니다.");
        }
        else if(player.inventory.slots[itemIndex].isWeapon()) {
            System.out.println("포션만 판매할 수 있습니다.");
        }
        else if(player.inventory.slots[itemIndex].isPotion()) {
            System.out.println("판매할 포션의 개수를 입력해주세요");
            int count = sc.nextInt();
            // 보유 개수가 입력한 수보다 적은 경우
            if(count > player.inventory.slots[itemIndex].count) {
                System.out.println("아이템 보유량이 부족합니다.");
            }
            // 보유 개수가 입력 수 이상인 경우
            else {
                if (player.inventory.slots[itemIndex].count == count) {
                    player.gold = player.gold + player.inventory.slots[itemIndex].potion.gold * count;
                    System.out.println(player.inventory.slots[itemIndex].potion.name + "을(를) 판매하였습니다.");    // clear()먼저 사용 후에 쓰면 null오류 뜸
                    player.inventory.slots[itemIndex].clear();
                }
                else {
                    player.inventory.slots[itemIndex].count = player.inventory.slots[itemIndex].count - count;
                    player.gold = player.gold + player.inventory.slots[itemIndex].potion.gold * count;
                    System.out.println(player.inventory.slots[itemIndex].potion.name + "을(를) 판매하였습니다. (현재수량: " + player.inventory.slots[itemIndex].count + ")");
                }
            }
        }
    }
}
