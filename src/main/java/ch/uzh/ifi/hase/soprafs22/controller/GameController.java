package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.game.Position;
import ch.uzh.ifi.hase.soprafs22.game.units.Unit;
import ch.uzh.ifi.hase.soprafs22.rest.dto.HealthDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.MovementDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UnitCommandPutDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UnitDeltaSockDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Game Controller
 * This class is responsible for handling all REST request that are related to
 * the game.
 * The controller will receive the request and delegate the execution to the
 * GameService and finally return the result.
 */

@RestController
public class GameController {
    @Value("${api.version}")
    private String apiVersion;

    private static final String TOPIC_GAME = "/topic/game/";  // Compliant

    @Autowired
    SimpMessagingTemplate socketMessage;

    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PutMapping("/{apiVersion}/game/match/{id}/command/attack")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unitAttack(@RequestHeader("token") String token, @PathVariable Long id, @RequestBody UnitCommandPutDTO unitCommandPutDTO) {
        Position attacker = DTOMapper.INSTANCE.convertPositionDTOToPosition(unitCommandPutDTO.getStart());
        Position defender = DTOMapper.INSTANCE.convertPositionDTOToPosition(unitCommandPutDTO.getEnd());
        Unit defendingUnit = gameService.unitAttack(id, token, attacker, defender);

        UnitDeltaSockDTO unitDeltaSock = new UnitDeltaSockDTO();
        // Health delta for socket.
        HealthDTO healthDeltaSock = new HealthDTO();
        healthDeltaSock.setHealth(defendingUnit.getHealth());
        healthDeltaSock.setDefenderPosition(DTOMapper.INSTANCE.convertPositionToPositionDTO(defendingUnit.getPosition()));
        unitDeltaSock.setHealth(healthDeltaSock);
        // Movement delta for socket.
        MovementDTO moveDeltaSock = new MovementDTO();
        moveDeltaSock.setStart(unitCommandPutDTO.getStart());
        moveDeltaSock.setEnd(unitCommandPutDTO.getEnd());
        unitDeltaSock.setMovement(moveDeltaSock);

        socketMessage.convertAndSend(TOPIC_GAME + id, unitDeltaSock);
    }

    @PutMapping("/{apiVersion}/game/match/{id}/command/wait")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unitWait(@RequestHeader("token") String token, @PathVariable Long id, @RequestBody UnitCommandPutDTO unitCommandPutDTO) {
        Position start = DTOMapper.INSTANCE.convertPositionDTOToPosition(unitCommandPutDTO.getStart());
        Position end = DTOMapper.INSTANCE.convertPositionDTOToPosition(unitCommandPutDTO.getEnd());

        gameService.unitWait(id, token, start, end);

        UnitDeltaSockDTO unitDeltaSock = new UnitDeltaSockDTO();
        MovementDTO moveDeltaSock = new MovementDTO();
        moveDeltaSock.setStart(unitCommandPutDTO.getStart());
        moveDeltaSock.setEnd(unitCommandPutDTO.getEnd());
        unitDeltaSock.setMovement(moveDeltaSock);

        socketMessage.convertAndSend(TOPIC_GAME + id, unitDeltaSock);
    }

    private static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}
