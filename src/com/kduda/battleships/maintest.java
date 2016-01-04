package com.kduda.battleships;


//public class maintest extends Application {
//
//    private boolean running = false;
//    private Board enemyBoard, playerBoard;
//
//    private int shipsToPlace = 5;
//
//    private boolean enemyTurn = false;
//
//    private Random random = new Random();
//
//
//
//    private Parent createContent() {
//        BorderPane root = new BorderPane();
//        root.setPrefSize(600, 800);
//
//
//        enemyBoard = new Board(true, event -> {
//            if (!running)
//                return;
//
//            Cell cell = (Cell) event.getSource();
//            if (cell.wasShot)
//                return;
//
//            enemyTurn = !cell.shootCell();
//
//            if (enemyBoard.ships == 0) {
//                System.out.println("YOU WIN");
//                System.exit(0);
//            }
//
//            if (enemyTurn)
//                enemyMove();
//        });
//
//        playerBoard = new Board(false, event -> {
//            if (running)
//                return;
//
//            Cell cell = (Cell) event.getSource();
//            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
//                if (--shipsToPlace == 0) {
//                    startGame();
//                }
//            }
//        });
//
//        VBox vbox = new VBox(50, enemyBoard, playerBoard);
//        vbox.setAlignment(Pos.CENTER);
//
//        root.setCenter(vbox);
//
//        return root;
//    }

//    private void enemyMove() {
//        while (enemyTurn) {
//            int x = random.nextInt(10);
//            int y = random.nextInt(10);
//
//            Cell cell = playerBoard.getCell(x, y);
//            if (cell.wasShot)
//                continue;
//
//            enemyTurn = cell.shootCell();
//
//            if (playerBoard.ships == 0) {
//                System.out.println("YOU LOSE");
//                System.exit(0);
//            }
//        }
//    }

//    private void startGame() {
//        // place enemy ships
//        int type = 5;
//
//        while (type > 0) {
//            int x = random.nextInt(10);
//            int y = random.nextInt(10);
//
//            if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
//                type--;
//            }
//        }
//
//        running = true;
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Scene scene = new Scene(createContent());
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
