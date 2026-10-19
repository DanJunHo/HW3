import java.util.Scanner;

public class InGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GameSystem system = new GameSystem();
        system.start(sc);
    }
}
