package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.game.Player;
import ch.uzh.ifi.hase.soprafs22.game.enums.Team;
import ch.uzh.ifi.hase.soprafs22.lobby.Lobby;
import ch.uzh.ifi.hase.soprafs22.lobby.enums.Visibility;
import ch.uzh.ifi.hase.soprafs22.lobby.interfaces.ILobby;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
    @Test
    public void testCreateLobby_fromLobby_toLobbyGetDTO_success() {

        // create Lobby
        ILobby lobby = new Lobby(1L,"myLobbyName", Visibility.PRIVATE);

        // MAP -> LobbyGetDTO
        LobbyGetDTO lobbyGetDTO = DTOMapper.INSTANCE.convertILobbyToLobbyGetDTO(lobby);

        // check content
        assertEquals(lobbyGetDTO.getId(), lobby.getId());
        assertEquals(lobbyGetDTO.getName(), lobby.getName());
        assertEquals(lobbyGetDTO.getOwnerId(), lobby.getOwner().getId());

        int counter = 0;
        for (Player player : lobby) {
            // Check that there exists an element in the lobbyGetDTO
            assertTrue(counter < lobbyGetDTO.getPlayers().size());
            // Get the next user from the lobby
            // Check that their id, username and ready status matches
            assertEquals(lobbyGetDTO.getPlayers().get(counter).getId(), player.getId());
            assertEquals(lobbyGetDTO.getPlayers().get(counter).getName(), player.getName());
            assertEquals(lobbyGetDTO.getPlayers().get(counter).isReady(), player.isReady());
            assertEquals(lobbyGetDTO.getPlayers().get(counter).getTeam(), player.getTeam().getTeamNumber());
            ++counter;
        }

        assertEquals(lobbyGetDTO.getVisibility(), lobby.getLobbyMode());
        assertEquals(lobbyGetDTO.getGameMode(), lobby.getGameMode());
        assertEquals(lobbyGetDTO.getGameType(), lobby.getGameType());
        assertEquals(lobbyGetDTO.getInvitationCode(), lobby.getInvitationCode());
    }

    @Test
    public void testGetLobby_fromPlayer_toPlayerGetDTO_success() {
        // create Lobby
        Player player = new Player(1L, "username", "token", Team.Red);

        // MAP -> LobbyGetDTO
        PlayerGetDTO playerGetDTO = DTOMapper.INSTANCE.convertPlayerToPlayerGetDTO(player);

        // check content
        assertEquals(player.getId(), playerGetDTO.getId());
        assertEquals(player.getName(), playerGetDTO.getName());
        assertEquals(player.isReady(), playerGetDTO.isReady());
        assertEquals(player.getTeam().getTeamNumber(), playerGetDTO.getTeam());
    }
}
