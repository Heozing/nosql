package 课堂作业;

public class ReverseString {

    //将字符串中rgb三个字母给分解出来，r放在最前面，b放在最后面
    public static void reRank(String[] str) {
        if (str == null) ;

        int i=0, left =0,right = str.length-1;
        while(i<=right)
        {
            if(str[i].equals("r")){
                String t1=str[i];
                str[i]=str[left];
                str[left]=t1;
                i++;
                left++;
            }else if(str[i].equals("g")){
                i++;
            }else if(str[i].equals(("b"))){
                String t1=str[i];
                str[i]=str[right];
                str[right]=t1;
                right--;
            }
        }

    }

    public static void main(String[] args) {
        String[] str = new String[]{"r","g","b","r","g","b","r","g","b","r","g","b"};
        reRank(str);
        for(int i=0;i<str.length;i++)
        {
            System.out.println(str[i]);
        }
        // write your code here
    }
}
