/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author alumno1718_2
 */
public class example_game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.append("You are a character\nYou have to touch a ball to win a point\nYou have 10 move points each turn , if you don't use them all you will have them at the next turn\nYou get 10 move points each turn\nBall speed and position is generated randomly every match\nBalls bounce when they reach a border\nYou have to input your move coordinates in an x(right and left) y(down and up) format\nThe first player to reach 5 points wins\nThe character | means nothing in that position , the character O means ball in that position\n");
        Field field = new Field(79, 20, 50);
        Game_engine game_engine = new Game_engine();
        Scanner input = new Scanner(System.in);
        Ball[] balls = {new Ball(field), new Ball(field), new Ball(field), new Ball(field), new Ball(field)};
        System.out.println("Input the character you want to use");
        Player[] players = {new Player(input.nextLine().charAt(0), field), new Player(input.nextLine().charAt(0), field)};
        game:
        while (true) {
            game_engine.DrawFrame(field);
            game_engine.UpdateLocations(field);
            game_engine.DrawFrame(field);
            for (Player player : players) {
                System.out.append("Player ");
                System.out.append(player.character);
                System.out.println(" input your move coordinates");
                try {
                    player.move(input.nextInt(), input.nextInt());
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Thats not a coordinate , you loose your turn");
                }
            }
            addPoints(field, players);
            showScore(players);
            for (Player player : players) {
                if (player.points >= 5) {
                    System.out.append("Player ");
                    System.out.append(player.character);
                    System.out.println(" has won the match");
                    break game;
                }
            }
        }
    }

    public static void showScore(Player[] players) {
        for (Player player : players) {
            System.out.append("Player ");
            System.out.append(player.character);
            System.out.append(" has ");
            System.out.append(player.points.toString());
            System.out.println(" points");
        }
    }

    public static void addPoints(Field field, Player[] players) {
        for (Player player : players) {
            for (Game_object game_object : field.game_objects[player.location.x][player.location.y]) {
                if (game_object != null) {
                    player.points += ("Ball".equals(game_object.object_type)) ? 1 : 0;
                }
            }
        }
    }
}
