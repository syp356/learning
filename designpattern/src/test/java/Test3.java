import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test3 {
    public static void main(String[] args){

//
//        String str = sc.nextLine();
//        String[] strArr = str.split("@");
//        if(strArr.length < 1){
//            System.out.println();
//        }else if(strArr.length == 1){
//            System.out.println(str.substring(0,str.length()-1));
//        }else{
//            String[] allStr = strArr[0].split(",");
//            if(null == allStr){
//                System.out.println();
//            }else{
//                String[] usedStr = strArr[1].split(",");
////                Map<String,Integer> leftMap = new HashMap<>();
//                Map<String,Integer> usedMap = new HashMap<>();
//                StringBuffer sb = new StringBuffer();
//                Arrays.asList(usedStr).stream().forEach(usedMapStr ->{
//                    String[] keyValue = usedMapStr.split(":");
//                    usedMap.put(keyValue[0],Integer.valueOf(keyValue[1]));
//                });
//
//                Arrays.asList(allStr).stream().forEach(strMap ->{
//                    String[] keyValue = strMap.split(":");
//                    String key = keyValue[0];
//                    Integer value = Integer.valueOf(keyValue[1]);
//
//                    if(null != usedMap.get(key)){
//                        value -= usedMap.get(key);
//                        if(value > 0){
//                            sb.append(key).append(":").append(value).append(",");
//                        }
//                    }else{
//                        sb.append(key).append(":").append(value).append(",");
//                    }
//                });
//
//                System.out.println(sb.toString().substring(0,sb.length()-1));
//            }
//        }

//        Scanner sc = new Scanner(System.in);
//        while(sc.hasNext()){
//            int n = sc.nextInt();
//            int count = getCount(n);
//            if(n == 0){
//                System.out.println("end");
//            }else{
//                System.out.println(count);
//            }
//        }
    }

    public static int getCount(int n){
        int count = 0;
        for(int i=1; i<=n/2; i++){
            int left = i;
            int right = n-i;
            if(isSingle(left) && isSingle(right)){
                count++;
            }
        }
        return count;
    }

    public static boolean isSingle(int n){
        if(n == 1){
            return false;
        }
        for(int i=2; i<n; i++){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }
}
