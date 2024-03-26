package hot_100;

/**
 * 寻找两个正序数组的中位数 -- 数组, 二分查找, 分治
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _4_寻找两个正序数组的中位数 {

    // 算法的时间复杂度应该为 O(log (m+n))

    /**
     * 1、合并有序数组并找到中位数 -- 2ms(33.34%), 43.8MB(7.71%)
     * <p>
     * 时间复杂度为 O(m + n)，空间复杂度为 O(m + n)
     */
    class Solution1 {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int len1 = nums1.length;
            int len2 = nums2.length;
            int[] nums = new int[len1 + len2];
            int i = 0;
            int j = 0;
            int k = 0;
            while (i < len1 && j < len2) {
                if (nums1[i] < nums2[j]) {
                    nums[k] = nums1[i];
                    i++;
                } else {
                    nums[k] = nums2[j];
                    j++;
                }
                k++;
            }
            while (i < len1) {
                nums[k] = nums1[i];
                i++;
                k++;
            }
            while (j < len2) {
                nums[k] = nums2[j];
                j++;
                k++;
            }
            if (nums.length % 2 != 0) {
                return (double) nums[nums.length / 2];
            } else {
                int pre = nums[nums.length / 2 - 1];
                int mid = nums[nums.length / 2];
                return (pre + mid) / 2.0;
            }
        }
    }

    /**
     * 2、分治 -- 1ms(100%), 43.8MB(9.44%)
     * <p>
     * 时间复杂度为 O( log(m + n) ), 空间复杂度为 O( log(m + n) )
     */
    class Solution2 {
        private int m;
        private int n;
        private int[] nums1;
        private int[] nums2;

        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            m = nums1.length;
            n = nums2.length;
            this.nums1 = nums1;
            this.nums2 = nums2;
            int a = f(0, 0, (m + n + 1) / 2); // 找到中位数左边的元素
            int b = f(0, 0, (m + n + 2) / 2); // 找到中位数右边的元素
            return (a + b) / 2.0; // 返回中位数
        }

        private int f(int i, int j, int k) {
            if (i >= m) {
                return nums2[j + k - 1]; // nums1 数组已经遍历完，返回 nums2 数组中对应位置的元素
            }
            if (j >= n) {
                return nums1[i + k - 1]; // nums2 数组已经遍历完，返回 nums1 数组中对应位置的元素
            }
            if (k == 1) {
                return Math.min(nums1[i], nums2[j]); // 达到中位数位置，返回两个数组当前位置上较小的元素
            }
            int p = k / 2; // 将 k 分成两部分
            int x = i + p - 1 < m ? nums1[i + p - 1] : 1 << 30; // 获取 nums1 数组中第 p 个元素，如果超出数组范围，设置为一个较大的值
            int y = j + p - 1 < n ? nums2[j + p - 1] : 1 << 30; // 获取 nums2 数组中第 p 个元素，如果超出数组范围，设置为一个较大的值
            return x < y ? f(i + p, j, k - p) : f(i, j + p, k - p); // 如果 x 小于 y，则在 nums1 数组的右侧或者 nums2 数组的左侧继续查找，否则在 nums2 数组的右侧或者 nums1 数组的左侧继续查找
        }
    }
}
