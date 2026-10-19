import java.util.Scanner;

public class InGame {
    public static void main(String[] args) {
        System.out.println("commit email을 변경하였음");
        Scanner sc = new Scanner(System.in);
        GameSystem system = new GameSystem();
        system.start(sc);
    }
}
