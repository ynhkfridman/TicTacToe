package com.fusion.TicTacToe.controller;


import com.fusion.TicTacToe.model.Game;
import com.fusion.TicTacToe.model.Move;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class GameController {
	
    private Game game = new Game();     

    @MessageMapping("/move")
    @SendTo("/topic/game")
    public Game makeMove(Move move) {
        game.makeMove(move.getRow(), move.getCol());
        return game;
    }

    @MessageMapping("/state")
    @SendTo("/topic/game")
    public Game getState() {
        return game;
    }

	@MessageMapping("/newgame")
    @SendTo("/topic/game")
    public Game newGame() {
        game = new Game();
        return game;
    }    

}
