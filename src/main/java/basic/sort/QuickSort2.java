package basic.sort;

/**
 * 通过递归，指定中轴值，比中轴大放置右边，比中轴小放置左边
 *
 * Created by vanki on 2018/10/15 17:34.
 */
public class QuickSort2 {

    public static void main(String[] args) {
        int[] arr = {4, 2, 7, 5, 0, 9, 8, 6, 5, 4, 3, 1};
        quickSort(arr, 0, arr.length - 1);

    }

    private static void quickSort(int[] arr, int low, int high) {
        if (high <= low) return;
        int middle = getMiddle(arr, low, high);
        quickSort(arr, low, middle - 1);
        quickSort(arr, middle + 1, high);
    }

    private static int getMiddle(int[] arr, int low, int high) {
        int tmp = arr[low];
        while (low < high) {
            while (low < high && tmp <= arr[high]) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = tmp;
        return low;
    }
}
