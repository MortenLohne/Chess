package Frontend;

import Backend.Chess;
import Backend.Piece;
import BackendImprovement.Board;
import Bot.Bot04;
import Bot.Bot05;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Graphcis2 extends Application {

    //Settings================================================
    public static final boolean DEV_LISTENER = true;

    //Frontend ===============================================
    private Button[][] tileButtons = new Button[8][8];
    private GridPane grid = new GridPane();
    public static Image[][] chessPieceIcons = new Image[2][6];
    private int previouslyClickedX;
    private int previouslyClickedY;
    private boolean someTileIsSelected;
    private Background previousSelectedColor;
    private boolean botHasMadeMove = true;

    //Backend ================================================
    public static Board mainBoard;

    /**
     * Where everything starts!
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) {
        setup();
        Button resetButton = new Button("Reset board");
        resetButton.setBackground(getBackground(Color.GREEN));
        resetButton.setOnAction(e -> {
            mainBoard = new Board();
            botHasMadeMove = true;
            drawBoard();
        });
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(resetButton);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(buttonBox);
        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(grid, stackPane);
        Scene mainScene = new Scene(mainBox);
        stage.setScene(mainScene);
        stage.setTitle("Chess");
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        resetButton.setMinWidth(stage.getWidth());
        resetButton.setMinHeight(resetButton.getHeight());
    }

    /**
     * Initialize all kinds of stuff
     */
    private void setup() {
        mainBoard = new Board();
        importChessPieces();
        initialiseButtons();
        setActionListeners();
        drawBoard();
    }

    /**
     * Adds action-listeners for all the buttons
     */
    private void setActionListeners() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tileButtons[x][y].setOnMouseClicked(e -> {
                    if (DEV_LISTENER) {
                        devActionListener(e);
                    } else {
                        botActionListener(e);
                    }
                });
            }
        }
    }

    /**
     * Bot-action-listener (when playing against a bot)
     */
    private void botActionListener(Event e) {
        int sourceX = GridPane.getColumnIndex((Node) e.getSource());
        int sourceY = GridPane.getRowIndex((Node) e.getSource());

        int realX = Math.abs(7 - sourceX);
        int realY = Math.abs(7- sourceY);
        //System.out.println("ClickEvent: X=" + sourceX + " Y=" + sourceY);
        //System.out.println("realX = " + realX + " realY = " + realY);
        if (someTileIsSelected || mainBoard.hasPiece(realX, realY)) {
            if (someTileIsSelected) {
                if (botHasMadeMove && mainBoard.doMove(previouslyClickedX, previouslyClickedY, realX, realY)) {
                    someTileIsSelected = false;
                    tileButtons[Math.abs(7-previouslyClickedX)][Math.abs(7-previouslyClickedY)].setBackground(previousSelectedColor);
                    drawBoard();
                    new Thread(() -> {
                        botHasMadeMove = false;
                        Bot05.makeMove(mainBoard, Board.BLACK);
                        Platform.runLater(this::drawBoard);
                        botHasMadeMove = true;
                    }).start();
                }
                someTileIsSelected = false;
                tileButtons[Math.abs(7-previouslyClickedX)][Math.abs(7-previouslyClickedY)].setBackground(previousSelectedColor);
            } else {
                previouslyClickedX = realX;
                previouslyClickedY = realY;
                previousSelectedColor = tileButtons[sourceX][sourceY].getBackground();
                tileButtons[sourceX][sourceY].setBackground(getBackground(Color.RED));
                someTileIsSelected = true;
            }
        }
    }

    /**
     * Action-listener for development and testing
     */
    private void devActionListener(Event e) {
        int sourceX = GridPane.getColumnIndex((Node) e.getSource());
        int sourceY = GridPane.getRowIndex((Node) e.getSource());

        int realX = Math.abs(7 - sourceX);
        int realY = Math.abs(7- sourceY);

        System.out.println("ClickEvent: X=" + sourceX + " Y=" + sourceY);
        System.out.println("realX = " + realX + " realY = " + realY);
        if (someTileIsSelected || mainBoard.hasPiece(realX, realY)) {
            if (someTileIsSelected) {
                if (mainBoard.doMove(previouslyClickedX, previouslyClickedY, realX, realY)) {
                    drawBoard();
                }
                someTileIsSelected = false;
                tileButtons[Math.abs(7-previouslyClickedX)][Math.abs(7-previouslyClickedY)].setBackground(previousSelectedColor);
            } else {
                previouslyClickedX = realX;
                previouslyClickedY = realY;
                previousSelectedColor = tileButtons[sourceX][sourceY].getBackground();
                tileButtons[sourceX][sourceY].setBackground(getBackground(Color.RED));
                someTileIsSelected = true;
            }
        }
    }

    /**
     * Button-setup
     * Sizing, color, ...
     */
    private void initialiseButtons() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Button currentButton = new Button();
                tileButtons[x][y] = currentButton;
                GridPane.setConstraints(currentButton, x, y);
                grid.getChildren().add(currentButton);
                currentButton.setGraphic(getBlankImageView());
                grid.setHgrow(currentButton, Priority.ALWAYS);
                grid.setVgrow(currentButton, Priority.ALWAYS);
                currentButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                currentButton.setPadding(Insets.EMPTY);
                if ((x % 2 == 0 && y % 2 == 1) || x % 2 == 1 && y % 2 == 0) {
                    currentButton.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    currentButton.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }
    }

    /**
     * Get a background object for JFX-buttons with the color of your choice
     * @param color
     * @return
     */
    public Background getBackground(Color color) {
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    /**
     * Imports chess piece-icons for the tile-buttons
     */
    private void importChessPieces() {
        try {
            File file = new File("C:\\Users\\Petter Daae\\Documents\\Java Prosjekter\\ChessLastAttempt\\src\\Frontend\\pieces.png");
            //URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceIcons[i][j] = SwingFXUtils.toFXImage(bufferedImage.getSubimage(j * 64, i * 64, 64, 64), null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes button-icons for the pieces
     * Communicated with backend through the private Chess instance 'chessGame'
     */
    private void drawBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                int realX = Math.abs(7 - x);
                int realY = Math.abs(7 - y);

                byte piece = mainBoard.getPiece(x, y);
                if (piece != 0) {
                    Image image = chessPieceIcons[mainBoard.getColor(piece)][Math.abs(piece) - 1];
                    tileButtons[realX][realY].setGraphic(new ImageView(image));
                } else {
                    tileButtons[realX][realY].setGraphic(getBlankImageView());
                }
            }
        }
    }

    /**
     * Used for setting the imageView on the TileButtons when a piece is moved away from the button
     * @return blank, transparent ImageView object
     */
    private ImageView getBlankImageView() {
        return new ImageView(SwingFXUtils.toFXImage(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB) , null));
    }

    /**
     * JavaFX syntax
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


}