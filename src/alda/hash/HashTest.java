package alda.hash;

public class HashTest {

    //jensmikaelerik

    public String letter = "j";

    public int hashCode() {

        return this.letter.charAt(0) % 29;
    }

    public int hash2() {
        return 5 - this.hashCode() % 5;
    }

    public static void main(String[] args) {

        HashTest ht = new HashTest();

        String name = "jensmikaelerik";

        for(int i = 0; i < name.length(); i++) {
            System.out.print(name.substring(i, i+1) + " --> ");
            ht.letter = name.substring(i, i+1);
            System.out.print(ht.hashCode() + " --> ");
            System.out.println(ht.hash2());
        }

        //System.out.println("j".hashCode() % 29);

        System.exit(0);

    }
}
