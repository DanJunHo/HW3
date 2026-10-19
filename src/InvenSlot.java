public class InvenSlot {
    Weapon weapon;
    Potion potion;
    int count;
    boolean equipped = false;

    boolean isEmpty() {   // 슬롯이 비우기
        return weapon == null && potion == null && count == 0;
    }

    boolean isWeapon() {
        return weapon != null;  // weapon이 null이 아니면 true출력
    }

    boolean isPotion() { return potion != null; }

    void setWeapon(Weapon weapon) {  // 슬롯에 무기넣기
        this.weapon = weapon;
        this.potion = null;
        this.count = 1;
    }

    void setPotion(Potion potion, int count) {   // 슬롯에 포션넣기
        this.potion = potion;
        this.weapon = null;
        this.count = this.count + count;
    }

    void clear() {  // 슬롯 비우기
        weapon = null;
        potion = null;
        count = 0;
    }
}
