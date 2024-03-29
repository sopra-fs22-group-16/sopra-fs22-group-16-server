package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.game.Game;
import ch.uzh.ifi.hase.soprafs22.game.enums.GameMode;
import ch.uzh.ifi.hase.soprafs22.game.enums.GameType;
import ch.uzh.ifi.hase.soprafs22.game.player.IPlayer;
import ch.uzh.ifi.hase.soprafs22.lobby.Lobby;
import ch.uzh.ifi.hase.soprafs22.lobby.LobbyManager;
import ch.uzh.ifi.hase.soprafs22.lobby.enums.Visibility;
import ch.uzh.ifi.hase.soprafs22.lobby.interfaces.ILobby;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static ch.uzh.ifi.hase.soprafs22.game.enums.GameMode.ONE_VS_ONE;
import static ch.uzh.ifi.hase.soprafs22.game.enums.GameMode.TWO_VS_TWO;
import static ch.uzh.ifi.hase.soprafs22.game.enums.GameType.RANKED;
import static ch.uzh.ifi.hase.soprafs22.game.enums.GameType.UNRANKED;
import static ch.uzh.ifi.hase.soprafs22.lobby.enums.Visibility.PRIVATE;
import static ch.uzh.ifi.hase.soprafs22.lobby.enums.Visibility.PUBLIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LobbyServiceTest {

    private static final String TOKEN = "token";
    private static final Long LOBBY_ID = 1L;
    private final LobbyManager lobbyManager;
    private final LobbyService lobbyService;

    public LobbyServiceTest() {
        this.lobbyManager = mock(LobbyManager.class);
        UserRepository userRepository = mock(UserRepository.class);
        this.lobbyService = new LobbyService(userRepository, lobbyManager);
    }

    @Test
    void playerModification() throws Exception {
        lobbyService.modifyPlayer(TOKEN, LOBBY_ID, "newName", true);

        verify(lobbyManager).modifyPlayer(TOKEN, LOBBY_ID, "newName", true);
    }

    @Test
    void createGame() throws Exception {
        ILobby lobby = mock(ILobby.class);
        Game game = mock(Game.class);
        IPlayer host = mock(IPlayer.class);
        IPlayer player = mock(IPlayer.class);
        when(lobbyManager.getLobbyWithId(LOBBY_ID)).thenReturn(lobby);
        when(lobby.getHost()).thenReturn(host);
        when(host.getToken()).thenReturn(TOKEN);
        when(lobby.getGameMode()).thenReturn(ONE_VS_ONE);
        when(lobby.getNumberOfPlayers()).thenReturn(2);
        when(lobby.iterator()).thenReturn(List.of(host, player).iterator());
        when(host.isReady()).thenReturn(true);
        when(player.isReady()).thenReturn(true);
        when(lobby.getGame()).thenReturn(null).thenReturn(game);

        Game actualGame = lobbyService.createGame(TOKEN, LOBBY_ID);

        assertEquals(game, actualGame);
    }

    @Test
    void updateLobby() throws Exception {
        ILobby lobby = mock(ILobby.class);
        IPlayer host = mock(IPlayer.class);
        when(lobby.getHost()).thenReturn(host);
        when(host.getToken()).thenReturn(TOKEN);

        lobbyService.updateLobby(lobby, TOKEN, "newName", PUBLIC, TWO_VS_TWO, UNRANKED);

        verify(lobby).setName(eq("newName"));
        verify(lobby).setVisibility(eq(PUBLIC));
        verify(lobby).setGameMode(eq(TWO_VS_TWO));
        verify(lobby).setGameType(eq(UNRANKED));
    }

    @Test
    void getQRCode_validInput_success() {
        // given
        long id = 0L;
        String lobbyName = "lobbyName";
        Visibility visibility = PRIVATE;
        GameMode gameMode = ONE_VS_ONE;
        GameType gameType = UNRANKED;

        // create lobby
        ILobby createdLobby = new Lobby(id, lobbyName, visibility, null);
        createdLobby.setGameMode(gameMode);
        createdLobby.setGameType(gameType);
        String hostToken = createdLobby.getHost().getToken();

        // Sample QRCode png
        String hexByteCodeSampleImage = "89504e470d0a1a0a0000000d49484452000000640000006401030000004a2c071700000006504c5445ffffff00000055c2d37e000000097048597300000ec400000ec401952b0e1b0000015e49444154388d8dd44d8a84301005e0922cb24b5f40f01ad9e52a425f20bd6ffcb980b95276b946c00be82e8b60cdb39919e8812ea710e15b882f6595446f35725121f1468a39ff514f769d53fb28eb44b2ee8725a3d7c5a9502ec58b26efd67fa825ea26adae842c75d0d9b89f641f85f3ad8b3dafefd37e14aadba835bf8dfaa8f170d553eb757d2459dd82bc11f7757259d4133d989906d7319e93d437916e31df626bec85bce3c5d526e2f3ca52075aa5798e6a431649f71d410853c0932351bd21b5593e9c9a8bac961c798d0667ef643dc7d805ee42a4316551fd58a8411cbc44932835a361690d492d24ab37b6350e596a93648d9bc640757bc26867517d53da81aab78ce689ba875831085897c1caea1f7c2ef4cecc318b427128bc9f23230bbb929b526f650d4ca2b07f849e2d0e2b280b3b9d8d559bc6205c8a277a2db4bd541e3422bd264b12b260a6eab92b24eb3c9f27f2f80369596ff5054e213ebe412541f30000000049454e44ae426082";

        // Convert hex data into byte array
        byte[] qrCode = new byte[hexByteCodeSampleImage.length() / 2];
        for (int i = 0; i < qrCode.length; i++) {
            int index = i * 2;

            // Using parseInt() method of Integer class
            int val = Integer.parseInt(hexByteCodeSampleImage.substring(index, index + 2), 16);
            qrCode[i] = (byte) val;
        }

        // create spy out of lobby
        createdLobby = Mockito.spy(createdLobby);

        // override generateQRCode() method
        when(createdLobby.getQrCode()).thenReturn(qrCode);

        // mock lobbyManager that it returns the mocked lobby
        when(lobbyManager.getLobbyWithId(Mockito.anyLong())).thenReturn(createdLobby);

        // do
        byte[] result = lobbyService.getQRCodeFromLobby(hostToken, id);

        // then
        Mockito.verify(createdLobby, Mockito.atLeastOnce()).getQrCode();
        assertEquals(qrCode, result);
    }
}
