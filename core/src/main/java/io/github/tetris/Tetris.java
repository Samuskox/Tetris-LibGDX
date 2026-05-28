package io.github.tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Tetris {
    int cellSize = 24;
    int spaceBetweenCells = 1;
    int gridWidth = 10;
    int gridHeight = 20;
    int grid[][] = new int[gridWidth][gridHeight];

    Piece piece;

    List<Integer> bagPieces = new ArrayList<>();

    float fallTimer = 0;
    float fallInterval = 1.0f;

    float lockTimer = 0;
    float lockDelay = 0.5f;

    int score = 0;
    int lines = 0;

    Boolean gameOver = false;
    

    public Tetris(){
        for(int i = 0;i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                grid[i][j] = 0;
            }
        }

        spawnPiece();
    }

    public void spawnPiece(){

        int num = getNextPieceNum();
        Vector2 spawnPosition = new Vector2(4,19);
        Piece protPiece = new Piece(num, this, spawnPosition);
        if(isSpawnBlocked(protPiece, spawnPosition)){
            gameOver = true;
        } else {
            piece = new Piece(num, this, spawnPosition);
        }
    }

    public void drawGrid(ShapeDrawer shapeDrawer){
        
        shapeDrawer.setColor(Color.RED);
        for(int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j] == 1){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.CYAN);
                } else if (grid[i][j] == 2){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.GREEN);
                } else if (grid[i][j] == 3){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.RED);
                } else if (grid[i][j] == 4){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.PURPLE);
                } else if (grid[i][j] == 5){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.YELLOW);
                } else if (grid[i][j] == 6){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.ORANGE);
                } else if (grid[i][j] == 7){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.BLUE);
                } else if (grid[i][j] == 8){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.GRAY);
                } else if (grid[i][j] == 9){
                    shapeDrawer.filledRectangle(
                        (i * cellSize) + Gdx.graphics.getWidth()/2 - (gridWidth * cellSize)/2,
                         (j * cellSize), cellSize - (spaceBetweenCells * 2),
                          (cellSize - (spaceBetweenCells * 2)) + 1,
                           Color.WHITE);
                }

            }
        }
        drawLines(shapeDrawer);
    }

    public void drawLines(ShapeDrawer shapeDrawer){
        shapeDrawer.setColor(Color.WHITE);
        float offsetX = (Gdx.graphics.getWidth()/2f 
                    - (gridWidth * cellSize)/2f) - 1;
        for(int i = 0; i <= gridWidth; i++){
            shapeDrawer.line(offsetX + (i * cellSize), 0, offsetX + (i * cellSize), gridHeight * cellSize, 2);
        }
        for(int j = 0; j <= gridHeight; j++){
            shapeDrawer.line(offsetX, (j * cellSize), offsetX + (gridWidth * cellSize), j * cellSize, 2);
        }

        //shapeDrawer.line(offsetX, offsetX, offsetX, offsetX, offsetX);
    }

    public void drawBox(ShapeDrawer shapeDrawer, int x, int y, int num, Piece pieceBox){
        shapeDrawer.filledRectangle(
            x,
            y,
            150,
              90,
               Color.BLACK);
        shapeDrawer.rectangle(x, y, 150, 90, Color.WHITE);
        Rectangle box = new Rectangle(x, y, 150, 90);
        shapeDrawer.rectangle(box, 4);
        Vector2[] boxBlocks = null;
        Color color = null;
        switch (num) {
            case 1:
                boxBlocks = piece.iP;
                color = piece.color;
                y += 15; 
                break;
            case 2:
                boxBlocks = piece.sP;
                color = piece.color;
                x += 15;
                break;
            case 3:
                boxBlocks = piece.zP;
                color = piece.color;
                x += 30;
                y += 15;
                break;
            case 4:
                boxBlocks = piece.tP;
                x += 5;
                y += 15;
                color = piece.color;
                break;
            case 5:
                boxBlocks = piece.oP;
                color = piece.color;
                y += 5;
                break;
            case 6:
                boxBlocks = piece.lP;
                color = piece.color;
                x += 10;
                break;
            case 7: 
                boxBlocks = piece.jP;
                color = piece.color;
                x += 10;
                break;
            default:
                break;
        }
        for(Vector2 block : boxBlocks){
            shapeDrawer.filledRectangle(
                x + 50 + (block.x * cellSize),
                y + 20 + (block.y * cellSize),
                cellSize - (spaceBetweenCells * 2),
                cellSize - (spaceBetweenCells * 2),
                color);
        }

    }

    public void lockPiece(){
        for(Vector2 block : piece.blocks){
            int x = (int)(piece.position.x + block.x);
            int y = (int)(piece.position.y + block.y);

            if(x >= 0 && x < gridWidth && y >= 0 && y < gridHeight){
                grid[x][y] = piece.num;
            } else {
                    gameOver = true;
                System.out.println("Game Over");
            }
        }
        cleanLine();
        System.out.println("lockou a peça");
    }

    public void hardDrop(){
        Boolean canFall = true;

        while(canFall){
            if(piece.canFall()){
                //System.out.println("caindo");
            } else {
                canFall = false;
            }
        }
    }


    public void cleanLine(){

        int linescore = 0;
        
        for(int i = 0; i <= gridHeight - 1; i++){
            boolean lineFull = true;
            for(int j = 0; j < gridWidth; j++){
                if(grid[j][i] == 0){
                    lineFull = false;
                    break;
                }
            }
            if(lineFull){
                removeline(i--);
                linescore++;
                lines++;
            }
        }

        updateScore(linescore);
    }

    public void removeline(int lineIndex){
        for(int i = lineIndex; i < gridHeight - 1; i++){
            for(int j = 0; j < gridWidth; j++){
                grid[j][i] = grid[j][i + 1];
            }
        }
        for(int j = 0; j < gridWidth; j++){
            grid[j][gridHeight - 1] = 0;
        }
    }

    public Boolean isSpawnBlocked(Piece piece, Vector2 spawnPosition){
        for(Vector2 block : piece.blocks){
            int x = (int)(spawnPosition.x + block.x);
            int y = (int)(spawnPosition.y + block.y);

            if(x < 0 || x >= gridWidth || y < 0 ){
                return true;
            }

            if(y >= gridHeight){
                continue;
            }

            //System.out.println("x: " + x + " y: " + y);
            if(grid[x][y] >= 1){
                return true;
            }
        }
        return false;
    }

    public void gameOver(){
        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                if(grid[i][j] >= 1){
                    grid[i][j] = 8;
                }
            }
        }
    }

    public void refillPieceQueue(){
        bagPieces.add(1);
        bagPieces.add(2);
        bagPieces.add(3);
        bagPieces.add(4);
        bagPieces.add(5);
        bagPieces.add(6);
        bagPieces.add(7);
        Collections.shuffle(bagPieces);
    }

    public int getNextPieceNum(){
        System.out.println(bagPieces);
        if(bagPieces.isEmpty()){
            refillPieceQueue();
        }
        return bagPieces.remove(0);
    }

    public void updateScore(int linesCleared){
        //System.out.println("Score: " + score);
        switch(linesCleared){
            case 1: 
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
        //System.out.println("After Score: " + score);
    }
    
}
