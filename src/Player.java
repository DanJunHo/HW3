import java.sql.SQLOutput;
import java.util.Scanner;

public class Player {
    String name;
    int clssNum;
    String clss;
    int level = 4;
    int exp = 0;
    int maxExp;
    int hp;
    int maxHp;
    int attack;
    int armor;
    int gold = 100000;

    int equipNum = -1;   // 장착중인 무기의 인덱스를 표시하기 위함. 무기 해제 시 필요.

    int potionCount = 4;

    Inventory inventory;
    Weapon equipWeapon;     // 장착된 무기
    Skill[] skill;

    Player(String name, int clssNum) {
        this.name = name;
        this.clssNum = clssNum;
        this.inventory = new Inventory();
        this.maxExp = 100;

        if (clssNum == 1) {
            this.clss = "전사";
            this.hp = 140;
            this.maxHp = 140;
            this.attack = 18;
            this.armor = 10;
            this.skill = new Skill[3];
            // 객체 생성
            skill = new Skill[]{
                    new Skill("관통하는 검", 10),
                    new Skill("전사의 각성", 20),
                    new Skill("파멸의 일격", 30),
            };
        } else if (clssNum == 2) {
            this.clss = "궁수";
            this.hp = 105;
            this.maxHp = 105;
            this.attack = 25;
            this.armor = 4;
            skill = new Skill[]{
                    new Skill("철갑 화살", 10),
                    new Skill("연속 사격", 20),
                    new Skill("천공의 화살", 30),
            };
        } else {
            this.clss = "법사";
            this.hp = 95;
            this.maxHp = 95;
            this.attack = 15;
            this.armor = 3;
            skill = new Skill[]{
                    new Skill("반사의 보호막", 10),
                    new Skill("방어력 붕괴", 20),
                    new Skill("종말의 마법", 30),
            };
        }
    }

    // 스탯창 열기
    void showStat() {
        System.out.println("======================== S T A T U S ========================");
        System.out.println("닉네임 : " + this.name);
        System.out.println("직업 : " + this.clss);
        System.out.println("레벨 : " + this.level);
        System.out.println("경험치 : " + this.exp + "/" + this.maxExp);
        System.out.println("체력 : " + this.hp + "/" + this.maxHp);
        System.out.println("공격력 : " + this.attack);
        System.out.println("방어력 : " + this.armor);
        System.out.println("보유 골드 : " + this.gold);
        if (equipWeapon == null) {
            System.out.println("장착중인 무기 : (비어있음)");
        } else {
            System.out.println("장착중인 무기 : " + equipWeapon.name);
        }
        System.out.println("==============================================================");
    }

    // 전투 관련 메서드

    void attack(Monster monster) {  // 기본공격 메서드
        int damage = attack - monster.armor;
        if (damage <= 0) {
            damage = 0;
        }
        monster.hp = monster.hp - damage;

        if (monster.hp <= 0) {
            monster.hp = 0;
        }

        System.out.println(monster.name + " 에게 " + damage + "만큼의 피해를 입혔습니다.(남은체력 : " + monster.hp + ")");
        // 궁수 skillB 발동
        if (this.clssNum == 2 && this.skill[1].availability == true) {
            System.out.println(this.skill[1].name + " 발동! (추가 피해량: " + damage / 2 + " / 남은체력 : " + monster.hp + ")");
            monster.hp = monster.hp - damage / 2;
            if (monster.hp <= 0) {
                monster.hp = 0;
            }
        }

        // 전사 skillB 발동
        if (this.clssNum == 1 && this.skill[1].availability == true) {
            this.hp = this.hp + (damage / 5);
            if (hp >= maxHp) {
                hp = maxHp;
            }
            System.out.println(this.skill[1].name + " 발동! (체력회복: " + damage / 5 + ")");
        }
    }

    void takeDamage(Monster monster) {  // 피해 메서드
        int damage = monster.attack - this.armor;
        if (this.clssNum == 3 && this.skill[1].availability == true) {
            damage = (2 * (monster.attack - this.armor)) / 3;
            System.out.println(this.skill[1] + " 에 의해 입은 피해량이 감소합니다.");
        }
        if (damage <= 0) {
            damage = 0;
        }
        this.hp = this.hp - damage;
        if (this.hp <= 0) {
            this.hp = 0;
        }
        System.out.println(monster.name + "에게 " + damage + "만큼의 피해를 입었습니다.(남은체력: " + this.hp + ")");

        // 법사 스킬B발동!
        if (this.clssNum == 3 && this.skill[1].availability == true && this.hp != 0) {
            System.out.println(this.skill[1].name + "발동! " + (13 * damage / 10) + "의 피해를 되돌려줍니다.");
        }
    }

    void win(Monster monster) {     // 승리시 실행 될 메서드
        System.out.println(monster.name + " 을(를) 처치하였습니다.");
        this.exp = this.exp + monster.expReward;
        if (this.exp >= this.maxExp) {
            levelUp();
        }
        System.out.println("경험치 획득 : " + monster.expReward + "(현재 경험치: " + this.exp + ")");
        this.gold = this.gold + monster.goldReward;
        System.out.println("골드 획득 : " + monster.goldReward);
    }

    void levelUp() {    // 레벨업 메서드
        // 레벨 업
        this.level++;
        this.exp = this.exp - this.maxExp;
        System.out.println("★☆★☆★☆★☆★ 레벨이 상승하였습니다! (현재 " + this.name + " 님의 레벨 : " + this.level + ") ★☆★☆★☆★☆★");
        updateStats();

        // 스킬획득
        if (level == 10) {
            this.skill[0].availability = true;
            System.out.println(skill[0].name + " 스킬을 획득하였습니다!");
        } else if (level == 20) {
            this.skill[1].availability = true;
            System.out.println(skill[1].name + " 스킬을 획득하였습니다!");
            if (clssNum == 1) {
                System.out.println(skill[1].name + " 에 의해 영구적으로 공격력이 증가합니다! (공격력 증가: +20)");
            }
        } else if (level == 30) {
            this.skill[2].availability = true;
            System.out.println(skill[2].name + " 스킬을 획득하였습니다!");
        }
    }

    void updateStats() {
        this.maxExp = level * 100;
        if (clssNum == 1) {
            this.maxHp = this.maxHp + 18;
            this.hp = this.maxHp;
            this.attack = this.attack + 3;
            this.armor = this.armor + 1;
        } else if (clssNum == 2) {
            this.maxHp = this.maxHp + 11;
            this.hp = this.maxHp;
            this.attack = this.attack + 4;
            this.armor = (level - 1) / 2;
        } else if (clssNum == 3) {
            this.maxHp = this.maxHp + 13;
            this.hp = this.maxHp;
            this.attack = this.attack + 2;
            this.armor = (level - 1) / 3;
        }
    }

    void deathPenalty() {   // 패널티 메서드
        int lossGold = this.gold / 10;
        int lossExp = this.exp / 10;
        this.gold = this.gold - lossGold;
        if (this.gold <= 0) {
            this.gold = 0;
        }
        this.exp = this.exp - lossExp;
        if (this.exp <= 0) {
            this.exp = 0;
        }
        System.out.println("경험치 손실 : -" + lossExp);
        System.out.println("골드 손실 : -" + lossGold);
        System.out.println("마을로 이동합니다.");
    }

    // 인벤토리와 관련된 메서드

    void equipWeapon(Scanner sc) {
        if (this.equipWeapon != null) {
            System.out.println("이미 장착중인 무기가 있습니다.");
            return;
        }
        // System.out.println("[DEBUG] equipWeapon 메서드 진입");
        while (true) {
            System.out.println("장착할 무기의 번호를 입력하세요");
            int invenNum = sc.nextInt();
            int invenIndex = invenNum - 1;

            if (invenIndex >= 0 && invenIndex < 30) {
                if (this.inventory.slots[invenIndex].isEmpty()) {
                    System.out.println("해당 슬롯은 비어있습니다.");
                    return;
                } else if (this.inventory.slots[invenIndex].isPotion()) {
                    System.out.println("해당 아이템은 무기가 아닙니다.");
                    return;
                } else if (this.inventory.slots[invenIndex].isWeapon()) {
                    // 무기 레벨이 플레이어 레벨보다 높은 경우
                    if (this.inventory.slots[invenIndex].weapon.level > this.level) {
                        System.out.println("착용레벨이 부족합니다.");
                        return;
                    }
                    // 플레이어 레벨이 무기 레벨과 같거나 높은 경우
                    else {
                        this.equipWeapon = this.inventory.slots[invenIndex].weapon; // 해당 슬롯의 무기를 장착중인 무기 객체가 됨.
                        this.inventory.slots[invenIndex].equipped = true;   // 해당 슬롯의 상태가 장착중으로 변경
                        this.equipNum = invenIndex;
                        this.attack = this.attack + this.equipWeapon.attack;
                        System.out.println(this.equipWeapon.name + "을 장착하였습니다.(공격력 증가 +" + this.equipWeapon.attack + ")");
                        return;
                    }
                }
            }
            else {
                System.out.println("입력오류: 올바른 값을 입력하세요");
            }
        }
    }

    void unequipWeapon() {
        if (equipWeapon == null) {
            System.out.println("현재 장착중인 무기가 없습니다.");
        } else {
            this.attack = this.attack - equipWeapon.attack;
            this.inventory.slots[this.equipNum].equipped = false;
            this.equipNum = - 1;
            System.out.println(equipWeapon.name + " 을(를) 장착 해제하였습니다.");
            equipWeapon = null;
        }
    }

    boolean usePotion(boolean inBattle, Scanner sc) {  // 반환값이 true이면 플레이어 턴 반복, false면 몬스터 턴으로 넘어감.
        if (inBattle == true) {
            while (true) {
                // 전투 중 포션사용 선택 시 인벤토리를 띄운다.
                this.inventory.open(this);
                System.out.println("사용할 포션의 번호를 입력하세요 (0. 뒤로가기)");

                int invenNum = sc.nextInt();
                int invenIndex = invenNum - 1;
                // 0입력 시 호출한 메서드로 되돌아가기.
                if (invenNum == 0) {
                    return true;
                }
                // 범위 내로 올바르게 입력한 경우
                if (invenIndex >= 0 && invenIndex < 30) {
                    // 포션 사용 조건을 만족하지 않는 경우
                    if (this.inventory.slots[invenIndex].isEmpty()) {
                        System.out.println("해당 슬롯은 비어있습니다.");
                    } else if (this.inventory.slots[invenIndex].isWeapon()) {
                        System.out.println("해당 슬롯은 포션이 아닙니다.");
                        // 포션 사용 조건을 만족하는 경우
                    } else if (this.inventory.slots[invenIndex].isPotion()) {
                        if (this.inventory.slots[invenIndex].potion.level > this.level) {
                            System.out.println("해당 포션을 사용하기 위한 레벨이 부족합니다.");
                        } else {
                            if (this.potionCount == 0) {
                                System.out.println("포션 사용 개수를 초과하였습니다.");
                            } else {
                                // 체력회복
                                this.hp = this.hp + this.inventory.slots[invenIndex].potion.hpUp;
                                if (this.hp >= this.maxHp) {
                                    this.hp = this.maxHp;
                                }
                                // 개수감소 & 전투중 사용개수 감소
                                this.inventory.slots[invenIndex].count = this.inventory.slots[invenIndex].count - 1;
                                this.potionCount = this.potionCount - 1;
                                // 포션 사용 메시지. 슬롯을 먼저 clear하면 이후에 potion.name에서 null이 발생하기 때문에 먼저 출력!
                                System.out.println(this.inventory.slots[invenIndex].potion.name + " 을(를) 사용하였습니다! " +
                                        "(현재체력: " + this.hp +
                                        " / 남은 포션 수: " + this.inventory.slots[invenIndex].count +
                                        " /사용 가능 포션 수: " + this.potionCount + ")");
                                // 포션개수가 0개일 시 인벤토리 비우기
                                if (this.inventory.slots[invenIndex].count == 0) {
                                    this.inventory.slots[invenIndex].clear();
                                }
                                return false;
                            }
                        }
                    }
                }
                // 입력한 범위가 올바르지 않은경우
                else {
                    System.out.println("입력오류: 올바른 범위의 숫자를 입력하세요.");
                }
            }
        }
        // 싸움중이 아닌경우. 뒤로가기 누르면 false반환
        else {
            while (true) {
                // 전투중이 아닌 경우 인벤토리에서 포션 사용을 선택할 수 있다.
                this.inventory.open(this);
                System.out.println("사용할 포션의 번호를 입력하세요 (0. 뒤로가기)");

                int invenNum = sc.nextInt();
                int invenIndex = invenNum - 1;
                // 0입력 시 호출한 메서드로 되돌아가기.
                if (invenNum == 0) {
                    return false;
                }
                // 범위 내로 올바르게 입력한 경우
                if (invenIndex >= 0 && invenIndex < 30) {
                    // 포션 사용 조건을 만족하지 않는 경우
                    if (this.inventory.slots[invenIndex].isEmpty()) {
                        System.out.println("해당 슬롯은 비어있습니다.");
                    } else if (this.inventory.slots[invenIndex].isWeapon()) {
                        System.out.println("해당 슬롯은 포션이 아닙니다.");
                        // 포션 사용 조건을 만족하는 경우
                    } else if (this.inventory.slots[invenIndex].isPotion()) {
                        if (this.inventory.slots[invenIndex].potion.level > this.level) {
                            System.out.println("해당 포션을 사용하기 위한 레벨이 부족합니다.");
                        } else {
                            if (this.potionCount == 0) {
                                System.out.println("포션 사용 개수를 초과하였습니다.");
                            } else {
                                // 체력회복
                                this.hp = this.hp + this.inventory.slots[invenIndex].potion.hpUp;
                                if (this.hp >= this.maxHp) {
                                    this.hp = this.maxHp;
                                }
                                // 개수만 감소
                                this.inventory.slots[invenIndex].count = this.inventory.slots[invenIndex].count - 1;
                                // 포션 사용 메시지. 슬롯을 먼저 clear하면 이후에 potion.name에서 null이 발생하기 때문에 먼저 출력!
                                System.out.println(this.inventory.slots[invenIndex].potion.name + " 을(를) 사용하였습니다! " +
                                        "(현재체력: " + this.hp +
                                        " / 남은 포션 수: " + this.inventory.slots[invenIndex].count + ")");
                                // 포션개수가 0개일 시 인벤토리 비우기
                                if (this.inventory.slots[invenIndex].count == 0) {
                                    this.inventory.slots[invenIndex].clear();
                                }
                                return true;
                            }
                        }
                    }
                }
                // 입력한 범위가 올바르지 않은경우
                else {
                    System.out.println("입력오류: 올바른 범위의 숫자를 입력하세요.");
                }
            }
        }
    }
}