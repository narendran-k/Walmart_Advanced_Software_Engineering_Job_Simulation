import java.util.ArrayList;
import java.util.List;

public class PowerOfTwoMaxHeap {
    private final int numChildren;
    private final List<Integer> heap;

    public PowerOfTwoMaxHeap(int numChildren) {
        if (numChildren < 0) {
            throw new IllegalArgumentException("Number of children must be non-negative.");
        }
        this.numChildren = numChildren;
        this.heap = new ArrayList<>();
    }

    // Insert a new value into the heap
    public void insert(int value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    // Pop the maximum value from the heap
    public int popMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty.");
        }
        int max = heap.get(0);
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);
        siftDown(0);
        return max;
    }

    // Sift up to maintain heap property
    private void siftUp(int index) {
        int value = heap.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / (numChildren + 1);
            if (value > heap.get(parentIndex)) {
                heap.set(index, heap.get(parentIndex));
                index = parentIndex;
            } else {
                break;
            }
        }
        heap.set(index, value);
    }

    // Sift down to maintain heap property
    private void siftDown(int index) {
        int value = heap.get(index);
        int childrenCount = (int) Math.pow(2, numChildren);
        int firstChildIndex = index * childrenCount + 1;
        int maxIndex = index;

        for (int i = 0; i < childrenCount; i++) {
            int childIndex = firstChildIndex + i;
            if (childIndex < heap.size() && heap.get(childIndex) > heap.get(maxIndex)) {
                maxIndex = childIndex;
            }
        }

        if (maxIndex != index) {
            heap.set(index, heap.get(maxIndex));
            siftDown(maxIndex);
        } else {
            heap.set(index, value);
        }
    }

    // Get the current size of the heap
    public int size() {
        return heap.size();
    }

    // Check if the heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Get the maximum value without removing it
    public int peekMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty.");
        }
        return heap.get(0);
    }

    public static void main(String[] args) {
        PowerOfTwoMaxHeap heap = new PowerOfTwoMaxHeap(2); // Each node has 4 children (2^2)

        heap.insert(10);
        heap.insert(15);
        heap.insert(5);
        heap.insert(20);
        heap.insert(30);
        heap.insert(25);

        System.out.println("Max value: " + heap.popMax()); // Should return 30
        System.out.println("Max value: " + heap.popMax()); // Should return 25
    }
}
