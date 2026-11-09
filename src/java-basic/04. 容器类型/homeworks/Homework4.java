public class Homework4 {
    public static void main(String[] args) {
        String s = "hello";
        System.out.println(s.substring(1, 4));
        System.out.println(s.contains("el") + " " + !s.contains("x"));

        System.out.println(s.concat(" world") + " " + s.repeat(2));

        String vowels = "aeiou";
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (vowels.indexOf(s.charAt(i)) != -1) count++;
        }
        System.out.println(count);
        System.out.println("length: " + s.length());
    }
}