import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     8/2023
 **************************************************************************** */
public class Percolation {
    private int size;
    private boolean[][] grid;
    private int[] obIndex;
    private int openSites;
    private WeightedQuickUnionUF unionFind;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be less than 1");
        }
        size = n;
        grid = new boolean[size][size];
        obIndex = new int[(size * size) + 2];
        for (int i = 0; i < (size * size) + 2; i++) {
            obIndex[i] = i;
        }
        openSites = 0;
        unionFind = new WeightedQuickUnionUF((size * size) + 2);
    }

    private int conversion(int r, int c) {
        return ((size * r) + c) + 1;
        // converts grid[][] to obIndex[] index
    }

    private boolean isValid(int r, int c) {
        int n = size;
        int row = r + 1;
        int col = c + 1;
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("index " + row + " is not between 1 and " + n);
        }
        else {
            if (col < 1 || col > n) {
                throw new IllegalArgumentException(
                        "index " + col + " is not between 1 and " + n);
            }
            else {
                return true;
            }
        }
    }

    private boolean validAdjacent(int r, int c) {
        int n = size;
        int row = r + 1;
        int col = c + 1;
        if (row < 1 || row > n) {
            return false;
        }
        else {
            if (col < 1 || col > n) {
                return false;
            }
            else {
                return true;
            }
        }
    }

    public void open(int row, int col) {
        // 1. validate row,col indices;  2. mark the site as open; 3. link to open neighbors via UF methods 4. link to topParent & bottomParent if applicable
        int gridRow = row - 1;
        int gridCol = col - 1;
        if (isValid(gridRow, gridCol) && !grid[gridRow][gridCol]) {
            grid[gridRow][gridCol] = true;
            openSites++;
        }
        int site = conversion(gridRow, gridCol);
        int siteComponent = unionFind.find(site);
        int bottomSite = (size * size) + 1;
        int topSite = 0;

        if (isValid(gridRow, gridCol) && gridRow == 0) {
            unionFind.union(siteComponent, unionFind.find(topSite));
            obIndex[site] = unionFind.find(site);
            obIndex[topSite] = unionFind.find(topSite);
        }
        if (isValid(gridRow, gridCol) && gridRow == (size - 1)) {
            unionFind.union(siteComponent, unionFind.find(bottomSite));
            obIndex[site] = unionFind.find(site);
            obIndex[bottomSite] = unionFind.find(bottomSite);
        }
        if (validAdjacent(gridRow - 1, gridCol) && grid[gridRow - 1][gridCol]) {
            int top = conversion(gridRow - 1, gridCol);
            unionFind.union(siteComponent, unionFind.find(top));
            obIndex[site] = unionFind.find(site);
            obIndex[top] = unionFind.find(top);
        }
        if (validAdjacent(gridRow, gridCol - 1) && grid[gridRow][gridCol - 1]) {
            int left = conversion(gridRow, gridCol - 1);
            unionFind.union(siteComponent, unionFind.find(left));
            obIndex[site] = unionFind.find(site);
            obIndex[left] = unionFind.find(left);
        }
        if (validAdjacent(gridRow + 1, gridCol) && grid[gridRow + 1][gridCol]) {
            int bottom = conversion(gridRow + 1, gridCol);
            unionFind.union(siteComponent, unionFind.find(bottom));
            obIndex[site] = unionFind.find(site);
            obIndex[bottom] = unionFind.find(bottom);
        }
        if (validAdjacent(gridRow, gridCol + 1) && grid[gridRow][gridCol + 1]) {
            int right = conversion(gridRow, gridCol + 1);
            unionFind.union(siteComponent, unionFind.find(right));
            obIndex[site] = unionFind.find(site);
            obIndex[right] = unionFind.find(right);
        }
    }

    public boolean isOpen(int row, int col) {
        int gridRow = row - 1;
        int gridCol = col - 1;
        return isValid(gridRow, gridCol) && grid[gridRow][gridCol];
    }

    public boolean isFull(int row, int col) {
        int gridRow = row - 1;
        int gridCol = col - 1;
        int siteIndex = conversion(gridRow, gridCol);
        return isValid(gridRow, gridCol) && unionFind.find(obIndex[0]) == unionFind.find(
                obIndex[obIndex[siteIndex]]);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        int topParent = unionFind.find(obIndex[0]);
        int bottomParent = unionFind.find(obIndex[obIndex[(size * size) + 1]]);
        return topParent == bottomParent;
    }

    public static void main(String[] args) {

    }
}

