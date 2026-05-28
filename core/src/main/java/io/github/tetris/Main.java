package io.github.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion image;
    private ShapeDrawer shapeDrawer;
    private InputProcessor inputProcessor;

    private Tetris tetris;
    private BitmapFont font;

    @Override
    public void create() {
        font = new BitmapFont();
        font.getData().setScale(2);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Deixa o texto menos "pixelado"
        batch = new SpriteBatch();
        image = new TextureRegion(new Texture("blankTexture.png"));
        shapeDrawer = new ShapeDrawer(batch, image);
        tetris = new Tetris();

        inputProcessor = new InputProcessor(tetris);
        Gdx.input.setInputProcessor(inputProcessor);
        //test();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        tetris.fallTimer += Gdx.graphics.getDeltaTime();
        if (tetris.fallTimer >= tetris.fallInterval) {
            // System.out.println("one seconds passing");
            // System.out.println("time reseting");
            tetris.fallTimer = 0;
            tetris.lockTimer = 0;
            tetris.piece.canFall();

        }

        if (tetris.piece.isValidMove(new Vector2(tetris.piece.position.x, tetris.piece.position.y - 1),
                tetris.piece.blocks)) {
            tetris.lockTimer = 0;
            // System.out.println("vendo se reseto");
        } else {
            tetris.lockTimer += Gdx.graphics.getDeltaTime();
            if (tetris.lockTimer >= tetris.lockDelay) {
                tetris.lockPiece();
                tetris.spawnPiece();
                tetris.lockTimer = 0;
            }
        }
        //COMEÇO DOS DESENHOS
        batch.begin();

        


        batch.setColor(Color.WHITE);
        tetris.drawGrid(shapeDrawer);
        tetris.drawBox(shapeDrawer, 20, 80, tetris.piece.num, tetris.piece);

        //poderia juntar os 3 ifs, mas fica mais organizado assim
        if(!tetris.gameOver){
            tetris.piece.draw(shapeDrawer);
            tetris.piece.drawGhostPiece(shapeDrawer);
        }

        if (tetris.gameOver) {
            tetris.gameOver();
        }
        
        if(tetris.gameOver){
            font.setColor(Color.ORANGE);
            font.draw(batch, "Game Over", Gdx.graphics.getWidth()/2f - 50, Gdx.graphics.getHeight()/2f);
        }

        font.draw(batch, "Score: " + tetris.score, 10, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Lines: " + tetris.lines, 10, Gdx.graphics.getHeight() - 50);
        

        batch.end();
        //FIM DOS DESENHOS
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void test() {
        for (int i = 0; i < tetris.gridWidth; i++) {
            for (int j = 0; j < tetris.gridHeight; j++) {
                tetris.grid[i][j] = 0;
            }
        }

        for (int i = 0; i < tetris.gridWidth - 1; i++) {
            for (int j = 0; j < 3; j++) {
                tetris.grid[i][j] = 1;
            }
        }
    }
}
