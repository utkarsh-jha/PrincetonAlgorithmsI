

public class Percolation {

    private final int total;
    private final int matrixSize;
    private final int[] a;
    private final int[] size;
    private int openSites = 0;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.matrixSize = n;
        this.total = n * n;
        a = new int[total];
        size = new int[total];
        for (int i = 0; i < total; i++) {
            a[i] = -1;
            size[i] = 1;
        }
    }

    private int mapRowColToIndex(int row, int col) {
        return (row - 1) * matrixSize + (col - 1);
    }

    private boolean isValid(int row, int col) {
        if (row > matrixSize || col > matrixSize || row <= 0 || col <= 0) {
            return false;
        }
        return true;
    }

    public void open(int row, int col) {
        if (row > matrixSize || col > matrixSize || row <= 0 || col <= 0) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            int index = mapRowColToIndex(row, col);
            a[index] = index;
            int above = index - matrixSize;
            int below = index + matrixSize;
            int left = index - 1;
            int right = index + 1;
            if (isValid(row-1, col) && isOpen(above)) {
                union(above, index);
            }

            if (isValid(row+1, col) && isOpen(below)) {
                union(below, index);
            }

            if (isValid(row, col+1) && isOpen(right)) {
                union(right, index);
            }

            if (isValid(row, col-1) && isOpen(left)) {
                union(left, index);
            }
            openSites = openSites + 1;
        }

    }

    public boolean isOpen(int row, int col) {
        if (row > matrixSize || col > matrixSize || row <= 0 || col <= 0) {
            throw new IllegalArgumentException();
        }
        int index = mapRowColToIndex(row, col);
        return isOpen(index);
    }

    private boolean isOpen(int index) {
        return a[index] != -1;
    }

    public boolean isFull(int row, int col) {
        if (row > matrixSize || col > matrixSize || row <= 0 || col <= 0) {
            throw new IllegalArgumentException();
        }
        int index = mapRowColToIndex(row, col);
        if (isOpen(index)) {
            for (int i = 0; i < matrixSize; i++) {
                if (isOpen(i) && isConnected(index, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        for (int j = 0; j < matrixSize; j++) {
            if (isOpen(j)) {
                for (int k = total - matrixSize; k < total; k++) {
                    if (isOpen(k) && isConnected(k, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int root(int index) {
        while (index != a[index]) {
            index = a[index];
        }
        return index;
    }

    private boolean isConnected(int index1, int index2) {
        return root(index1) == root(index2);
    }

    private void union(int index1, int index2) {
        int i = root(index1);
        int j = root(index2);
        if (i == j) return;

        // make smaller root point to larger one
        if (size[i] < size[j]) {
            a[i] = j;
            size[j] += size[i];
        }
        else {
            a[j] = i;
            size[i] += size[j];
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open(5, -1);
    }


}
