import java.util.*;

/**
 * @Author: SYP
 * @Date: 2020/6/7
 * @Description:
 */
public class Main {
        public static void main(String[] args) {
//            ApplicationContext app = new ClassPathApplicationContext();
            Scanner sc = new Scanner(System.in);
            while(sc.hasNext()){
                int num = sc.nextInt();
                List<Integer> list = new ArrayList<>();
                for(int i=0; i<num; i++){
                    list.add(sc.nextInt());
                }
                System.out.println(redraiment(list));
//                double input = sc.nextDouble();
//                if(input == 0 || input == 1){
//                    System.out.println(input);
//                    return;
//                }
//                double result = getCubeRoot(input);
//                System.out.println(String.format("%.1f",result));
                return;
            }
            sc.close();
        }

        public static int redraiment(List<Integer> list) {
            if (list.size() <= 1) {
                return list.size();
            }
            int index = 0;
            int min = list.get(index);
            List<Integer> step = new ArrayList<>();
            step.add(list.get(0));
            while (index < list.size()) {
                for (int i = index; i < list.size(); i++) {
                    index = i;
                    if (list.get(i) < min) {
                        if(step.isEmpty() || list.get(i) > step.get(step.size()-1)){
                            min = list.get(i);
                            step.add(list.get(i));
                        }
                    }
                }
            }

            return step.size();
        }


//        while(sc.hasNext()){
//            int input = sc.nextInt();
//            int inputb = sc.nextInt();
////            if(input % inputb == 0){
////                return inputb;
////            }
//            int inputc = Math.min(input,inputb);
//            for(int i= inputc; i >= 1; i--){
//                if(input % i ==0 && inputb % i ==0){
//                    System.out.println(input * inputb / i);
//                    break;
//                }
//            }
//        }

    public static double getCubeRoot(double input){
        double max = input;
        double min = 0;
        double mid = 0;
        while(max - min > 0.01){
            mid = (max + min) / 2;
            if(mid * mid * mid > input){
                max = mid;
                continue;
            }
            if(mid * mid * mid < input){
                min = mid;
                continue;
            }
            return mid;
        }
        return max;
    }

}
