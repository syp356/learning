import java.util.*;
public class Test2{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        List<String[]> strList = new ArrayList<>();
        int n = sc.nextInt();
        int forCount = 0;
        while(sc.hasNextLine()){
            String str = sc.nextLine();
            if(str.isEmpty()){
                continue;
            }
            String[] strArray = str.split(",");
            strList.add(strArray);
            int count = strArray.length%n == 0 ? strArray.length/n :strArray.length/n+1;
            if(forCount < count){
                forCount = count;
            }
            fun(n,strList,forCount);
        }
        sc.close();
    }

    public static void fun(int n, List<String[]> strList, int forCount){
        int count = 0;
        StringBuffer sb = new StringBuffer();
        while(forCount > 0 ){
            for(int i=0; i<strList.size(); i++){
                String[] arr = strList.get(i);
                if(arr.length <= n){
                    sb.append(Arrays.asList(arr));
                }
                for(int j=count*n; j<count*n+n; j++){
                    if(j>=arr.length){
                        break;
                    }
                    sb.append(arr[j]).append(",");
                }
            }
            count++;
            forCount--;
        }

        System.out.println(sb.toString());
    }

    //            String str = sc.nextLine();
//            StringBuffer sb = new StringBuffer(str);
//            List<String> strList = new ArrayList();
//            int length = str.length();
//            if(length%8 != 0){
//                for(int i=0; i<8-length%8; i++){
//                    sb.append("0");
//                }
//            }
//            str = sb.toString();
//            for(int i=0; i<str.length()/8; i++){
//                int nextIndex = (strList.size()+1)*8;
//                String subStr = str.substring(strList.size()*8,nextIndex > str.length() ? str.length() : nextIndex);
//                strList.add(subStr);
//            }
//
//            strList.stream().forEach(item -> System.out.println(item));


}