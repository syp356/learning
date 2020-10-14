import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<n; i++){
            int next = sc.nextInt();
            list.add(next);
        }

        int minStep = 0;
        for(int i=0; i<list.size(); i++){
            int stepCount = 0;
            int index = list.get(i);
            while(index<list.size()){
                stepCount++;
                int value = list.get(index);
                index += value;
                if(index >= list.size()-1){
                    if(index == list.size()-1){
                        if(minStep == 0 || minStep > stepCount){
                            minStep = stepCount;
                        }
                    }
                    break;
                }
            }
        }
        System.out.println(getStep(list));

        sc.close();
    }

    public static int getStep(List<Integer> list){
        if (list.size() <= 1){
            return 0;
        }
        int step = 0;
        int index = 0;
        int len = list.size();
        int p = 0;
        while (p <= len - 1) {
            if (p + list.get(p) >= len - 1){
                return  step + 1;
            }
            int _max = -1;
            for (int i = p + 1; i <= p + list.get(p); ++i) {
                if (_max < i + list.get(i)) {
                    _max = i + list.get(i);
                    index = i;
                }
            }
            step++;
            p = index;
        }

        return p;
    }
}