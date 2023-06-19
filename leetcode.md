算法题

 1.统计n以内的素数的个数-暴力拆解

package com.wdy;

import java.util.Scanner;

/**

 * @author:wdy

 * .统计n以内的素数的个数-暴力拆解
   */
   
   
   
   public class Main {
   public static void main(String[] args) {
       Scanner sc=new Scanner(System.in);

       int n=sc.nextInt();
       System.out.println(bf(n));


    }
    public static int bf(int n){
        int count=0;
        for (int i=2;i<n;i++){
            count+=isPrime(i) ? 1 :0;
    
        }
        return count;
    }
    public static boolean isPrime(int x){
        for (int i=2;i*i<=x;i++){
            if(x%i==0){
                return false;
            }
        }
        return true;
    }


}

  2.埃筛法

  [埃拉托斯特尼](https://baike.baidu.com/item/埃拉托斯特尼?fromModule=lemma_inlink)筛法，简称[埃氏筛](https://baike.baidu.com/item/埃氏筛/5677377?fromModule=lemma_inlink)或爱氏筛，是一种由希腊数学家埃拉托斯特尼所提出的一种简单检定素数的算法。要得到自然数n以内的全部素数，必须把不大于根号n的所有素数的倍数剔除，剩下的就是素数。
  package com.wdy;

import java.util.Scanner;

/**

 * @author:wdy

 * 埃筛法
   */
   
   
   
   
   public class Main1 {
   public static void main(String[] args) {
       Scanner sc=new Scanner(System.in);

       int n=sc.nextInt();
       System.out.println(eratosthenes(n));

   }
   //减低遍历
   public static int eratosthenes(int n){
       boolean [] isPrime=new boolean[n];//false代表素数
       int count=0;
       for (int i=2;i<n;i++){
           if (!isPrime[i]){
               count++;
               //j就是和数的标记
               for (int j=i*i;j<n;j+=i){
                   isPrime[j]=true;
               }
           }
       }

       return count;

   }
   }
   
   

 2.一个有序数组nums,原地删除重复的元素，使每个元素只出现一次，返回删除后数组的新长度(不能是用额外的数组空间，必须在原地修改数组并在使用O（1）额外空间下完成)

 package com.wdy;



/**

 * 一个有序数组nums,原地删除重复的元素，使每个元素只出现一次，返回删除后数组的新长度
 * 不能是用额外的数组空间，必须在原地修改数组并在使用O（1）额外空间下完成
   */
   
   
   
   
   
   public class Main2 {
   public static void main(String[] args) {
       System.out.println(removeDup(new int[]{0,1,2,2,3,3,4}));



    }
    private static int removeDup(int [] nums){
        if (nums.length==0){
            return 0;
        }
        int i=0;
        //i是慢指针，j是快指针
        for (int j=1;j<nums.length;j++){
            if (nums[j]!=nums[i]){
                i++;
                nums[i]=nums[j];
            }
        }
        return i+1;
    }

}




3.给定一个整数组nums,请编写一个能返回数组中心下标的方法
中心下标：气左侧所有元素和等于右侧所有元素和，如果不存在中心下标，返回-1.





**Stream**是Java 8 API添加的一个新的抽象，称为流Stream，以一种声明性方式处理数据集合（侧重对于源数据计算能力的封装，并且支持序列与并行两种操作方式）



Array.Stream()返回的是一个元素序列，且支持顺序和并行的聚合操作。其实我们可以把它理解为包装类，其实和对于Int来说，元素序列类型为OptionalInt，包装类为Integer，它们在各自的领域各司其职，只不过Int类型和Integer类型可以自动转化






package com.wdy;


import java.util.Arrays;

/**
 *
 */
public class Main3 {
    public static void main(String[] args) {
        System.out.println(pivoIndex(new int[]{1,7,3,6,5,6}));

    }
    public static int pivoIndex(int [] nums){
        int sum= Arrays.stream(nums).sum();
        int total=0;
        for (int i=0;i< nums.length;i++){
            total+=nums[i];
            if (total==sum){
                return i;
            }
            sum=sum-nums[i];
        }
        return -1;
    
    }

}



4.在不使用sqrt的情况下，求x平方根的整数部分。

  /**

 * 二分法
   */
   
   
   
   
   public class Main4 {
   public static void main(String[] args) {
       System.out.println(sqr(16));

   }

   public static int sqr(int x){
       int index=-1;
       int left=0;
       int right=x;
       while (left<=right){
           int mid=(left+right)/2;
           if (mid*mid<=x){
               index=mid;
               left=mid+1;
           }else {
               right=mid-1;
           }
       }
       return index;

   }

}



5.链表反转

  

```
package com.wdy;

/**
 * 迭代法
 */
public class Main5 {
    static class ListNode{
        int val;//保持值
        ListNode next;
        public ListNode(int val,ListNode next){
            this.val=val;
            this.next=next;
        }


    }
    

    public static ListNode iterate(ListNode head){
        ListNode pre=null,next;//保持前后指针使用
        ListNode curr=head;//做遍历用，当前变量
        //head为作为第一个元素
        while (curr!=null){
            next=curr.next;
            curr.next=pre;
            pre=curr;
            curr=next;

        }



        return pre;
    }
    public static void main(String[] args) {
        ListNode node5=new ListNode(5,null);
        ListNode node4=new ListNode(4,node5);
        ListNode node3=new ListNode(3,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node1=new ListNode(1,node2);

    }
}


6.整型数组nums,在数组中找出由3个数字组成的最大乘积，并输出乘积

package com.wdy;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * 整型数组nums,在数组中找出由3个数字组成的最大乘积，并输出乘积
 */
 
 
 
public class Main6 {
    public static void main(String[] args) {
        System.out.println("输入几个数，用号隔开");
        Scanner scanner=new Scanner(System.in);
        String str=scanner.next().toString();
        String [] arr= str.split(",");
        int [] br=new int[arr.length];
        for (int i=0;i<arr.length;i++){
            br[i]=Integer.parseInt(arr[i]);
        }
        System.out.println(sort(br));
    }
    public static int sort(int [] nums){
        Arrays.sort(nums);
        int n=nums.length;

        return Math.max(nums[0]*nums[1]*nums[n-1],nums[n-1]*nums[n-2]*nums[n-3]);

    }
}




package com.wdy;

import java.util.Scanner;

/**
 * 二
 * Integer.MAX_VALUE表示int数据类型的最大取值数：2 147 483 647
 * Integer.MIN_VALUE表示int数据类型的最小取值数：-2 147 483 648
 */
public class Main7 {

    public static void main(String[] args) {
        System.out.println("输入几个数,用号隔开");
        Scanner scanner=new Scanner(System.in);
        String str=scanner.next().toString();
        String [] arr= str.split(",");
        int [] br=new int[arr.length];
        for (int i=0;i<arr.length;i++){
            br[i]=Integer.parseInt(arr[i]);
        }
        System.out.println(getMaxMin(br));


    }
    public static int getMaxMin(int [] nums){
        int min1=Integer.MAX_VALUE;
        int min2=Integer.MAX_VALUE;
        int max1=Integer.MIN_VALUE;
        int max2=Integer.MIN_VALUE;
        int max3=Integer.MIN_VALUE;

        for ( int x:nums){
            if (x<min1){
                min2=min1;
                min1=x;
            }else
            if (x<min2){
                min2=x;
            }
            if(x>max1){
                max3=max2;
                max2=max1;
                max1=x;

            }else if (x>max2){
                max3=max2;
                max2=x;

            }else if (x>max3){
                max3=x;
            }


        }

        return Math.max(min1*min2*max1,max1*max2*max3);

    }

}
7.冒泡排序

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class Main1 {
    public static void main(String[] args) {
        int [] array={3,9,-1,10,-2};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));

    }
    public static void bubbleSort(int [] array){
        int temp=0;
        boolean flag=false; //表示变量
        for (int i=0;i<array.length-1;i++){
            for(int j=0;j<array.length-1-i;j++){
                if (array[j]>array[j+1]){
                    flag=true;
                    temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
            //这一趟没有发生交换
            if (!flag){
                break;
            }else {
                flag=false;
            }

        }

    }

}
8.选择排序

/**
 * 选择排序
 */
public class Main2 {
    public static void main(String[] args) {
        int [] array=new int[80000];
        for(int i=0;i<80000;i++){
            array[i]=(int)(Math.random()*8000000);
        }
        Date date1=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=simpleDateFormat.format(date1);
        System.out.println("排序前"+dateStr);
        selectSort(array);
        Date date2=new Date();
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1=simpleDateFormat1.format(date2);
        System.out.println("排序后"+dateStr1);



    }
    public static void selectSort(int [] array){

        for (int i=0;i<array.length-1;i++){
            int minIndex=i;
            int min=array[i];
            for (int j=1+i;j<array.length;j++){
                if(min>array[j]){
                    minIndex=j;
                    min=array[j];
                }
            }
            if(minIndex!=i){
                array[minIndex]=array[i];
                array[i]=min;
            }
        }
    }
}
9.插入排序
/**
 * 插入排序
 */
public class Main3 {
    public static void main(String[] args) {
        int [] array={74,4,4,27,8,4};
        insertSort(array);
        System.out.println(Arrays.toString(array));

    }
    public static void insertSort(int [] array){
        for (int i=1;i<array.length;i++){
            //待插入的数
            int insertValue=array[i];
            int insertIndex=i-1;
            while (insertIndex>=0 && insertValue<array[insertIndex]){
                array[insertIndex+1]=array[insertIndex];
                insertIndex--;
            }
            array[insertIndex+1]=insertValue;

        }

    }

}

9.希尔排序
/**
 * 希尔排序
 */
public class Main4 {
    public static void main(String[] args) {
        int [] array={5,63,2,5,6,73,2,2,4,5};
        shellSort(array);
        System.out.println(Arrays.toString(array));


    }
    public static void shellSort(int [] array){
        int temp=0;
        for (int gap=array.length;gap>0;gap/=2){
            for (int i=gap;i<array.length;i++){
                for (int j=i-gap;j>=0;j-=gap){
                    if(array[j]>array[j+gap]){
                        temp=array[j];
                        array[j]=array[j+gap];
                        array[j+gap]=temp;

                    }
                }
            }
        }




    }
}

10.快排

/**
 * 快排
 * 该代码实现了快速排序（Quick Sort）算法的一部分，用于对给定数组的指定范围进行排序。
 * sort方法接受一个整型数组 array、一个左边界 left 和一个右边界 right 作为参数，表示对 array 数组中从索引 left 到索引 right 的元素进行排序。
 * 首先，代码定义了几个局部变量：l 和 r 分别表示当前排序范围的左右边界，temp 用于交换元素的临时存储，mid 表示选择的中间元素。
 * 接下来是一个 while 循环，在循环中进行快速排序的关键步骤：
 *     在内层的第一个 while 循环中，找到左边第一个大于等于 mid 的元素，将 l 递增，直到找到这样的元素。
 *     在内层的第二个 while 循环中，找到右边第一个小于等于 mid 的元素，将 r 递减，直到找到这样的元素。
 *     如果 l 大于等于 r，说明已经完成了一轮划分，退出循环。
 *     否则，交换 array[l] 和 array[r] 的值，将较小的元素放在 l 位置，较大的元素放在 r 位置。
 *     接下来，判断交换后的元素是否与 mid 相等，如果是，则进一步移动 l 或 r，以确保不会出现 mid 重复的情况。
 *     循环回到第一步，继续进行划分操作。
 * 循环结束后，会得到一个以 mid 为分界点的划分，左边的元素小于等于 mid，右边的元素大于等于 mid。
 * 接下来的代码片段用于递归调用 sort 方法，将左右两个划分分别进行快速排序。
 * 需要注意的是，在递归调用时，要根据边界条件判断是否需要继续进行排序。如果左边界 left 小于 r，说明左边还有元素需要排序，可以继续递归调用 sort 方法；如果右边界 right 大于 l，说明右边还有元素需要排序，同样可以继续递归调用 sort 方法。
 * 通过递归调用和不断划分子数组，最终实现了对整个数组的排序。
 * 如果左边界 left 和右边界 right 相等（即 l == r），说明当前划分的子数组中只有一个元素。在这种情况下，不需要再进行划分，直接将左边界向后移动一个位置（l += 1），将右边界向前移动一个位置（r -= 1）即可。
 */
public class Main5 {
    public static void main(String[] args) {
        int [] arr={2,4,1,6,8,1};
        sort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));

    }
    public static void sort(int [] array,int left,int right){
        int l=left;
        int r=right;
        int temp=0;
        int mid=array[(l+right)/2];
        while (l<r){
            while (array[l]<mid){
                l+=1;
            }
            while (array[r]>mid){
                r-=1;
            }
            if ((l>=r)){
                break;
            }
            //交换
            temp=array[l];
            array[l]=array[r];
            array[r]=temp;

            //如果交换完后，array[l]==mid,r--

            if (array[l]==mid){
                r-=1;
            }
            if (array[r]==mid){
                l+=1;
            }

        }
        if (l==r){
            l+=1;
            r-=1;
        }
        //向左递归
        if(left<r){
            sort(array, left, r);
        }
//        向右递归
        if(right>l){
            sort(array, l, right);
        }


    }
}

11.给你一个字符串数组，请你将字母异位词 组合在一起。可以按任意顺序返回结果列表。
 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
 
 *     首先，我们定义了一个名为GroupAnagrams的公共类。
 *     在类中，我们定义了一个静态方法groupAnagrams，它接受一个字符串数组strs作为参数，并返回一个列表，其中包含字母异位词分组的结果。
 *     在方法的开头，我们首先对输入进行了检查。如果strs为空或长度为0，则直接返回一个空列表new ArrayList<>()，表示没有任何分组。
 *     创建了一个HashMap<String, List<String>>对象map，用于存储分组的结果。其中，键为经过排序的字符串，值为属于同一组的原始字符串列表。
 *     遍历输入的字符串数组strs，使用增强型for循环依次处理每个字符串。
 *     对于当前的字符串str，我们将其转换为字符数组charArray，以便后续对字符进行排序。
 *     使用Arrays.sort(charArray)对字符数组进行排序，使得相同字母组成的异位词具有相同的排序后的字符串。
 *     将排序后的字符数组转换为字符串sortedStr，以便在map中作为键使用。
 *     检查sortedStr是否已经存在于map中，如果不存在，则将其作为键加入到map中，并将对应的值初始化为一个空列表new ArrayList<>()。
 *     无论sortedStr是否已经存在于map中，我们都可以通过map.get(sortedStr)获取到当前排序后的字符串对应的值，即属于同一组的原始字符串列表。
 *     将当前的原始字符串str添加到对应的值（列表）中，即map.get(sortedStr).add(str)。
 *     完成了所有字符串的处理后，我们使用map.values()获取map中所有的值，即分组的结果集合。然后，通过new ArrayList<>(map.values())将结果转换为列表形式并返回。
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GroupAnagrams {
    public static List<List<String>> groupAnagrams(String []strs){
        if (strs==null||strs.length==0){
            return new ArrayList<>();
        }
        HashMap<String,List<String>> map=new HashMap<>();
        for (String str:strs){
            char [] charArray=str.toCharArray();
            Arrays.sort(charArray);
            String sortStr=new String(charArray);
            if (!map.containsKey(sortStr)){
                map.put(sortStr,new ArrayList<>());
            }
            map.get(sortStr).add(str);
        }
        return new ArrayList<>();
    }
    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = groupAnagrams(strs);
        System.out.println(result);
    }
}

12.反转链表
  
  **
 * 反转链表
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }

    public static void main(String[] args) {
        // 创建示例链表 {1, 2, 3}
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);

        node1.next = node2;
        node2.next = node3;

        // 创建解决方案对象
        Solution solution = new Solution();

        // 反转链表
        ListNode reversedHead = solution.reverseList(node1);

        // 打印反转后的链表
        while (reversedHead != null) {
            System.out.print(reversedHead.val + " ");
            reversedHead = reversedHead.next;
        }
    }
}

13.HJ16动态规划

/**
 * HJ16
 * dp[i][j]表示在前i个物品中花费不超过j元的情况下能够
 * 代码中使用两层循环，外层循环遍历每个物品，内层循环遍历预算金额的范围。
 *     如果当前物品是主件（attach[i] == 0），则通过比较选择两种情况中满意度更高的一种：
 *         不购买该主件物品，即使用上一行（上一个物品）相同预算金额的满意度 dp[i-1][j]。
 *         购买该主件物品，使用上一行预算金额减去当前主件物品的价格 j-v[i]，并加上购买该主件物品的满意度 v[i] * w[i]。
 *     如果当前物品是附件（attach[i] > 0），则通过比较选择两种情况中满意度更高的一种：
 *         不购买该附件物品，即使用上一行（上一个物品）相同预算金额的满意度 dp[i-1][j]。
 *         购买该附件物品，使用上一行预算金额减去当前附件物品的价格和所属主件物品的价格 j-v[i]-attachPrice[i]，并加上购买该附件物品的满意度和所属主件物品的满意度 v[i] * w[i] + attachPrice[i] * attachWeight[i]。
 * 最终得到的 dp[m][N] 即为可获得的最大满意度。
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int m = scanner.nextInt();

        int[] v = new int[m + 1];
        int[] w = new int[m + 1];
        int[] attach = new int[m + 1];
        int[] attachPrice = new int[m + 1];
        int[] attachWeight = new int[m + 1];

        for (int i = 1; i <= m; i++) {
            v[i] = scanner.nextInt();
            w[i] = scanner.nextInt();
            attach[i] = scanner.nextInt();
            if (attach[i] > 0) {
                attachPrice[i] = v[attach[i]];
                attachWeight[i] = w[attach[i]];
            }
        }

        int[][] dp = new int[m + 1][N + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= N; j++) {
                if (attach[i] == 0) {
                    if (j >= v[i]) {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - v[i]] + v[i] * w[i]);
                    }
                } else {
                    if (j >= v[i] + attachPrice[i]) {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - v[i] - attachPrice[i]] + v[i] * w[i] + attachPrice[i] * attachWeight[i]);
                    }
                }
            }
        }

        System.out.println(dp[m][N]);
    }
}
14.HJ1
/**
 * HJ1
 */
public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str=scanner.nextLine();
        lastWord(str);
        System.out.println(lastWord(str));

    }
    public static int lastWord(String input){
        int index=input.lastIndexOf(" ");
        String str=input.substring(index+1);
        return str.length();
    }
}

15.HJ2
/**
 * HJ2
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase();
        char targetChar = scanner.nextLine().toLowerCase().charAt(0);
        int count = countCharacter(input, targetChar);
        System.out.println(count);
    }

    private static int countCharacter(String input, char targetChar) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == targetChar) {
                count++;
            }
        }
        return count;
    }
}

16.给你一个字符串 s，找到 s 中最长的回文子串。

如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。

 

示例 1：

输入：s = "babad"
输出："bab"
解释："aba" 同样是符合题意的答案。

示例 2：

输入：s = "cbbd"
输出："bb"

 

提示：

    1 <= s.length <= 1000
    s 仅由数字和英文字母组成


代码：

public class LongestPalindrome {
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return right - left - 1;
    }

    public static void main(String[] args) {
        String s = "babad";
        String longestPalindrome = longestPalindrome(s);
        System.out.println(longestPalindrome);
    }
}

在这个解决方案中，我们使用了中心扩展法。我们遍历字符串中的每个字符，并以该字符为中心向两边扩展，检查是否有回文子串。我们需要考虑两种情况：回文串长度为奇数和回文串长度为偶数。最终，我们更新最长回文子串的起始位置和结束位置，并返回最长回文子串。





