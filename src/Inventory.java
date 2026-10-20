import java.util.Scanner;

public class Inventory {
    InvenSlot[] slots = new InvenSlot[10];

    Inventory() {   // 30개의 인벤토리 슬롯 객체 생성
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new InvenSlot();
        }
    }

    void open(Player player) {  // 인벤토리 열기
        System.out.println("================= 인 벤 토 리 =================");
        for (int i = 0; i < slots.length; i++) {
            System.out.print((i + 1) + ". ");

            if (slots[i].isEmpty()) {
                System.out.println("(빈 슬롯)");
            }
            else if (slots[i].equipped == true && slots[i].isWeapon()) {
                System.out.println(slots[i].weapon.name + " (장착중)");
            }
            else if (slots[i].equipped == false && slots[i].isWeapon()) {
                System.out.println(slots[i].weapon.name + " (착용레벨: " + slots[i].weapon.level + " /공격력:  " + slots[i].weapon.attack + ")");
            } else if (slots[i].isPotion()) {
                System.out.println(slots[i].potion.name + " (회복량: " + slots[i].potion.hpUp + " /개수: " + slots[i].count + ")");
            }
        }
        System.out.println("보유골드: " + player.gold);
        System.out.println("=============================================");
    }

    void invenMenu(Player player, Scanner sc) {
        System.out.println("1. 아이템 장착\n2. 아이템 장착 해제\n3. 포션 사용\n4. 닫기");
        int choice = sc.nextInt();

        if (choice == 1) {
            player.equipWeapon(sc);
        }
        else if (choice == 2) {
            player.unequipWeapon();
        }
        else if (choice == 3) {
            // usePotion에서 뒤로가기 입력 시 while문 벗어나도록 작성.
            boolean back = true;
            while(back) {
                back = player.usePotion(false, sc);
            }
        }
        else if (choice == 4) {
            return;
        }
        else {
            System.out.println("입력오류: 올바른 숫자를 입력하세요");
        }

    }
}
