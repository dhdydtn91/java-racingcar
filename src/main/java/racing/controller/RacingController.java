package racing.controller;

import racing.model.CarNameParser;
import racing.model.RacingCarDto;
import racing.model.RacingGame;
import racing.view.InputView;
import racing.view.ResultView;

import java.util.List;

public class RacingController {

    private final RacingGame racingGame;
    private final GameTurn gameTurn;

    public RacingController() {
        InputView inputView = new InputView();
        this.gameTurn = new GameTurn(inputView.getTurnCount());
        CarNameParser carNameParser = new CarNameParser(inputView.getParticipationList());
        this.racingGame = new RacingGame(carNameParser.parse());
    }

    /**
     * 게임 시작 메서드 1 턴씩 진행하며 게임이 끝날때까지 반복된다.
     */
    public void play() {
        ResultView resultView = new ResultView();
        for (int i = 0; i < gameTurn.getGameTurn(); i++) {
            turn(resultView);
        }
        finish(resultView);
    }

    /**
     * 매 게임 턴 마다 자동차를 움직이고 결과를 보여주는 메서드
     */
    public void turn(ResultView resultView) {
        List<Integer> randomValue = racingGame.createRandomValue();
        racingGame.moveAndStop(randomValue);
        List<RacingCarDto> carDtoList = createDtoList();
        resultView.turnResultView(carDtoList);
    }

    /**
     * ResultView에 넘길 데이터전송객체 리스트를 만드는 메서드
     */
    public List<RacingCarDto> createDtoList() {
        return racingGame.createDtoList();
    }

    /**
     * 게임을 끝내고 우승자를 보여주는 메서드
     */
    private void finish(ResultView resultView) {
        resultView.drawWinner(racingGame.findWinners());
    }

    public static void main(String[] args) {
        RacingController racingController = new RacingController();
        racingController.play();
    }

}
