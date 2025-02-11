import java.util.Random;
import java.util.Scanner;
public class Minesweeper {

        private static final int SIZE = 10; // 游戏区域大小
        private static final int MINES = 10; // 雷的数量
        private static final char MINE = '*'; // 雷的表示符号
        private static final char COVERED = '-'; // 未点击区域的表示符号
        private static final char EMPTY = ' '; // 无雷区域的表示符号

        private char[][] board; // 游戏区域（显示给玩家）
        private boolean[][] mines; // 雷的位置
        private boolean[][] revealed; // 是否已点击

        public Minesweeper() {
            board = new char[SIZE][SIZE];
            mines = new boolean[SIZE][SIZE];
            revealed = new boolean[SIZE][SIZE];
            initializeBoard();
            placeMines();
            calculateNumbers();
        }

        // 初始化游戏区域
        private void initializeBoard() {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    board[i][j] = COVERED;
                    mines[i][j] = false;
                    revealed[i][j] = false;
                }
            }
        }

        // 随机放置雷
        private void placeMines() {
            Random random = new Random();
            int placedMines = 0;
            while (placedMines < MINES) {
                int x = random.nextInt(SIZE);
                int y = random.nextInt(SIZE);
                if (!mines[x][y]) {
                    mines[x][y] = true;
                    placedMines++;
                }
            }
        }

        // 计算每个格子周围的雷数
        private void calculateNumbers() {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (!mines[i][j]) {
                        int count = countAdjacentMines(i, j);
                        if (count > 0) {
                            board[i][j] = (char) ('0' + count);
                        } else {
                            board[i][j] = EMPTY;
                        }
                    }
                }
            }
        }

        // 计算某个格子周围的雷数
        private int countAdjacentMines(int x, int y) {
            int count = 0;
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && i < SIZE && j >= 0 && j < SIZE && mines[i][j]) {
                        count++;
                    }
                }
            }
            return count;
        }

        // 显示游戏区域
        private void displayBoard() {
            System.out.print("  ");
            for (int i = 0; i < SIZE; i++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i < SIZE; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < SIZE; j++) {
                    if (revealed[i][j]) {
                        System.out.print(board[i][j] + " ");
                    } else {
                        System.out.print(COVERED + " ");
                    }
                }
                System.out.println();
            }
        }

        // 点击某个格子
        private boolean click(int x, int y) {
            if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || revealed[x][y]) {
                return false; // 无效点击
            }
            revealed[x][y] = true;
            if (mines[x][y]) {
                return true; // 踩到雷
            }
            if (board[x][y] == EMPTY) {
                // 如果是空白区域，递归点击周围的格子
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (i >= 0 && i < SIZE && j >= 0 && j < SIZE) {
                            click(i, j);
                        }
                    }
                }
            }
            return false;
        }

        // 检查是否胜利
        private boolean checkWin() {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (!mines[i][j] && !revealed[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }

        // 游戏主循环
        public void play() {
            Scanner scanner = new Scanner(System.in);
            boolean gameOver = false;
            while (!gameOver) {
                displayBoard();
                System.out.print("输入要点击的坐标 (x y): ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if (click(x, y)) {
                    System.out.println("你踩到雷了！游戏结束！");
                    gameOver = true;
                } else if (checkWin()) {
                    System.out.println("恭喜你，扫雷成功！");
                    gameOver = true;
                }
            }
            scanner.close();
        }

        public static void main(String[] args) {
            Minesweeper game = new Minesweeper();
            game.play();
        }
}
