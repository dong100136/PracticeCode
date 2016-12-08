public class JavaStringTest{
    public static void main(String[] args){
        String str1 = "abcd";
        String str2 = "abcd";
        String str3 = "ab"+"cd";
        String str4 = "ab";str4+="cd";

        System.out.println(str1==str2);
        System.out.println(str1==str3);
        System.out.println(str1==str4);

        Integer num1 = 127;
        Integer num2 = 127;
        Integer num3 = 128;
        Integer num4 = 128;
        System.out.println(num1==num2);
        System.out.println(num3==num4);
    }
}
