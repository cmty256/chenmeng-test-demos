package hot_100;


/**
 * 移动零 -- 数组, 双指针
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _283_移动零 {

    /* 给定一个数组 nums，
       编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
       请注意 ，必须在不复制数组的情况下原地对数组进行操作。 */

    /**
     * 1、位置交换 -- 1ms(99.96%), 44.23MB(16.49%)
     * <p>
     * 时间复杂度分析：由于只对数组进行了一次遍历，所以时间复杂度为 O(n)，其中 n 是数组的长度。
     * <p>
     * 空间复杂度分析：除了输入数组之外，没有使用额外的数据结构来存储信息，因此空间复杂度为 O(1)。
     */
    class Solution {
        public void moveZeroes(int[] nums) {
            // j用来记录非0元素的索引
            int j = 0;
            // 遍历数组
            for (int i = 0; i < nums.length; i++) {
                // 如果当前元素不为0
                if (nums[i] != 0) {
                    // 记录当前元素
                    int tmp = nums[j];
                    // 将当前元素赋值给非0元素的索引
                    nums[j] = nums[i];
                    // 将记录的当前元素赋值给当前元素
                    nums[i] = tmp;
                    // 非0元素的索引加1
                    j++;
                }
            }
        }
    }

    /**
     * 2、新数组拷贝到原数组 -- 1ms(99.96%), 43.45MB(98.82%)
     * <p>
     * 时间复杂度：总的时间复杂度为两个循环的时间复杂度之和，即 O(n + n) = O(2n)。
     * <p>
     * 空间复杂度为：由于创建了一个新的数组 arr 来存储非零元素，所以空间复杂度为 O(n)，其中 n 是数组的长度。
     */
    class Solution2 {
        public void moveZeroes(int[] nums) {

            int[] arr = new int[nums.length];
            int count = 0;
            // 遍历数组nums，将非0元素放入新的数组arr中
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    arr[count] = nums[i];
                    count++;
                }
            }
            // 将新数组arr中的剩余位置都赋值为0
            for (int j = count; j < nums.length; j++) {
                arr[j] = 0;
            }
            // 将新数组arr的值拷贝到原数组nums中
            // nums = Arrays.copyOf(arr,arr.length);
            System.arraycopy(arr, 0, nums, 0, arr.length);
        }
    }
}
