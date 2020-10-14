import java.util.List;

/**
 * @Author: SYP
 * @Date: 2020/6/11
 * @Description:
 */
public class Test1 {
    public static void main(String[] args) {

    }


    public static int getMaxNum(int num){
//        for(int i=num; i>= 0; i--){
            String numStr = num+"";
            String[] strArray = numStr.split("");
            for(int i=0; i<strArray.length-1; i++){
                if(Integer.valueOf(strArray[i]) < Integer.valueOf(strArray[i+1])){
                   if(i == strArray.length-2){
                       return num;
                   }else{
                       continue;
                   }
                }else{
                    break;
                }
            }

        int length = numStr.length();
        //个位是length到9
        int g = 9;
        //十位是length-1 到9
        //百位length-3到9
        //最高位是1到最高位的数减一
        int maxValue = 1;
        for(int j=0; j<length; j++){
            maxValue *= 10;
        }
        int max = num / maxValue;
        int s = 9-length;


//        }
    }

    public static void stole(List<Integer> list){
        int[] d = new int[list.size()];
        int[] p = new int[list.size()];
        int max = list.get(0);
        for(int i=0; i<list.size(); i++){
            for(int j=0; j<i; j++){
                if(d[j] + d[j+2] > max && d[j+1] < d[j]+d[j+2]){
                    max = d[j]+d[j+2];
                    d[j+2] = max;
                    p[j+2] = j;
                }
            }
        }
    }
}
