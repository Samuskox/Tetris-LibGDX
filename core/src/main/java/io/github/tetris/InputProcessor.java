package io.github.tetris;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class InputProcessor extends InputAdapter {
    private Tetris tetris;

    public InputProcessor(Tetris tetris) {
        this.tetris = tetris;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            //System.out.println("Rotate");
            tetris.piece.blocks = tetris.piece.rotate();
        }

        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            tetris.piece.move(new Vector2(0, -1));
            //System.out.println("x: " + tetris.piece.position.x + " y: " + tetris.piece.position.y);
        }

        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            tetris.piece.move(new Vector2(-1, 0));
            //System.out.println("x: " + tetris.piece.position.x + " y: " + tetris.piece.position.y);
        }

        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            tetris.piece.move(new Vector2(1, 0));
            //System.out.println("x: " + tetris.piece.position.x + " y: " + tetris.piece.position.y);
        }

        if (keycode == Input.Keys.SPACE) {
            tetris.hardDrop();
            tetris.lockPiece();
            tetris.spawnPiece();
        }

        if (keycode == Input.Keys.R) {
            for (int i = 0; i < tetris.gridWidth; i++) {
                for (int j = 0; j < tetris.gridHeight; j++) {
                    tetris.grid[i][j] = 0;
                }
            }

            // for (int i = 0; i < tetris.gridWidth - 1; i++) {
            //     for (int j = 0; j < 17; j++) {
            //         tetris.grid[i][j] = 1;
            //     }
            // }
                tetris.gameOver = false;
                tetris.score = 0;
                tetris.lines = 0;
                tetris.spawnPiece();
        }

        return false;
    }

}
