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

3.给定一个整数组





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
```

