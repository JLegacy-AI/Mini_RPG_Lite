package game.ControllerViews;

import game.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    //Attributes

    //This Attr contains Heroes Data on the left side of game Screen
    public VBox heroContainer;

    //This Attr Contains Enemies Data on the right side of game screen
    public VBox enemiesContainer;

    //This border is the complete Border Box which contains Elements Side bars
    public BorderPane screenContainer;

    //This attr Show message like "Attacks Success"
    public Text messageText;

    //this attr game is the instance of game class which contain main logic of game
    private Game game;

    //this attr work when rewards starts this count turns and remove GUI components when reward distributed
    private int heroNumber = 0;


    //This method start first of all, this method sets Game Board
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setGame();
        refreshNode();
        addGameStartButtons();
    }

    /**
     * This method Initialize buttons attack, defend, use food and use potions
     * Along with initialization it also add action listener and
     * add to GUI Screen
     */
    private void addGameStartButtons() {

        Button btn = getButton("Attack", "game/ControllerViews/images/sword.jpg");
        Button btn1 = getButton("Defend", "game/ControllerViews/images/shield.jpg");
        Button btn2 = getButton("Use Food", "game/ControllerViews/images/gapple.jpg");
        Button btn3 = getButton("Use Potion", "game/ControllerViews/images/potion.jpg");

        TilePane tp = new TilePane();
        tp.setAlignment(Pos.CENTER);
        tp.getChildren().addAll(btn, btn1, btn2, btn3);
        screenContainer.setCenter(tp);
        tp.setHgap(10);
        tp.setVgap(10);

        btn.setOnAction(this::actionPerformed);
        btn1.setOnAction(this::actionPerformed);
        btn2.setOnAction(this::actionPerformed);
        btn3.setOnAction(this::actionPerformed);
    }

    /**
     *
     * @param txt - String  get text to show on TextInputDialog then ask Enter Total Heroes and Enter Healer, Hunter, Warrior and Mage
     * @return  Return the result given the User as a input to respective Questions
     */
    private String ask(String txt) {
        TextInputDialog tid = new TextInputDialog();
        tid.setHeaderText(txt);
        tid.setGraphic(null);
        tid.setTitle("Information Seek");
        tid.showAndWait();
        return tid.getResult();
    }

    /**
     * This Method use ask(txt) method and get input about the heroes Number from users
     */
    private void setGame() {
        int totalHeroes = Integer.parseInt(ask("Enter Total Number of Heroes"));
        int mage = Integer.parseInt(ask("Enter Total Mage"));
        int hunter = Integer.parseInt(ask("Enter Total Hunters"));
        int warrior = Integer.parseInt(ask("Enter Total Warriors"));
        int healer = Integer.parseInt(ask("Enter Total Healers"));
        game = new Game(totalHeroes, mage, healer, hunter, warrior);
    }

    /**
     * This method add Enemies in Pane in the Side bar and also set their colors if they are dead
     * And respective colors to respective states of Enemies
     */
    private void setEnemiesContainer() {
        for (Enemy enemy : game.getEnemyList()) {
            if (game.getEnemyByTurn() == enemy) {
                VBox vb = getEnemyInformationPane(enemy);
                vb.setStyle("-fx-background-color: #ee00ff;");
                enemiesContainer.getChildren().add(vb);
            } else if (enemy.getLifePoints() <= 0) {
                VBox vb = getEnemyInformationPane(enemy);
                vb.setStyle("-fx-background-color: #ff0000;");
                enemiesContainer.getChildren().add(vb);
            } else {
                VBox vb = getEnemyInformationPane(enemy);
                enemiesContainer.getChildren().add(vb);
            }
        }
    }

    /**
     * @param e - this get Enemy as an instance, extract information and return VBox which contain Enemy Information
     * @return  Return a VBox which contain information of Enemy
     */
    private VBox getEnemyInformationPane(Enemy e) {
        VBox vb = new VBox();
        vb.setPrefWidth(139);
        vb.setMaxWidth(139);
        vb.setStyle("-fx-background-color: #ffff;");
        vb.getChildren().add(getHorizontalBox("Name   ", e.getName()));
        vb.getChildren().add(getHorizontalBox("Life       ", "" + e.getLifePoints()));
        vb.getChildren().add(getHorizontalBox("Weapon Damage      ", "" + e.getDamage()));
        vb.setEffect(new DropShadow(10, Color.WHITE));
        return vb;
    }

    /**
     * This Method sets heroes information same like enemies
     * This method sets Color of Enemy weather they are dead or alive
     */
    private void setHeroContainer() {
        for (Hero hero : game.getHeroList()) {
            if (game.getHeroList().get(game.getPlayerTurn()) == hero) {
                VBox vb = getHeroInformationPane(hero);
                vb.setStyle("-fx-background-color: #ff0087;");
                heroContainer.getChildren().add(vb);
            } else if (hero.getLifePoints() <= 0) {
                VBox vb = getHeroInformationPane(hero);
                vb.setStyle("-fx-background-color: #ff0101;");
                heroContainer.getChildren().add(vb);
            } else {
                heroContainer.getChildren().add(getHeroInformationPane(hero));
            }
        }
    }


    /**
     * @param hero - Hero as an instance then extract information and display
     * @return  VBox which contains Heroes Information
     */
    private VBox getHeroInformationPane(Hero hero) {
        VBox vb = new VBox();
        vb.setPrefWidth(139);
        vb.setMaxWidth(139);
        vb.setStyle("-fx-background-color: #ffff;");
        vb.getChildren().add(getHorizontalBox("Name:   ", hero.getName()));
        vb.getChildren().add(getHorizontalBox("Life:       ", "" + hero.getLifePoints()));
        vb.getChildren().add(getHorizontalBox("Armor:      ", "" + hero.getArmor()));
        vb.getChildren().add(getHorizontalBox("Weapon Damage:      ", "" + hero.getWeaponDamage()));
        vb.getChildren().add(getHorizontalBox("Foods:   ", "" + hero.getLembas().size()));
        vb.setEffect(new DropShadow(10, Color.WHITE));
        if (hero instanceof Hunter) {
            vb.getChildren().add(getHorizontalBox("Arrows: ", "" + hero.getArrows()));
        }
        if (hero instanceof Mage || hero instanceof Healer) {
            vb.getChildren().add((getHorizontalBox("Potions:        ", "" + hero.getPotions().size())));
            vb.getChildren().add((getHorizontalBox("Mana-Points:        ", "" + hero.getManaPoints())));
            vb.getChildren().add(getHorizontalBox("Mana-Cost:     ", "" + hero.getManaCost()));
        }
        return vb;
    }

    /**
     * @param txt - String which is set into text and here information passes like "Life", "Mana-Points" and so on.
     * @param inf - this is information of heroes, like life points and so on.
     * @return  HBox which contains only two text objects in this manner "   Life-Points:                 10"
     */
    private HBox getHorizontalBox(String txt, String inf) {
        HBox hb = new HBox();
        hb.setPadding(new Insets(5));
        hb.getChildren().addAll(new Text(txt), new Text(inf));
        return hb;
    }

    /**
     * This method sets the label on the Side bars which contains "Enemy" and "Heroes"
     */
    private void initializeLabel() {
        Label label = new Label("Heroes");
        label.setStyle("-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        heroContainer.getChildren().add(label);
        label = new Label("Enemies");
        label.setStyle("-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        enemiesContainer.getChildren().add(label);
    }

    /**
     * This method clear the data from the sides containers and refresh the containers
     * On every action this methods calls and refresh the new states
     */
    private void refreshNode() {
        heroContainer.getChildren().clear();
        enemiesContainer.getChildren().clear();
        try {
            initializeLabel();
            setHeroContainer();
            setEnemiesContainer();
        } catch (Exception ignored) {

        }
    }


    /**
     * This method run the respective statement with respect to buttons
     * @param e - Action Event Object this helps in recognizing which button Triggered Event
     */
    private void actionPerformed(ActionEvent e) {
        refreshNode();
        winLoose();
        if (((Button) e.getSource()).getText().equalsIgnoreCase("Attack")) {
            setMessageText(game.attack());
        } else if (((Button) e.getSource()).getText().equalsIgnoreCase("Defend")) {
            setMessageText(game.defend());
        } else if (((Button) e.getSource()).getText().equalsIgnoreCase("Use food")) {
            setMessageText(game.consumeFood());
        } else if (((Button) e.getSource()).getText().equalsIgnoreCase("use potion")) {
            setMessageText(game.consumePotion());
        }
        try {
            winLoose();
            refreshNode();
        } catch (Exception ignored) {
        }
    }

    /**
     *This method set Buttons after each combat only when heroes win the combat
     * add the instantiated buttons into container
     * add action listener on buttons
     */
    private void heroesChoice() {

        Button btn1 = new Button("Increase Armor");
        btn1.setPrefSize(400, 50);

        Button btn2 = new Button("Increase Weapon Damage");
        btn2.setPrefSize(400, 50);

        Button btn3 = new Button("Increase Potion and Food Effectiveness");
        btn3.setPrefSize(400, 50);

        Button btn4 = new Button("Increase Potion and Food Number");
        btn4.setPrefSize(400, 50);

        Button btn5 = new Button("Increase Arrows");
        btn5.setPrefSize(400, 50);

        Button btn6 = new Button("Decrease Mana-Cost");
        btn6.setPrefSize(400, 50);

        FlowPane fp = new FlowPane();
        fp.setAlignment(Pos.CENTER);
        fp.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);

        btn1.setOnAction(this::choiceAction);
        btn2.setOnAction(this::choiceAction);
        btn3.setOnAction(this::choiceAction);
        btn4.setOnAction(this::choiceAction);
        btn5.setOnAction(this::choiceAction);
        btn6.setOnAction(this::choiceAction);

        screenContainer.setCenter(fp);
    }

    /**
     * This method run respective statements related to buttons
     * @param e - Action Event Object helps in recognize button which triggered Event
     */
    private void choiceAction(ActionEvent e) {
        refreshNode();
        Button btn = (Button) e.getSource();
        if (btn.getText().contains("Armor")) {
            setMessageText(game.increaseArmor());
        } else if (btn.getText().contains("Weapon")) {
            setMessageText(game.increaseWeaponDamage());
        } else if (btn.getText().contains("Mana-Cost")) {
            setMessageText(game.decreaseManaCost());
        } else if (btn.getText().contains("Arrows")) {
            setMessageText(game.increaseArrows());
        } else if (btn.getText().contains("Effectiveness")) {
            setMessageText(game.increaseFoodAndPotionEffectiveness());
        } else if (btn.getText().contains("Number")) {
            setMessageText(game.increasePotionAndFoodNumber());
        }

        if (heroNumber >= game.getHeroList().size()) {
            System.out.println("Jamal");
            doContinue();
            setEnemiesContainer();
            addGameStartButtons();
            refreshNode();
            heroNumber = 0;
            return;
        }

        heroNumber++;
        refreshNode();
    }

    /**
     *This method shows information when needed i.e "Win" / "Loose"
     * @param txt - this get as a text and show on Alert Box
     */
    private void alertDialogue(String txt) {
        System.out.println("Alert Dialogue");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, txt);
        alert.showAndWait();
    }

    /**
     * This Method Shows "Win" / "Loose" and calls alertDialog(txt) method
     * and set reward buttons if wins
     * otherwise new combat
     */
    private void winLoose() {
        if (game.win()) {
            alertDialogue("You Win!!!");
            heroesChoice();
            setEnemiesContainer();
        } else if (game.lose()) {
            alertDialogue("You Lose!!!");
            doContinue();
            initialize(null, null);
        }
    }

    /**
     * This method ask from user to Do you Want to continue the game or not
     */
    private void doContinue() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do You Want to Continue");

        ButtonType buttonTypeOne = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeTwo = new ButtonType("No", ButtonBar.ButtonData.NO);


        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            alertDialogue(game.newCombat());
        } else if (result.get() == buttonTypeTwo) {
            System.exit(0);
        } else {
            System.exit(0);
        }
    }

    /**
     * @param name - name to display on Button
     * @param path - path of image which display on Background of button
     * @return Button Object
     */
    private Button getButton(String name, String path) {
        Button btn = new Button(name);
        btn.setPrefSize(200, 200);
        btn.setBackground(new Background(new BackgroundImage(new Image(path), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(200, 200, false, false, true, true))));
        btn.setCursor(Cursor.HAND);
        btn.setEffect(new DropShadow(10, Color.BLACK));
        return btn;
    }

    /**
     * @param txt - message to display on the bottom of the top container which shows action applied
     */
    private void setMessageText(String txt) {
        messageText.setText(txt);
    }
}
