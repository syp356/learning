/**
 * @Author: SYP
 * @Date: 2020/4/17
 * @Description:
 */
public class test {
    public static void main(String[] args) {
        System.out.println(canJump(new int[]{0,1}));
    }
        public static boolean canJump(int[] nums) {
//            if (nums.length == 1) return true;
//            // 直接寻找
//            int i, m;
//            for (i = 0; i < nums.length - 1; i++)
//            {
//                if (nums[i] == 0) // 只有0的时候会出现无法向后继续寻找
//                {
//                    for (m = 0; m < i; m++)
//                    {
//                        if (nums[m] + m > i) break;  // 说明能往后移动
//                    }
//                    if (m == i) return false;
//                }
//            }
//            return true;
            if(nums.length <= 1){
                return true;
            }
            for(int i=0; i<nums.length-1; i++){
                int currentIndex = 0;
                int jumpCount = i+1;
                for(int j=0; j<nums.length-1; j++){
                    currentIndex += jumpCount;
                    if(currentIndex == nums.length-1){
                        return true;
                    }else if (currentIndex >= nums.length || jumpCount == 0 || jumpCount >= nums.length){
                        break;
                    }else{
                        jumpCount = nums[currentIndex];
                    }
                }
            }
            return false;
        }
}
