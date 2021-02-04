import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class LogicHandler {
    private Canvas canvas;
    private GraphicsContext gc;
    private Tile[][] tiles;
    private Button[][] buttons;

    private static final int TILESIZE = 12;

    public LogicHandler(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        tiles = new Tile[(int) canvas.getHeight() / 10][(int) canvas.getHeight() / 10];
        buttons = new Button[(int) canvas.getHeight() / 10][(int) canvas.getHeight() / 10];
    }

    public LogicHandler() {
    }

    public GridPane setup() {
        GridPane grid = new GridPane();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                tiles[y][x] = new Tile(x * TILESIZE, y * TILESIZE);
                buttons[y][x] = new Button(" ");
                buttons[y][x].setFont(Font.font("Monospaced", 5));
                buttons[y][x].setPrefSize(5, 5);
                int finalX = x;
                int finalY = y;
                buttons[y][x].setOnAction(e -> {
                    Tile t = tiles[finalY][finalX];
                    Button b = buttons[finalY][finalX];

                    if (t.isAlive()) {
                        b.setText(" ");
                    } else {
                        b.setText("X");
                    }

                    t.setAlive(!t.isAlive());
                });
                buttons[y][x].setPrefSize(5, 5);
                grid.add(buttons[y][x], x, y);
            }
        }
        return grid;
    }

    public void next() {
        Tile[][] lastTiles = new Tile[tiles.length][tiles[0].length];
        for (int y = 0; y < lastTiles.length; y++) {
            for (int x = 0; x < lastTiles[y].length; x++) {
                lastTiles[y][x] = tiles[y][x].copy();
            }
        }
        for (int y = 0; y < lastTiles.length; y++) {
            for (int x = 0; x < lastTiles[y].length; x++) {
                int aliveNeighbours = 0;
                if (x - 1 >= 0 && y - 1 >= 0) {
                    if (lastTiles[y - 1][x - 1].isAlive()) aliveNeighbours++;
                }

                if (y - 1 >= 0) {
                    if (lastTiles[y - 1][x].isAlive()) aliveNeighbours++;
                }

                if (x + 1 < lastTiles[y].length && y - 1 >= 0) {
                    if (lastTiles[y - 1][x + 1].isAlive()) aliveNeighbours++;
                }

                if (x - 1 >= 0) {
                    if (lastTiles[y][x - 1].isAlive()) aliveNeighbours++;
                }

                if (x + 1 < lastTiles[y].length) {
                    if (lastTiles[y][x + 1].isAlive()) aliveNeighbours++;
                }

                if (x - 1 >= 0 && y + 1 < lastTiles.length) {
                    if (lastTiles[y + 1][x - 1].isAlive()) aliveNeighbours++;
                }

                if (y + 1 < lastTiles.length) {
                    if (lastTiles[y + 1][x].isAlive()) aliveNeighbours++;
                }

                if (y + 1 < lastTiles.length && x + 1 < lastTiles[y].length) {
                    if (lastTiles[y + 1][x + 1].isAlive()) aliveNeighbours++;
                }

                if (aliveNeighbours < 2 && tiles[y][x].isAlive()) tiles[y][x].setAlive(false);
                if (aliveNeighbours > 3 && tiles[y][x].isAlive()) tiles[y][x].setAlive(false);
                if (aliveNeighbours == 3 && !tiles[y][x].isAlive()) tiles[y][x].setAlive(true);
            }
        }

        for (Tile[] ta : tiles) {
            for (Tile t : ta) {
                if (t.isAlive()) {
                    gc.setFill(WHITE);
                } else {
                    gc.setFill(BLACK);
                }
                gc.fillRect(t.getX(), t.getY(), TILESIZE, TILESIZE);
            }
        }
    }
}
